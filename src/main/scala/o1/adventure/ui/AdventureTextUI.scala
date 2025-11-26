package o1.adventure.ui

import o1.adventure.*
import scala.io.StdIn.*

/** The singleton object `AdventureTextUI` represents a fully text-based version
  * of the Adventure game application. The object serves as an entry point for
  * the game, and it can be run to start up a user interface that operates in
  * the text console.
  * @see
  *   [[AdventureGUI]]
  */
object AdventureTextUI extends App:

  private val Indent = "    "
  private val game = Adventure(DifficultyLevels.Medium)
  private val player = game.player
  this.run()

  /** Adds indentation to each line of the given text. */
  private def indented(text: String): String =
    text.linesIterator
      .map(line => Indent + line)
      .mkString("\n")

  /** Runs the game. First, a welcome message is printed, then the player gets
    * the chance to play any number of turns until the game is over, and finally
    * a goodbye message is printed.
    */
  private def run() =
    this.clearScreen()
    println(indented(this.game.welcomeMessage))
    while !this.game.isOver do
      this.printAreaInfo()
      this.printShortHelp()
      this.playTurn()
    println("\n" + indented(this.game.goodbyeMessage))

  /** Prints out a description of the player character's current location, as
    * seen by the character.
    */
  private def printAreaInfo() =
    val area = this.player.location
    println("\n\n" + Indent + area.name)
    println(Indent + "-" * area.name.length)
    println(indented(area.fullDescription) + "\n")

  private def printShortHelp() =
    val location = this.player.location
    val exitList = location.neighbors.map(_.name).mkString(", ")
    val itemList = location.items.map(_.name).mkString(", ")
    val inventoryList = this.player.items.map(_.name).mkString(", ")
    val consumableList =
      this.player.consumables.map(_.name).mkString(", ")
    val weaponList = this.player.weapons.map(_.name).mkString(", ")

    val commands = Vector(
      Option.when(exitList.nonEmpty)(s"go <${exitList}>"),
      Option.when(itemList.nonEmpty)(s"get <${itemList}>"),
      Option.when(inventoryList.nonEmpty)(
        s"drop <${inventoryList}>"
      ),
      Option.when(consumableList.nonEmpty)(
        s"use <${consumableList}>"
      ),
      Option.when(weaponList.nonEmpty)(
        s"fire <${weaponList}>"
      ),
      Option.when(inventoryList.nonEmpty)(
        s"examine <${inventoryList}>"
      ),
      Some("inventory"),
      Some("rest"),
      Some("quit"),
      Some("help")
    ).flatten
    println(indented("Available commands: " + commands.mkString(", ")))

  /** Requests a command from the player, plays a game turn accordingly, and
    * prints out a report of what happened.
    */
  private def playTurn() =
    println()
    val command = readLine(Indent + "Command: ")
    this.clearScreen()
    val turnReport = this.game.playTurn(command)
    if turnReport.nonEmpty then println(indented(turnReport))

  /** Clears the console screen using ANSI escape codes. */
  private def clearScreen() =
    print("\u001b[2J\u001b[H\n\n")
    Console.flush()

end AdventureTextUI
