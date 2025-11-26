package o1.adventure

/** The class `Item` represents items in a text adventure game. Each item has a
  * name and a longer description. (In later versions of the adventure game,
  * items may have other features as well.)
  *
  * N.B. It is assumed, but not enforced by this class, that items have unique
  * names. That is, no two items in a game world have the same name.
  *
  * @param name
  *   the item’s name
  * @param description
  *   the item’s description
  */
class Item(
    val itemType: String,
    val name: String,
    val description: String,
    val isConsumable: Boolean = false
) extends Named:

  /** Returns a short textual representation of the item (its name, that is). */
  override def toString = this.name
end Item

abstract class Consumable(itemType: String, name: String, description: String)
    extends Item(itemType, name, description, true):
  def consumeAction: Player => (String, Boolean)

class EnginePart(
    name: String,
    description: String
) extends Consumable("engine part", name, description):
  override def consumeAction = (player: Player) =>
    player.location.areaElements.collectFirst { case spaceship: Spaceship =>
      spaceship
    } match
      case Some(spaceship) =>
        val result = spaceship.repair(this)
        (result, true)
      case None =>
        (
          s"You can't install the ${this.name} here. You need to be at the spaceship to install engine parts.",
          false
        )

class OxygenTank(tankType: String, amountOfAir: Int)
    extends Consumable(
      "air",
      s"$tankType oxygen tank",
      s"A $tankType oxygen tank that can replenish $amountOfAir units of oxygen."
    ):
  override def consumeAction = (player: Player) =>
    player.oxygenLevel =
      Math.min(player.oxygenLevel + amountOfAir, player.maxOxygenLevel)
    (
      s"You used the $tankType oxygen tank and replenished $amountOfAir units of oxygen. Your oxygen level is now ${player.oxygenLevelPercent}%.",
      true
    )

class Weapon(
    name: String,
    description: String,
    val damage: Int
) extends Item("weapon", name, description, false):
  def fireAt(foe: Foe): String =
    foe.receiveDamage(this.damage)

object EngineParts:
  val fluxCapacitor = new EnginePart(
    "flux capacitor",
    "A crystalline capacitor that regulates temporal energy flow through the ship's drive core."
  )
  val antimatterInjector = new EnginePart(
    "antimatter injector",
    "A sealed injector assembly used to meter antimatter into the reaction chamber."
  )
  val gravimetricStabilizer = new EnginePart(
    "gravimetric stabilizer",
    "A heavy-field stabilizer that keeps the drive's gravity wells from collapsing."
  )
  val naniteActuator = new EnginePart(
    "nanite actuator",
    "A micro-actuator lattice driven by repair nanites; essential for fine-tuned engine control."
  )

class BigOxygenTank extends OxygenTank("big", 10)
class SmallOxygenTank extends OxygenTank("small", 4)
class MiniOxygenTank extends OxygenTank("mini", 2)
class RegularOxygenTank extends OxygenTank("regular", 6)

object Weapons:
  val laserPistol = new Weapon(
    "laser pistol",
    "A standard-issue laser pistol, capable of firing concentrated beams of light.",
    5
  )
  val plasmaRifle = new Weapon(
    "plasma rifle",
    "A high-powered rifle that shoots superheated plasma bolts.",
    10
  )
  val ionCannon = new Weapon(
    "ion cannon",
    "A heavy weapon that discharges bursts of ionized particles to disable targets.",
    15
  )
