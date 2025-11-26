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
    "You are somewhere in the forest. There are a lot of trees here.\nBirds are singing."
  )
  private val eastHallway = Area(
    "East Hallway",
    "You are somewhere in the forest. A tangle of bushes blocks further passage north.\nBirds are singing."
  )
  private val airlockA = Area(
    "Airlock to Hangers 01-02",
    "The forest just goes on and on."
  )
  private val hanger01 = Area(
    "Hanger 01",
    "You are at a small clearing in the middle of forest.\nNearly invisible, twisted paths lead in many directions."
  )
  private val hanger02 = Area(
    "Hanger 02",
    "You are in a dense tangle of bushes. It's hard to see exactly where you're going."
  )
  private val northHallway = Area(
    "North Hallway",
    "Home sweet home! Now the only thing you need is a working remote control."
  )
  private val engineRoomB = Area(
    "Engine Room B",
    "Home sweet home! Now the only thing you need is a working remote control."
  )
  private val controlRoom = Area(
    "Control Room",
    "Home sweet home! Now the only thing you need is a working remote control."
  )
  private val southHallway = Area(
    "South Hallway",
    "Home sweet home! Now the only thing you need is a working remote control."
  )
  private val engineRoomA = Area(
    "Engine Room A",
    "Home sweet home! Now the only thing you need is a working remote control."
  )
  private val airlockB = Area(
    "Airlock to Command and Private Hangers",
    "Home sweet home! Now the only thing you need is a working remote control."
  )
  private val hangerCommand = Area(
    "Command Hanger",
    "Home sweet home! Now the only thing you need is a working remote control."
  )
  private val hangerPrivate = Area(
    "Private hanger",
    "Home sweet home! Now the only thing you need is a working remote control."
  )
  private val westHallway = Area(
    "West Hallway",
    "Home sweet home! Now the only thing you need is a working remote control."
  )
  private val waterProcessing = Area(
    "Water Processing Facility",
    "Home sweet home! Now the only thing you need is a working remote control."
  )
  private val oxygenProcessing = Area(
    "Oxygen Processing Facility",
    "Home sweet home! Now the only thing you need is a working remote control."
  )
  private val wasteProcessing = Area(
    "Waste Processing Facility",
    "Home sweet home! Now the only thing you need is a working remote control."
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
      "north hallway" -> northHallway,
      "research facility" -> researchFacility
    )
  )

  airlockA.setNeighbors(
    Vector(
      "hanger01" -> hanger01,
      "hanger02" -> hanger02,
      "east hallway" -> eastHallway,
      "north hallway" -> northHallway
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
      "east hallway" -> eastHallway,
      "engine room B" -> engineRoomB,
      "control room" -> controlRoom
    )
  )

  engineRoomB.setNeighbors(
    Vector(
      "leave" -> northHallway
    )
  )

  controlRoom.setNeighbors(
    Vector(
      "north hallway" -> northHallway,
      "south hallway" -> southHallway
    )
  )

  southHallway.setNeighbors(
    Vector(
      "control room" -> controlRoom,
      "engine room A" -> engineRoomA,
      "airlock" -> airlockB,
      "west hallway" -> westHallway
    )
  )

  airlockB.setNeighbors(
    Vector(
      "south hallway" -> southHallway,
      "west hallway" -> westHallway,
      "command hanger" -> hangerCommand,
      "private hanger" -> hangerPrivate
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
      "south hallway" -> southHallway,
      "airlock" -> airlockB,
      "water processing" -> waterProcessing,
      "oxygen processing" -> oxygenProcessing,
      "waste processing" -> wasteProcessing
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
  def isComplete = this.player.location == this.destination && player.has(
    "battery"
  ) && player.has("remote")

  /** Determines whether the player has won, lost, or quit, thereby ending the
    * game.
    */
  def isOver =
    this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit

  /** Returns a message that is to be displayed to the player at the beginning
    * of the game.
    */
  def welcomeMessage =
    "You are lost in the woods. Find your way back home.\n\nBetter hurry, 'cause Scalatut elämät is on real soon now. And you can't miss Scalkkarit, right?"

  /** Returns a message that is to be displayed to the player at the end of the
    * game. The message will be different depending on whether the player has
    * completed their quest.
    */
  def goodbyeMessage =
    if this.isComplete then "Home at last... and phew, just in time! Well done!"
    else if this.turnCount == this.timeLimit then
      "Oh no! Time's up. Starved of entertainment, you collapse and weep like a child.\nGame over!"
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
