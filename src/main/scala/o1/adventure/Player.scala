package o1.adventure

import scala.collection.mutable.Map

/** A `Player` object represents a player character controlled by the real-life
  * user of the program.
  *
  * A player object’s state is mutable: the player’s location and possessions
  * can change, for instance.
  *
  * @param startingArea
  *   the player’s initial location
  */
class Player(
    startingArea: Area,
    difficultyLevel: DifficultyLevel,
    val adventure: Adventure
):

  private var currentLocation =
    startingArea // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false // one-way flag
  var items = Vector[Item]()
  def consumables = items.collect { case c: Consumable => c }
  def weapons = items.collect { case w: Weapon => w }

  val maxOxygenLevel = difficultyLevel.maxOxygen
  var oxygenLevel = maxOxygenLevel
  def oxygenLevelPercent = ((oxygenLevel.toDouble / maxOxygenLevel) * 100).round

  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  /** Returns the player’s current location. */
  def location = this.currentLocation

  /** Attempts to move the player in the given direction. This is successful if
    * there is an exit from the player’s current location towards the direction
    * name. Returns a description of the result: "You go DIRECTION." or "You
    * can't go DIRECTION."
    */
  def go(direction: String) =
    Utils.findByName(this.location.neighbors, direction) match {
      case Some(area) =>
        this.currentLocation = area
        this.oxygenLevel -= 1
        s"You go to the ${area.name}."
      case None => s"You can't go $direction."
    }

  def use(i: String) =
    Utils.findByName(this.items, i) match {
      case Some(item) =>
        item match {
          case consumable: Consumable =>
            val (consumeReport, wasConsumed) = consumable.consumeAction(this)
            if wasConsumed then
              this.items = this.items.filterNot(_ == consumable)
            consumeReport
          case _ => s"The ${item.name} is not something you can use."
        }
      case None => s"You don't have that item."
    }

  def fire(i: String) =
    Utils.findByName(this.items, i) match {
      case Some(item) =>
        item match {
          case weapon: Weapon =>
            if this.location.foes.isEmpty then
              s"There are no foes here to fire at."
            else
              val foe = this.location.foes.head
              val result = weapon.fireAt(foe)
              if foe.isDefeated then this.location.removeFoe(foe)
              result
          case _ => s"The ${item.name} is not a weapon you can fire."
        }
      case None => s"You don't have that item."
    }

  def inventory =
    if items.isEmpty then "You are empty-handed."
    else
      s"You are carrying:\n${items.map(item => s"- ${item.name}").mkString("\n")}"

  def get(i: String) =
    Utils.findByName(this.location.items, i) match {
      case Some(item) =>
        this.items = this.items :+ item
        this.location.items = this.location.items.filterNot(_ == item)
        this.oxygenLevel -= 1
        s"You pick up the ${item.name}. ${item.description}"
      case None => s"There is no $i here to pick up."
    }

  def examine(i: String) =
    Utils.findByName(this.items, i) match {
      case Some(value) => value.description
      case None        => "You don't have that item."
    }

  def drop(i: String) =
    Utils.findByName(this.items, i) match {
      case Some(item) =>
        this.items = this.items.filterNot(_ == item)
        this.location.items = this.location.items :+ item
        s"You drop the ${item.name}."
      case None => "You don't have that item."
    }

  /** Causes the player to rest for a short while (this has no substantial
    * effect in game terms). Returns a description of what happened.
    */
  def rest() =
    "You rest for a while. Better get a move on, though. Air is running low."

  /** Signals that the player wants to quit the game. Returns a description of
    * what happened within the game as a result (which is the empty string, in
    * this case).
    */
  def quit() =
    this.quitCommandGiven = true
    ""

  /** Returns a brief description of the player’s state, for debugging purposes.
    */
  override def toString = "Now at: " + this.location.name

end Player
