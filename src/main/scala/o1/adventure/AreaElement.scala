package o1.adventure

/* An AreaElement represents a kind of addon that exists in a specific area
 * it has a mutable state, and it is mentioned every time you enter the area.
 * Currently it's used only for the space ship, but it could for example also
 * represent a control panel in the control room, giving live updates about the station's
 * status every time the room is visited. */
trait AreaElement:
  def description: String

class Spaceship extends AreaElement:
  var missingParts = Set(
    EngineParts.fluxCapacitor,
    EngineParts.antimatterInjector,
    EngineParts.gravimetricStabilizer,
    EngineParts.naniteActuator
  )

  var foundParts = Set[EnginePart]()
  def percentRepaired: Double =
    val totalParts = missingParts.size + foundParts.size
    ((foundParts.size.toDouble / totalParts) * 100).round

  def fullyRepaired: Boolean = missingParts.isEmpty

  def description: String =
    if foundParts.isEmpty then
      "You see your damaged spaceship under the wreckage - or what's left of it, anyway. It seems that several crucial engine parts are missing. You need to find them in order to repair the ship and escape this place. Percent repaired: 0%. Missng parts: " +
        missingParts.map(_.name).mkString(", ") + "."
    else if missingParts.isEmpty then
      "You see your spaceship, fully repaired and ready for takeoff! Congratulations, you have found all the missing engine parts and restored your ship to working order. Percent repaired: 100%."
    else
      s"You see your spaceship, which is slowly taking shape as you find more engine parts. Percent repaired: ${percentRepaired}%. Missing parts: " +
        missingParts.map(_.name).mkString(", ") + "."

  def repair(part: EnginePart): String =
    if missingParts.contains(part) then
      missingParts -= part
      foundParts += part
      s"You have installed the ${part.name} into the spaceship's engine. ${description}"
    else s"The ${part.name} is not needed for the spaceship's repair."
