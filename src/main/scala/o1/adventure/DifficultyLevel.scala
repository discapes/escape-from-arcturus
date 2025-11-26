package o1.adventure

class DifficultyLevel(
    val name: String,
    val maxOxygen: Int,
    val weaponAvailable: Weapon
) extends Named

object DifficultyLevels:
  case object Easy
      extends DifficultyLevel(
        "Easy",
        maxOxygen = 30,
        weaponAvailable = Weapons.ionCannon
      )
  case object Medium
      extends DifficultyLevel(
        "Medium",
        maxOxygen = 20,
        weaponAvailable = Weapons.plasmaRifle
      )

  case object Hard
      extends DifficultyLevel(
        "Hard",
        maxOxygen = 10,
        weaponAvailable = Weapons.laserPistol
      )
