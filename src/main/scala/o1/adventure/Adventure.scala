package o1.adventure

/** The class `Adventure` represents text adventure games. An adventure consists
  * of a player and a number of areas that make up the game world. It provides
  * methods for playing the game one turn at a time and for checking the state
  * of the game.
  *
  * N.B. This version of the class has a lot of “hard-coded” information that
  * pertains to a very specific adventure game that involves a small trip
  * through a twisted forest. All newly created instances of class `Adventure`
  * are identical to each other. To create other kinds of adventure games, you
  * will need to modify or replace the source code of this class.
  */
class Adventure:

  /** the name of the game */
  val title = "A Forest Adventure"

  private val researchFacility = Area(
    "Reseach Facility",
    "Flickering monitors cast pale light over overturned carts and shattered glass. Whatever they were studying, they left in a hurry."
  )
  private val eastHallway = Area(
    "East Hallway",
    "A narrow corridor stretches ahead, walls scarred by tool marks and emergency lighting pulsing weakly along the floor."
  )
  private val airlockA = Area(
    "Airlock to Hangers 01-02",
    "The chamber is silent, its inner door half-open. A faint draft whispers through, carrying the smell of stale vacuum seals."
  )
  private val hanger01 = Area(
    "Hanger 01",
    "The large bay echoes with every step. Empty clamps and broken cargo crates suggest a ship once rested here."
  )
  private val hanger02 = Area(
    "Hanger 02",
    "Dim floodlights reveal scattered mech parts and a dangling crane hook swaying gently in still air."
  )
  private val northHallway = Area(
    "North Hallway",
    "Cold air circulates loudly through exposed vents, and a sluggish service drone lies powered down along the wall."
  )
  private val engineRoomB = Area(
    "Engine Room B",
    "Dark coolant stains cover the grated floor. A massive reactor housing looms ahead, silent but ominously warm."
  )
  private val controlRoom = Area(
    "Control Room",
    "Dead screens wrap around the central console. A lone blinking indicator stubbornly flashes, waiting for a signal that will never come."
  )
  private val southHallway = Area(
    "South Hallway",
    "Loose ceiling panels clatter softly above, disturbed by the station’s slow, rhythmic shudder."
  )
  private val engineRoomA = Area(
    "Engine Room A",
    "A maze of pipes and dormant thrusters crowds the chamber. Tools lie abandoned beside an open maintenance hatch."
  )
  private val airlockB = Area(
    "Airlock to Command and Private Hangers",
    "This airlock’s outer door is sealed tight, reinforced with manual clamps someone added in a panic."
  )
  private val hangerCommand = Area(
    "Command Hanger",
    "Large enough to house a flagship, the cavernous room is lined with dark command pods and empty fuel tracks."
  )
  private val hangerPrivate = Area(
    "Private hanger",
    "This smaller bay is cluttered with personal lockers, scattered schematics, and a covered craft that someone never returned to uncover."
  )
  private val westHallway = Area(
    "West Hallway",
    "The corridor slopes slightly, lights buzzing as if struggling to stay alive. A soft vibration travels through the floorplates."
  )
  private val waterProcessing = Area(
    "Water Processing Facility",
    "Rows of cracked filtration tanks drip steadily, echoing like a slow clock counting down the station’s final hours."
  )
  private val oxygenProcessing = Area(
    "Oxygen Processing Facility",
    "Massive compressors stand idle, coated in frost. A low hiss resonates—a reminder that the air here is running out."
  )
  private val wasteProcessing = Area(
    "Waste Processing Facility",
    "A foul chemical odor fills the chamber. Conveyor arms lie frozen mid-cycle, gripping a container that never completed its run."
  )
  private val destination = researchFacility

  researchFacility.setNeighbors(
    Vector(
      "leave" -> eastHallway
    )
  )

  eastHallway.setNeighbors(
    Vector(
      "airlock" -> airlockA,
      "northHallway" -> northHallway,
      "researchFacility" -> researchFacility
    )
  )

  airlockA.setNeighbors(
    Vector(
      "hanger01" -> hanger01,
      "hanger02" -> hanger02,
      "eastHallway" -> eastHallway,
      "northHallway" -> northHallway
    )
  )

  hanger01.setNeighbors(
    Vector(
      "leave" -> airlockA
    )
  )

  hanger02.setNeighbors(
    Vector(
      "leave" -> airlockA
    )
  )

  northHallway.setNeighbors(
    Vector(
      "eastHallway" -> eastHallway,
      "engineRoomB" -> engineRoomB,
      "controlRoom" -> controlRoom
    )
  )

  engineRoomB.setNeighbors(
    Vector(
      "leave" -> northHallway
    )
  )

  controlRoom.setNeighbors(
    Vector(
      "northHallway" -> northHallway,
      "southHallway" -> southHallway
    )
  )

  southHallway.setNeighbors(
    Vector(
      "controlRoom" -> controlRoom,
      "engineRoomA" -> engineRoomA,
      "airlock" -> airlockB,
      "westHallway" -> westHallway
    )
  )

  engineRoomA.setNeighbors(
    Vector(
      "leave" -> southHallway
    )
  )

  airlockB.setNeighbors(
    Vector(
      "southHallway" -> southHallway,
      "westHallway" -> westHallway,
      "commandHanger" -> hangerCommand,
      "privateHanger" -> hangerPrivate
    )
  )

  hangerCommand.setNeighbors(
    Vector(
      "leave" -> airlockB
    )
  )

  hangerPrivate.setNeighbors(
    Vector(
      "leave" -> airlockB
    )
  )

  westHallway.setNeighbors(
    Vector(
      "southHallway" -> southHallway,
      "airlock" -> airlockB,
      "waterProcessing" -> waterProcessing,
      "oxygenProcessing" -> oxygenProcessing,
      "wasteProcessing" -> wasteProcessing
    )
  )

  waterProcessing.setNeighbors(
    Vector(
      "leave" -> westHallway
    )
  )

  oxygenProcessing.setNeighbors(
    Vector(
      "leave" -> westHallway
    )
  )

  wasteProcessing.setNeighbors(
    Vector(
      "leave" -> westHallway
    )
  )

  hanger01.addItem(Item("ship part 1", "Ship part 1"))
  engineRoomB.addItem(Item("engine part 1", "Engine part 1"))
  engineRoomA.addItem(Item("engine part 2", "Engine part 2"))
  wasteProcessing.addItem(Item("ship part 2", "Ship part 2"))

  /** The character that the player controls in the game. */
  val player = Player(researchFacility)

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0

  /** The maximum number of turns that this adventure game allows before time
    * runs out.
    */
  val timeLimit = 40

  /** Determines if the adventure is complete, that is, if the player has won.
    */
  def isComplete = this.player.location == this.destination
    && player.has("ship part 1")
    && player.has("ship part 2")
    && player.has("engine part 1")
    && player.has("engine part 2")

  /** Determines whether the player has won, lost, or quit, thereby ending the
    * game.
    */
  def isOver =
    this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit

  /** Returns a message that is to be displayed to the player at the beginning
    * of the game.
    */
  def welcomeMessage =
    "An abandoned station floats in the void, beacon offline, crew unaccounted for. Your ship is dying, and the answers you need are somewhere inside. \nStep carefully. Something here still breathes in the dark."

  /** Returns a message that is to be displayed to the player at the end of the
    * game. The message will be different depending on whether the player has
    * completed their quest.
    */
  def goodbyeMessage =
    if this.isComplete then
      "You made it out alive. Against all odds, you survived."
    else if this.turnCount == this.timeLimit then
      "Your breath grows thin. The emergency gauge hits zero.\nThe station’s corridors blur as the cold creeps in, and your body finally gives way.\nYour ship remains broken. The station claims another soul."
    else // game over due to player quitting
      "Quitter!"

  /** Plays a turn by executing the given in-game command, such as “go west”.
    * Returns a textual report of what happened, or an error message if the
    * command was unknown. In the latter case, no turns elapse.
    */
  def playTurn(command: String): String =
    val action = Action(command)
    val outcomeReport = action.execute(this.player)
    if outcomeReport.isDefined then this.turnCount += 1
    outcomeReport.getOrElse(s"""Unknown command: "$command".""")

end Adventure
