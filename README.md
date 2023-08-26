# Minedom explanation & documentation
***
Minedom is a plugin to recreate a mmo system.\
This doc contains everything that I think is important, or any code explanation if not obvious.\
Also, if I leave the code for a while, I'll know what is what and why.
### Custom Entities
***
Custom entities can be created in a class extending the ```RegistryEntity``` class.\
These entities can drop [skill exp](#skills) upon killing them. These are controlled by the abstract function properties.

Summonable with the /spawn command

These entities should be registered in the ```RegisterEntities``` class by creating a new instance of them inside the class constructor.
### Registered Players
***
The ```RegistryPlayer``` class is an extension of the ```RegistryEntity``` class.\
These two classes hold mostly unique values, but both of the classes have a variable with the bukkit entity itself.

In both of the casts the stat values are the same (so you don't have to cast to a ```RegistryPlayer``` class every time you use the ```getRegistryEntity``` method).
### Abilities
***
There is an abstract class for creating custom abilities quickly.\
Every ability extended class has a listener for plenty of events, so an ability can trigger multiple ways and execute different code.\
An ability can only be executed with events.

There is a **requirement** field that controls if the ability user can use the ability based off his [skills](#skills).

***It is required to register your abilities to a [custom item](#items) with its constructor to make it work. (The non-item based abilities aren't implemented yet)***
### Skills
***
NO ACTUAL FUNCTION YET

Gain skill exp by killing certain entities.\
The required exp to level up a skill increases over certain levels.

Skills don't have any impact on the skills (yet), but can be used to access abilities by leveling up.\
These [abilities](#abilities) doesn't show up when leveling up, but can be found in the certain ability class itself.
### Stats
***
Different types of stats control the game, damage, health, speed etc. These can be modified by holding certain items, or wearing armor, perhaps with abilities.\
The operations can be found in event listeners.

Every ```LivingEntity``` has a stat hashmap.
Stats are stored in the [RegistryEntity](#custom-entities) class privately with a key of a ```Stat``` and a value of ```Double```: ```public HashMap<Stats, Double> stats```

List of stats can be found in the ```Stats``` enum.
### Items
***
An abstract class for creating custom items.
These custom items can hold multiple abilities in their ```setAbilities()``` function. The ```setAbilities()``` function should return a ```ArrayList``` of ```Ability```.

The items can also give any stat, that is applied when the player is holding it or if it is in one of the equipment slots.

There are a bunch of abstract subclasses of the items class for specifying the exact use case of the item.

Also, it is possible to assign a [stat requirement](#stats) to the item, but it is only visual (not actual function implemented).

These items should be registered in the ```RegisterItems``` class by creating a new instance of the item class inside the class constructor.
### Events
***
An ability trigger listener keeps track of events.\
The ```PublicAbilityListener``` class tracks  every important event and passes them to the ability handlers. Then the ```Ability``` class checks the instance of the event and executes only the specific event related methods.


There is a custom event for magic damage: ```MagicHitEvent```. It only triggers on direct call from ```Bukkit.getServer().getPluginManager().callEvent();``` function, doesn't trigger naturally.\
It has a ```boolean lock``` parameter. When it's ```true```, it prevents any ```MagicHitEvent``` listener (so ability listeners too) from triggering when calling this event. It's used to prevent infinite damage loops when an ability deals extra magic damage to any entity.

```RegistryDeathEvent``` class is also a custom event. It's called, whenever a ```MagicHitEvent``` or ```RegistryHitEvent``` emits. If the damage is greater than the attacked entity's health the event is called.
### Damage & Calculations
***
The ```DamageOut``` class is responsible for listening to damage events.

There is a math folder for calculating damage, damage reduction, magic damage for stats. Its functions are used to deal and calculate damage. There are many factors that the final damage can change, feel free to look in the ```DmgCalc``` class how the damage calculations are done. 
###### Damaging
Deal damage with ```RegistryEntity#damage()``` and pass the damage as a ```double```, the damager as a ```RegistryEntityy```, and a source from the custom enum ```RegistryHitEvent.DamageSource```
### Healing
***