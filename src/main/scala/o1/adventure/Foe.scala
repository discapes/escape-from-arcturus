package o1.adventure

class Foe(
    val name: String,
    val description: String,
    var health: Int,
    val damage: Int,
    var location: Area,
    val player: Player
):
  def isDefeated: Boolean = this.health <= 0

  def attack(player: Player): String =
    player.oxygenLevel -= this.damage
    s"The ${this.name} attacks you and deals ${this.damage} damage! Your oxygen level is now ${player.oxygenLevelPercent}%."

  def receiveDamage(amount: Int): String =
    this.health -= amount
    if this.isDefeated then s"You have defeated the ${this.name}!"
    else
      s"You deal $amount damage to the ${this.name}. It has ${this.health} health remaining."

  def info: String =
    s"${this.name}: ${this.description} (Health: ${this.health}, Damage: ${this.damage})"

  // if the player is in the room, there is a 50% chance the foe will attack
  //    otherwise, there is a 50% chance it does nothing and a 50% chance it moves to a random adjacent area
  def turn(): String =
    if player.location == this.location then
      if scala.util.Random.nextBoolean() then this.attack(player)
      else s"The ${this.name} glares at you but does not attack this turn."
    else if scala.util.Random.nextBoolean() then
      val neighbors = this.location.neighbors.toVector
      if neighbors.nonEmpty then
        val newLocation = neighbors(
          scala.util.Random.nextInt(neighbors.length)
        )
        this.location.removeFoe(this)
        newLocation.addFoe(this)
        this.location = newLocation
      ""
    else ""

class SlimeMonster(
    location: Area,
    player: Player
) extends Foe(
      "Slime Monster",
      "A gooey creature that oozes around and attacks anything that moves.",
      health = 13,
      damage = 3,
      location,
      player
    )
