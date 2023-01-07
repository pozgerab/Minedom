# Minedom explanation & documentation

Minedom is a project for some kind of rpg or minecraft map *(I'll decide later)*.\
This doc contains everything that I think is important, or any code explanation that is not obvious.\
Also, if I leave the code for a while, I'll know what is what and why.
***
### Custom Entities
Custom entities can be created in a class extending the ```RegistryEntity``` class.\
These entities can drop [skill exp](#skills) upon killing them. These are controlled by the abstract function properties.

Every custom entity is a replacement to a vanilla or custom entity. The chances can also be set in the class in a range of 0-1.

These entities should be registered in the ```RegisterEntities``` class by creating a new instance of them inside the class constructor.
***
### Registered Players
The ```RegistryPlayer``` class is an extension of the ```RegistryEntity``` class.\
These two classes hold mostly different values, so make sure to cast to the class you want to use, but both of the classes have a variable with the bukkit entity itself.

In both of the casts the stat values are the same (so you don't have to cast to a ```RegistryPlayer``` class every time you use the ```getRegistryEntity``` method).
***
### Abilities
There is an abstract class for creating custom abilities quickly.\
Every ability extended class has a listener for plenty of events, so an ability can trigger multiple ways and execute different code.\
An ability can only be executed with events.

There is a **requirement** field that controls if the ability user can use the ability based off his [skills](#skills).

***It is required to register your abilities to a [custom item](#items) with its constructor to make it work. (There are no non-item based abilities yet, I will implement that to my project.)***
***
### Skills
NO ACTUAL FUNCTION YET

Gain skill exp by killing certain entities.\
The required exp to level up a skill increases over certain levels.

Skills don't have any impact on the skills (yet), but can be used to access abilities by leveling up.\
These [abilities](#abilities) doesn't show up when leveling up, but can be found in the certain ability class itself.
***
### Stats
Different types of stats control the game, damage, health, speed etc. These can be modified by holding certain items, or wearing armor, perhaps with abilities.\
The operations can be found in event listeners, like the damage controlling ones.

Every ```LivingEntity``` has a stat hashmap.
Stats are stored in the [RegistryPlayer](#registered-players) / [RegistryEntity](#custom-entities) class privately with a key of a ```Stat``` and a value of ```Double```: ```public HashMap<Stats, Double> stats```

List of stats can be found in the ```Stats``` enum.
***
### Items
An abstract class for creating custom items.
These custom items can hold multiple abilities in their ```setAbilities()``` function. The ```setAbilities()``` function should return a ```ArrayList``` of ```Ability```:
```ArrayList<Ability>```

The items can also give any stat, that is applied when the player is holding it or if it is in one of the equipment slots.

Also, it is possible to assign a [stat requirement](#stats) to the item, but it doesn't work properly.

These items should be registered in the ```RegisterItems``` class by creating a new instance of the item class inside the class constructor.
***
### Events
An ability trigger listener keeps track of events.\
The ```PublicAbilityListener``` class tracks  every important event and passes them to the ability handlers. Then the ```Ability``` class checks the instance of the event and executes only the specific event related methods.


There is a custom event for magic damage: ```MagicHitEvent```. It only triggers on direct call from ```Bukkit.getServer().getPluginManager().callEvent();``` function, doesn't trigger naturally.\
It has a ```boolean lock``` parameter. When it's ```true```, it prevents any ```MagicHitEvent``` listener (so ability listeners too) from triggering when calling this event. It's used to prevent infinite damage loops when an ability deals extra magic damage to any entity.

```RegistryDeathEvent``` class is also a custom event. It's called, whenever a ```MagicHitEvent``` or ```EntityDamageByEntityEvent``` happens. If the damage is greater than the attacked entity's health the event is called.
***
### Damage & Calculations
In the 'events' folder inside 'Damage', there is a DamageOut class for listening to damage events.

There is a math folder for calculating damage, damage reduction, magic damage for stats.\
Its functions are used to deal and calculate damage. There are many factors that the final damage can change, feel free to look in the ```DmgCalc``` class how the damage calculations are done.\
Deal magic damage: ```DmgCalc#magicDamageEntity(RegistryEntity target, double rawDamage, RegistryPlayer attacker);```
***