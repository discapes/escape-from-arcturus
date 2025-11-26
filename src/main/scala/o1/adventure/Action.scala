package o1.adventure

/** The class `Action` represents actions that a player may take in a text
  * adventure game. `Action` objects are constructed on the basis of textual
  * commands and are, in effect, parsers for such commands. An action object is
  * immutable after creation.
  * @param input
  *   a textual in-game command such as “go east” or “rest”
  */
class Action(input: String):

  private val commandText = input.trim.toLowerCase
  private val verb = commandText.takeWhile(_ != ' ')
  private val modifiers =
    commandText.drop(verb.length).trim

  /** Causes the given player to take the action represented by this object,
    * assuming that the command was understood. Returns a description of what
    * happened as a result of the action (such as “You go west.”). The
    * description is returned in an `Option` wrapper; if the command was not
    * recognized, `None` is returned.
    */
  def execute(actor: Player): Option[String] =
    val actionTypeOption = Utils.findByName(Actions.allActions, this.verb)
    actionTypeOption match
      case Some(actionType: ActionWithModifier) =>
        if (this.modifiers.nonEmpty) then
          Some(actionType.perform(actor, this.modifiers))
        else Some(s"The action '${this.verb}' requires a modifier.")
      case Some(actionType: ActionWithoutModifier) =>
        if (this.modifiers.isEmpty) then Some(actionType.perform(actor))
        else Some(s"The action '${this.verb}' does not take any modifiers.")
      case None => None

  /** Returns a textual description of the action object, for debugging
    * purposes.
    */
  override def toString = s"$verb (modifiers: $modifiers)"

end Action

sealed trait ActionType extends Named

trait ActionWithModifier(val name: String) extends ActionType:
  def perform(actor: Player, modifier: String): String

trait ActionWithoutModifier(val name: String) extends ActionType:
  def perform(actor: Player): String

object Actions:
  case object Go extends ActionWithModifier("go"):
    def perform(actor: Player, modifier: String) = actor.go(modifier)

  case object Use extends ActionWithModifier("use"):
    def perform(actor: Player, modifier: String) = actor.use(modifier)

  case object Drop extends ActionWithModifier("drop"):
    def perform(actor: Player, modifier: String) = actor.drop(modifier)

  case object Get extends ActionWithModifier("get"):
    def perform(actor: Player, modifier: String) = actor.get(modifier)

  case object Examine extends ActionWithModifier("examine"):
    def perform(actor: Player, modifier: String) =
      actor.examine(modifier)

  case object Fire extends ActionWithModifier("fire"):
    def perform(actor: Player, modifier: String) = actor.fire(modifier)

  case object Inventory extends ActionWithoutModifier("inventory"):
    def perform(actor: Player) = actor.inventory

  case object Rest extends ActionWithoutModifier("rest"):
    def perform(actor: Player) = actor.rest()

  case object Quit extends ActionWithoutModifier("quit"):
    def perform(actor: Player) = actor.quit()

  case object Debug extends ActionWithoutModifier("debug"):
    def perform(actor: Player) = s"""
    Player location: ${actor.location.name}
    Player oxygen level: ${actor.oxygenLevel}/${actor.maxOxygenLevel} (${actor.oxygenLevelPercent}%)
    Foe locations: ${actor.adventure.allFoes
        .map(foe => s"${foe.name} at ${foe.location.name}")
        .mkString(", ")}
    """

  case object Help extends ActionWithoutModifier("help"):
    def perform(actor: Player) = """You can use the following commands:
  go ROOM - Move to the speicified neighboring area.
  get ITEM - Pick up an item from your current location.
  drop ITEM - Drop an item from your inventory.
  use ITEM - Use a consumable item from your inventory.
  fire ITEM - Fire a weapon at a foe.
  examine ITEM - Examine an item in your inventory.
  inventory - Check the items you are carrying.
  rest - Rest for a while.
  quit - Quit the game.
  help - Show this help message.
  """

  val allActions: Vector[ActionType] = Vector(
    Go,
    Use,
    Drop,
    Get,
    Examine,
    Fire,
    Inventory,
    Rest,
    Quit,
    Help,
    Debug
  )
