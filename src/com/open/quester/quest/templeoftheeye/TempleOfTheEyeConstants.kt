package com.open.quester.quest.templeoftheeye

import org.powbot.api.Area
import org.powbot.api.Tile

object TempleOfTheEyeConstants {

    const val NAME_WIZARD_PERSTEN = "Wizard Persten"
    val TILE_WIZARD_PERSTEN = Tile(3285, 3232)
    val CONVERSATION_WIZARD_PERSTEN = arrayOf("What's a wizard doing in Al Kharid?", "Yes.")

    const val NAME_MAGE_OF_ZAMORAK = "Mage of Zamorak"
    val TILE_MAGE_OF_ZAMORAK = Tile(3259, 3383)
    val CONVERSATION_MAGE_OF_ZAMORAK = arrayOf("I need your help with an amulet.", "I'll be back soon.")
    val CONVERSATION_MAGE_OF_ZAMORAK2 = arrayOf("Could you help me with that amulet now?", "Yes.")

    const val NAME_TEA_SELLER = "Tea Seller"
    val TILE_TEA_SELLER = Tile(3272, 3411)
    val CONVERSATION_TEA_SELLER = arrayOf("Could I have a strong cup of tea?", "I'll be back soon.")

    const val ITEM_STRONG_CUP_OF_TEA = "Strong cup of tea"
    const val ITEM_BRONZE_PICKAXE = "Bronze pickaxe"
    const val ITEM_CHISEL = "Chisel"

    const val NAME_DARK_MAGE = "Dark Mage"
    val TILE_DARK_MAGE = Tile(3039, 4835)
    val CONVERSATION_DARK_MAGE = arrayOf("I need your help with an amulet.", "Could you help me with that amulet now?",
        "Yes.")// If its in varrock so teleport

    val AREA_FIRE_ALTAR = Area(Tile(2556, 4863), Tile(2618, 4807))
    const val NAME_FIRE_ALTAR_PORTAL = "Portal"
    val TILE_FIRE_ALTAR_PORTAL = Tile(2575, 849)
    val AREA_INNER_ABYSS = Area(
        Tile(3037, 4848),
        Tile(3023, 4840),
        Tile(3026, 4818),
        Tile(3040, 4815),
        Tile(3054, 4823),
        Tile(3056, 4833),
        Tile(3052, 4845)
    )

    const val NAME_FIRE_RIFT = "Fire rift"
    val TILE_FIRE_RIFT = Tile(3029, 4830)
    const val ACTION_FIRE_RIFT = "Exit-through"

    const val NAME_SEDRIDOR = "Archmage Sedridor"
    val TILE_SEDRIDOR = Tile(3104, 9571)
    val CONVERSATION_SEDRIDOR = arrayOf(
        "I need your help with an incantation.", "Could you tell me about the old Wizards' Tower?",
        "Thanks for the information"
    )
    val CONVERSATION_SEDRIDOR2 = arrayOf(
        "Let's do it."
    )

    const val NAME_WIZARD_TRAIBORN = "Wizard Traiborn"
    val TILE_TRAIBORN = Tile(3111, 3162, 1)
    val CONVERSATION_TRAIBORN = arrayOf(
        "I need your apprentices to help with an incantation.",
        "Thanks for the information",
        "I think I know what a thingummywut is!"
    )

    const val NAME_CELL_TABLE = "Weak cells"
    const val ITEM_WEAK_CELL = "Weak cell"
    const val ITEM_MEDIUM_CELL = "Medium cell"

    const val NAME_WEAK_CELL_TILE = "Weak cell tile"
    const val ACTION_WEAK_CELL = "Place-cell"

    const val OUTSIDE_PORTAL_ID = 43841
    const val MASSIVE_PORTAL_ID = 43694
    val TILE_PORTAL_OUTSIDE = Tile(3104, 9573, 0)
    val AREA_OUTSIDE_PORTAL = Area(Tile(3094, 9564), Tile(3112, 9579))

    const val NAME_APPRENTICE_CORDELIA = "Apprentice Cordelia"
    const val NAME_APPRENTICE_FELIX = "Apprentice Felix"
    const val NAME_APPRENTICE_TAMARA = "Apprentice Tamara"
    const val NAME_GREAT_GUARDIAN = "The Great Guardian"

