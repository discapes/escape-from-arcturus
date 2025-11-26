**Description of the game, unique features and objectives:**  

You are stranded on an abandonded space station. You need to find 4 ship
parts to get out.  You are also running low on oxygen, which is depleted
every time you pick up items or move around, so careful planning, many
playthroughs or good luck will be needed to escape in time. Escaping is
made easier by different-sized oxygen tanks scattered around the station
(randomly placed, each playthrough is different).

This game has been modified quite a bit from the O1 template. For
example, the screen is cleared after each turn, and to make playing the
game less tedious, actions, areas and items can be specified by only
typing their prefix, when it's unambiguous, so firing a plasma rifle
can be done with 'fi pl' instead of 'fire plasma rifle'. There's also
a monster that wanders around the station, starting from the opposite
side as the player. It has it's own turn every cycle, like the player,
and if you encounter it, it may attack and hit one of your oxygen tanks,
decreasing the amount of air you have left.  Fortunately, like the player,
it also has a health bar and can be defeated with the weapon found in
Airlock Alpha. Still, the best bet to win the game is to not encounter
the monster at all.

There's also support for different difficulty levels (Easy, Medium
and Hard), which can be changed in `AdventureTextUI.scala`. It affects
the initial and maximum amount of oxygen you can have, and the type of
weapon you find in Airlock Alpha. Also, we decided to get rid of the Swing
interface due to the time complexity of maintaining and testing two
different frontends to work with the additional turn logic.  Finally,
the ship is prepared by doing the 'use' action on the engine parts when
you are in Hangar 02 (where you initially land the ship). Try not to
run out of oxygen. Here's a narrated playthrough:

```scala
go air (go to the airlock)
get plasma rifle (hope we don't need it)
go hangar 01
get flux (get the first ship part, flux capacitor)
go air
go no (north hallway)
get reg (if there's a regular oxygen tank here)
go eng
get antimatter (get the second ship part, antimatter injector)
go no
go co
go so
go eng
get grav (gravimetric stabilizer)
get small (small oxygen tank, if present)
go so
use reg
use sma (fill up on oxygen, so we don't lose immedately if we get ambushed by the monster)
go west
go waste
get nanite
go west
fire plasma (oh no it's here)
fire plasma (fortunately we startled it so it didn't attack after the first hit, and we killed it on the second)
go so
go co
go no
go air
go hangar 02
use grav
use nanite
use anti
use flux

> You have installed the gravimetric stabilizer into the spaceship's engine. You see your spaceship, fully repaired and ready for takeoff! Congratulations, you have found all the missing engine parts and restored your ship to working order. Percent repaired: 100%.
> 
> Current oxygen level: 20%.
>
> You made it out alive. Against all odds, you survived.
```
