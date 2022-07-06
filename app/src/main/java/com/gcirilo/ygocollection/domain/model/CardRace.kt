package com.gcirilo.ygocollection.domain.model

enum class CardRace(val type: String) {
    Aqua("monster"),
    Beast("monster"),
    BeastWarrior("monster"){ override fun toString() = "Beast-Warrior" },
    CreatorGod("monster"){ override fun toString() = "Creator-God" },
    Cyberse("monster"),
    Dinosaur("monster"),
    DivineBeast("monster"){ override fun toString() = "Divine-Beast" },
    Dragon("monster"),
    Fairy("monster"),
    Fiend("monster"),
    Fish("monster"),
    Insect("monster"),
    Machine("monster"),
    Plant("monster"),
    Psychic("monster"),
    Pyro("monster"),
    Reptile("monster"),
    Rock("monster"),
    SeaSerpent("monster"){ override fun toString() = "Sea Serpent" },
    Spellcaster("monster"),
    Thunder("monster"),
    Warrior("monster"),
    WingedBeast("monster"){ override fun toString() = "Winged Beast" },
    Wyrm("monster"),
    Zombie("monster"),

    Normal("spell/trap"),
    Field("spell/trap"),
    Equip("spell/trap"),
    Continuous("spell/trap"),
    QuickPlay("spell/trap"){ override fun toString() = "Quick-Play" },
    Ritual("spell/trap"),
    Counter("spell/trap");

    companion object {
        fun spellTrapCardRaces(): List<CardRace> =
            enumValues<CardRace>().filter { it.type == "spell/trap" }

        fun monsterCardRaces(): List<CardRace> =
            enumValues<CardRace>().filter { it.type == "monster" }

    }
}