    val OFFSET_CORDELIA = Tile(0, 48)
    val OFFSET_FELIX = Tile(1, 18)
    val OFFSET_TAMARA = Tile(-14, 28)
    val OFFSET_TAMARA2 = Tile(0, 37)
    val OFFSET_PRESTEN = Tile(0, 36)
    val OFFSET_MIND = Tile(-2, 27)
    val OFFSET_WATER = Tile(7, 31)
    val OFFSET_MINING = Tile(-13, 23)

    const val NAME_CELL_TILE = "Inactive cell tile"
    const val ACTION_CELL_TILE = "Place-cell"

    const val NAME_ELEMENTAL_PILE = "Essence pile (elemental)"
    const val NAME_CATALYTIC_PILE = "Essence pile (catalytic)"
    const val ACTION_PILE = "Assemble"

    const val NAME_GUARDIAN_REMAINS = "Guardian remains"
    const val ACTION_GUARDIAN_REMAINS = "Mine"

    const val ITEM_GUARDIAN_FRAGMENTS = "Guardian fragments"
    const val ITEM_GUARDIAN_ESSENCE = "Guardian essence"

    const val NAME_UNCHARGED_CELLS = "Uncharged cells"
    const val ACTION_UNCHARGED_CELLS = "Take"
    const val ITEM_UNCHARGED_CELL = "Uncharged cell"

    const val NAME_WORKBENCH = "Workbench"
    const val ACTION_WORKBENCH = "Work-at"

    const val NAME_GUARDIAN_OF_MIND = "Guardian of Mind"
    const val NAME_GUARDIAN_OF_WATER = "Guardian of Water"
    const val ACTION_ENTER_ALTAR = "Enter"

    const val NAME_FIRE_ENERGY = "Fire Energy"
    const val NAME_COSMIC_ENERGY = "Cosmic Energy"
    const val NAME_EARTH_ENERGY = "Earth Energy"
    const val NAME_LAW_ENERGY = "Law Energy"
    const val NAME_DEATH_ENERGY = "Death Energy"
    const val NAME_NATURE_ENERGY = "Nature Energy"

    val ALL_ENERGY = arrayOf(
        NAME_FIRE_ENERGY,
        NAME_COSMIC_ENERGY,
        NAME_EARTH_ENERGY,
        NAME_LAW_ENERGY,
        NAME_DEATH_ENERGY,
        NAME_NATURE_ENERGY
    )

    const val MESSAGE_SUCCESS = "reacts strangely to your touch."
    const val MESSAGE_FAILURE = "does not respond to your touch."

    /**
     * 0 -> 32768
     * 1000000000000000
     * 32768 -> 32773
     * 1000000000000101
     * 32773 -> 33290
     * 1000001000001010
     * 33290 -> 33295 (Needs to get tea)
     * 1000001000001111
     * 33295 -> 35343 (Gotten tea)
     * 1000101000001111
     * 35343 -> 35348 (Given tea)
     * 1000101000010100
     * 35348 -> 36377 (Teleported abyss)
     * 1000111000011001
     * 36377 -> 36382
     * 1000111000011110 (Wet mage)
     * 36382 -> 178949667 (Puzzle)
     * 1010101010101000111000100011
     *             1000111000011110
     *
     *
     * 178949667 -> 187338275
     * 1011001010101000111000100011
     *
     * x -> 357338659 (1 puzzle left))
     * 10101010011001000111000100011
     *
     * 357338659 -> 36392 Puzzle solved
     * 1000111000101000
     * 536972875 (Puzzle)
     *  10101010011001000111000100011
     * 1 000000000 0001100011 1001001011
     *
     * 536976971 (Talked to Tamra)
     *  100000000000011000111001001011
     *  100000000000011001 111001001011
     *
     *  537001572 -> -1610482076 (Talked to felix)
     *   100000000000011111111001100100
     * -1011111111111100000000110011100
     *                          1101001
     *                          1100011
     * -536740252 (Talk to Tamara)
     *   -11111111111100000000110011100
     * -1011111111111100000000110011100
     *
     * -2147352988 (Guess talk to Coredlia)
     *   -11111111111100000000110011100
     * -1011111111111100000000110011100
     */


    const val VARPBIT_SHIFT_APPRENTICE = 29
    const val VARPBIT_SHIFT_TAMARA = 12
    const val VARPBIT_SHIFT_FELIX = 13
    const val VARPBIT_SHIFT_CORDELIA = 14


    const val VARPBIT_SHIFT_TAMARA_INGAME = 30
    const val VARPBIT_SHIFT_CORDELIA_INGAME = 32
    const val VARPBIT_SHIFT_FELIX_INGAME = 31
}