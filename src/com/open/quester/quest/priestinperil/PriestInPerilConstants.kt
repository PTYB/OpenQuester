package org.powbot.quests.quest.priestinperil

import org.powbot.api.Area
import org.powbot.api.Tile

object PriestInPerilConstants {

    const val ID_RUNE_ESSENCE = 1436
    const val ITEM_RUNE_ESSENCE = "Rune essence"
    const val ID_PURE_ESSENCE = 7936
    const val ITEM_PURE_ESSENCE = "Pure essence"

    val TILE_DREZEL_THIRD_FLOOR = Tile(3417, 3489, 2)
    val TILE_DREZEL_UNDERGROUND = Tile(3438, 9897, 0)

    const val NAME_KING_RONALD = "King Roald"
    val TILE_KING_RONALD = Tile(3222, 3473, 0)
    val TILE_NEAR_TEMPLE_DOOR = Tile(3407, 3489, 0)
    val TILE_TEMPLE_DOOR = Tile(3408, 3488, 0)

    val TILE_CENTER_BOTTOM_FLOOR = Tile(3412, 3488, 0)
    val TILE_SECOND_FLOOR = Tile(3414, 3489, 2)
    val TILE_UNDERGROUND = Tile(3420,9891, 0)

    val TILE_DOG = Tile(3405, 9901, 0)
    const val NAME_TEMPLE_GUARDIAN = "Temple Guardian"

    const val NAME_MONK = "Monk of Zamorak"
    const val NAME_DREZEL = "Drezel"
    const val ITEM_GOLDEN_KEY = "Golden key"
    const val ITEM_IRON_KEY = "Iron key"
    const val ITEM_BUCKET = "Bucket"

    const val NAME_STAIRCASE = "Staircase"
    const val NAME_GATE = "Gate"
    const val NAME_TRAP_DOOR = "Trapdoor"
    val TILE_TRAPDOOR = Tile(3405, 3507, 0)

    val AREA_BOTTOM_TEMPLE = Area(
        Tile(3408, 3491),
        Tile(3408, 3492),
        Tile(3409, 3494),
        Tile(3409, 3495),
        Tile(3412, 3495),
        Tile(3412, 3494),
        Tile(3416, 3494),
        Tile(3416, 3495),
        Tile(3418, 3495),
        Tile(3418, 3494),
        Tile(3419, 3494),
        Tile(3419, 3484),
        Tile(3418, 3484),
        Tile(3418, 3483),
        Tile(3416, 3483),
        Tile(3416, 3484),
        Tile(3412, 3484),
        Tile(3412, 3483),
        Tile(3409, 3483),
        Tile(3409, 3484),
        Tile(3408, 3485),
        Tile(3408, 3487),
        Tile(3409, 3487),
        Tile(3409, 3491)
    )
    val AREA_SECOND_FLOOR_TEMPLE = Area(Tile(3406, 3495, 1), Tile(3420, 3482, 1))
    val AREA_THIRD_FLOOR_OUTSIDE_JAIL = Area(Tile(3407, 3495, 2), Tile(3415, 3482, 2))
    val AREA_THIRD_INSIDE_JAIL = Area(Tile(3416, 3495, 2), Tile(3418, 3482, 2))
    val AREA_FIRST_UNDERGROUND = Area(Tile(3401, 9907), Tile(3408, 9895))
    val AREA_SECOND_UNDERGROUND = Area(
        Tile(3405, 9894),
        Tile(3415, 9900),
        Tile(3432, 9900),
        Tile(3431, 9882),
        Tile(3430, 9881),
        Tile(3415, 9881),
        Tile(3402, 9881),
        Tile(3402, 9894)
    )

    val CONVERSATION_START_QUEST = arrayOf("I'm looking for a quest!", "Yes.")
    val CONVERSATION_KILL_NPC =
        arrayOf(
            "I'll get going.", "Roald sent me to check on Drezel.",
            "Sure. I'm a helpful person!"
        )
    val CONVERSATION_TALK_TO_DREZEL = arrayOf("So, what now?", "Yes, of course.")

    const val NAME_COFFIN = "Coffin"
    const val NAME_MURKY_WATER = "Murky water"
    const val NAME_BLESSED_WATER = "Blessed water"
    const val NAME_CELL_DOOR = "Cell door"
    const val NAME_WELL = "Well"
    const val ACTION_TAKE_FROM = "Take-from"
    const val ACTION_STUDY = "Study"
    const val KEY_WIDGET = 272
    const val ITEM_COMPONENT = 8
    val IDS_MONUMENT = arrayOf(3493, 3499, 3494, 3497, 3495, 3498, 3496)
}
