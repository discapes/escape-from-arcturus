package o1.adventure

import scala.collection.mutable.Map

/** The class `Area` represents locations in a text adventure game world. A game
  * world consists of areas. In general, an “area” can be pretty much anything:
  * a room, a building, an acre of forest, or something completely different.
  * What different areas have in common is that players can be located in them
  * and that they can have exits leading to other, neighboring areas. An area
  * also has a name and a description.
  * @param name
  *   the name of the area
  * @param description
  *   a basic description of the area (typically not including information about
  *   items)
  */

/* We changed up the area logic so that they aren't connected together
 * by directions, but instead each room has a unique name. I belive this
 * makes the game easier to follow. */
class Area(var name: String, var description: String) extends Named:
  var items = Vector[Item]()
  var neighbors = Set[Area]()
  var areaElements = Vector[AreaElement]()
  var foes = Vector[Foe]()
  def addItem(i: Item) = items = items :+ i
  def removeItem(i: Item) = items = items.filterNot(_ == i)
  def addNeighbor(area: Area) = neighbors = neighbors + area
  def addFoe(foe: Foe) = foes = foes :+ foe
  def removeFoe(foe: Foe) = foes = foes.filterNot(_ == foe)
  def addAreaElement(element: AreaElement) =
    areaElements = areaElements :+ element

  /** Returns a multi-line description of the area as a player sees it. This
    * includes a basic description of the area as well as information about
    * exits and items. If there are no items present, the return value has the
    * form "DESCRIPTION\n\nExits available: DIRECTIONS SEPARATED BY SPACES". If
    * there are one or more items present, the return value has the form
    * "DESCRIPTION\nYou see here: ITEMS SEPARATED BY SPACES\n\nExits available:
    * DIRECTIONS SEPARATED BY SPACES". The items and directions are listed in an
    * arbitrary order.
    */
  def fullDescription: String =
    val areaElements = Some(
      this.areaElements
        .map(_.description)
        .mkString("\n")
    ).filter(_.nonEmpty).getOrElse("")
    val itemsAvailable =
      if (this.items.isEmpty) ""
      else
        "\nYou see here: " + this.items
          .map(_.name)
          .mkString(", ")
    val exitsAvailable =
      "\n\nExits available: " + this.neighbors
        .map(_.name)
        .mkString(", ")
    val aliveFoes = this.foes.filterNot(_.isDefeated)
    val foesPresent =
      if aliveFoes.isEmpty then ""
      else
        "\nBeware!! You see an enemy! " + aliveFoes.map(_.info).mkString(", ")
    this.description + " " + areaElements + itemsAvailable + exitsAvailable + foesPresent

  /** Returns a single-line description of the area for debugging purposes. */
  override def toString =
    this.name + ": " + this.description.replaceAll("\n", " ").take(150)

end Area
