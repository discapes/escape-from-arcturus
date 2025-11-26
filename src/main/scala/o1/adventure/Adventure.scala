package o1.adventure

import scala.util.Random

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
class Adventure(val difficultyLevel: DifficultyLevel):

  /** the name of the game */
  val title = "Escape from Arcturus-7"

  private val researchFacility = Area(
    "Research Facility",
    "Flickering monitors cast pale light over overturned carts and shattered glass. Whatever they were studying, they left in a hurry."
  )
  private val eastHallway = Area(
    "East Hallway",
    "A narrow corridor stretches ahead, walls scarred by tool marks and emergency lighting pulsing weakly along the floor."
  )
  private val airlockA = Area(
    "Airlock Alpha",
    "The chamber is silent, its inner door half-open. A faint draft whispers through, carrying the smell of stale vacuum seals."
  )
  private val hangar01 = Area(
    "Hangar 01",
    "The large bay echoes with every step. Empty clamps and broken cargo crates suggest a ship once rested here."
  )
  private val hangar02 = Area(
    "Hangar 02",
    "Dim floodlights reveal scattered mech parts and a dangling crane hook swaying gently in still air."
  )
  private val northHallway = Area(
    "North Hallway",
    "Cold air circulates loudly through exposed vents, and a sluggish service drone lies powered down along the wall."
  )
  private val engineRoomB = Area(
    "Engine Room Beta",
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
    "Engine Room Alpha",
    "A maze of pipes and dormant thrusters crowds the chamber. Tools lie abandoned beside an open maintenance hatch."
  )
  private val airlockB = Area(
    "Airlock Bravo",
    "This airlock’s outer door is sealed tight, reinforced with manual clamps someone added in a panic."
  )
  private val hangarCommand = Area(
    "Command Hangar",
    "Large enough to house a flagship, the cavernous room is lined with dark command pods and empty fuel tracks."
  )
  private val hangarPrivate = Area(
    "Private Hangar",
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

  private val locations = Vector(
    researchFacility,
    eastHallway,
    airlockA,
    hangar01,
    hangar02,
    northHallway,
    engineRoomB,
    controlRoom,
    southHallway,
    engineRoomA,
    airlockB,
    hangarCommand,
    hangarPrivate,
    westHallway,
    waterProcessing,
    wasteProcessing,
    oxygenProcessing
  )

  val player = Player(hangar02, difficultyLevel, this)
  val slimeMonster = new SlimeMonster(hangarPrivate, player)
  val allFoes = Vector(slimeMonster)
  val spaceship = new Spaceship

  /** The character that the player controls in the game. */

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0

  researchFacility.addNeighbor(eastHallway)

  eastHallway.addNeighbor(researchFacility)
  eastHallway.addNeighbor(airlockA)
  eastHallway.addNeighbor(northHallway)

  airlockA.addNeighbor(eastHallway)
  airlockA.addNeighbor(northHallway)
  airlockA.addNeighbor(hangar01)
  airlockA.addNeighbor(hangar02)

  hangar01.addNeighbor(airlockA)
  hangar02.addNeighbor(airlockA)

  northHallway.addNeighbor(eastHallway)
  northHallway.addNeighbor(engineRoomB)
  northHallway.addNeighbor(controlRoom)
  northHallway.addNeighbor(airlockA)

  engineRoomB.addNeighbor(northHallway)

  controlRoom.addNeighbor(northHallway)
  controlRoom.addNeighbor(southHallway)

  southHallway.addNeighbor(controlRoom)
  southHallway.addNeighbor(engineRoomA)
  southHallway.addNeighbor(airlockB)
  southHallway.addNeighbor(westHallway)

  engineRoomA.addNeighbor(southHallway)

  airlockB.addNeighbor(southHallway)
  airlockB.addNeighbor(westHallway)
  airlockB.addNeighbor(hangarCommand)
  airlockB.addNeighbor(hangarPrivate)

  hangarCommand.addNeighbor(airlockB)

  hangarPrivate.addNeighbor(airlockB)

  westHallway.addNeighbor(southHallway)
  westHallway.addNeighbor(airlockB)
  westHallway.addNeighbor(waterProcessing)
  westHallway.addNeighbor(oxygenProcessing)
  westHallway.addNeighbor(wasteProcessing)

  waterProcessing.addNeighbor(westHallway)
  oxygenProcessing.addNeighbor(westHallway)
  wasteProcessing.addNeighbor(westHallway)

  hangar01.addItem(EngineParts.fluxCapacitor)
  engineRoomB.addItem(EngineParts.antimatterInjector)
  engineRoomA.addItem(EngineParts.gravimetricStabilizer)
  wasteProcessing.addItem(EngineParts.naniteActuator)

  hangar02.addAreaElement(spaceship)

  airlockA.addItem(difficultyLevel.weaponAvailable)

  Random
    .shuffle(locations)
    .take(5)
    .zip(
      Vector(
        new BigOxygenTank,
        new RegularOxygenTank,
        new RegularOxygenTank,
        new SmallOxygenTank,
        new MiniOxygenTank
      )
    )
    .foreach((area, tank) => area.addItem(tank))

  hangarPrivate.addFoe(slimeMonster)

  /** Determines if the adventure is complete, that is, if the player has won.
    */
  def isComplete = this.player.location.areaElements.contains(spaceship) &&
    spaceship.fullyRepaired

  /** Determines whether the player has won, lost, or quit, thereby ending the
    * game.
    */
  def isOver =
    this.isComplete || this.player.hasQuit || player.oxygenLevel <= 0

  /** Returns a message that is to be displayed to the player at the beginning
    * of the game.
    */
  def welcomeMessage =
    """


Three weeks ago, the deep-space relay station Arcturus-7 went silent.
  
  The Galactic Union dispatched you—a freelance salvage operative—to investigate
  the blackout and recover any survivors or critical data. The station had been
  conducting classified research on an alien artifact recovered from a derelict
  vessel near the Kepler Veil. No distress signal. No explanation. Just silence.
  
  Your ship touched down in Hangar 02 without incident. You sealed your helmet,
  cycled the airlock, and stepped onto the deck. The station's atmosphere had long
  since vented—no air, no sound, just the eerie stillness of hard vacuum. At least
  the gravity generator still worked; your boots clung reassuringly to the deck
  plating. For better or worse, you wouldn't be floating.
  
  You had taken only a few steps when motion caught your eye. Above you, a lattice
  of corroded support beams shifted. In the soundless void you could only watch as
  tons of debris broke free and plummeted—straight onto your ship. The hull
  crumpled in silent, agonizing slow-motion. Sparks flared and died instantly in
  the vacuum. By the time the dust settled, your vessel was a wreck: flux capacitor
  shattered, antimatter injector cracked, gravimetric stabilizer bent beyond use,
  and the nanite actuator crushed to scrap.
  
  Without those four components, you're stranded. Fortunately, a station this size must have spare parts somewhere.
  
  Your suit's oxygen is limited. Moving and picking up items consumes oxygen.
  Explore carefully, scavenge what you need, and get off this station before you become
  another name on its manifest of the lost.""".stripMargin

  /** Returns a message that is to be displayed to the player at the end of the
    * game. The message will be different depending on whether the player has
    * completed their quest.
    */
  def goodbyeMessage =
    if this.isComplete then
      "You made it out alive. Against all odds, you survived."
    else if player.oxygenLevel <= 0 then
      "Your breath grows thin. The emergency gauge hits zero.\nThe station's corridors blur as the cold creeps in, and your body finally gives way.\nYour ship remains broken. The station claims another soul."
    else // game over due to player quitting
      "Quitter!"

  /** Plays a turn by executing the given in-game command, such as “go west”.
    * Returns a textual report of what happened, or an error message if the
    * command was unknown. In the latter case, no turns elapse.
    */
  def playTurn(command: String): String =
    val action = Action(command)
    var outcomeReport = ""
    action.execute(this.player) match {
      case Some(outcome) =>
        this.turnCount += 1
        outcomeReport += outcome
        outcomeReport += s"\n\nCurrent oxygen level: ${player.oxygenLevelPercent}%."
        for foe <- allFoes do
          if !foe.isDefeated then
            val foeActionReport = foe.turn()
            if foeActionReport.nonEmpty then
              outcomeReport += "\n" + foeActionReport
      case None => outcomeReport = s"""Unknown command: "$command"."""
    }
    outcomeReport

end Adventure
