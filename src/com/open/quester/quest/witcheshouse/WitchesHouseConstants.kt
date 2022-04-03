package org.powbot.quests.quest.witcheshouse

import org.powbot.api.Area
import org.powbot.api.Tile

object WitchesHouseConstants {

    const val NAME_WITCH = "Nora T. Hagg"

    const val NAME_BOY = "Boy"
    val TILE_BOY = Tile(2928, 3456)
    val CONVERSATION_BOY = arrayOf("What's the matter?", "Ok, I'll see what I can do.", "Yes.")

    const val NAME_PLANT = "Potted plant"
    val TILE_PLANT = Tile(2900, 3474, 0)

    val TILE_INFRONT_HOUSE = Tile(2900, 3473, 0)

    const val NAME_MOUSE = "Mouse"
    const val NAME_MOUSE_HOLE = "Mouse hole"
    val TILE_MOUSE_ROOM = Tile(2902, 3466, 0)

    val AREA_UNDER_WITCHES_HOUSE = Area(Tile(2897, 9870, 0), Tile(2908, 9877, 0))
    val TILE_INFRONT_CUPBOARD = Tile(2899, 9874)
    val ID_CUPBOARD_CLOSED = 2868
    val ID_CUPBOARD_OPEN = 2869

    const val ITEM_HOUSE_KEY = "Door key"
    const val ITEM_MAGNET = "Magnet"
    const val ITEM_SHED_KEY = "Key" //2411
    const val ITEM_BALL = "Ball"

    const val ITEM_CHEESE = "Cheese"
    val ID_CHEESE = 1985
    const val ITEM_LEATHER_GLOVES = "Leather gloves"
    val ID_LEATHER_GLOVES = 1059

    val AREA_OUTSIDE = Area(
        Tile(2900, 3465),
        Tile(2900, 3459),
        Tile(2934, 3459),
        Tile(2934, 3468),
        Tile(2914, 3468),
        Tile(2914, 3477),
        Tile(2908, 3477),
        Tile(2908, 3468),
        Tile(2904, 3468),
        Tile(2904, 3465)
    )

    val AREA_LEFT_SAFE_SIDE = Area(Tile(2900, 3465), Tile(2902, 3459))
    val AREA_RIGHT_SIDE = Area(Tile(2930, 3459), Tile(2933, 3465))

    val TILE_BOTTOM_START = Tile(2903, 3460)
    val TILE_BOTTOM_END = Tile(2930, 3460)

    val TILE_BOTTOM_WALKING_TILES = arrayOf(
        TILE_BOTTOM_START, Tile(2908, 3460), Tile(2916, 3460),
        Tile(2924, 3460), TILE_BOTTOM_END
    )

    val TILE_TOP_START = Tile(2914, 3466)
    val TILE_TOP_END = Tile(2932, 3466)

    val TILE_TOP_WALKING_TILES = arrayOf(
        TILE_TOP_START,
        Tile(2920, 3466),
        Tile(2927, 3466),
        TILE_TOP_END,
    )

    val TILE_NORTH_LURE_SAFESPOT = Tile(2937, 3466)
    val TILE_NORTH_NPC_LURE_SPOT = Tile(2937, 3465)
    val TILE_NORTH_SAFESPOT = Tile(2936, 3465)
    val TILE_SOUTH_SAFESPOT = Tile(2936, 3459)

    val AREA_LEFT_TURNING_AREA = Area(Tile(2903, 3464), Tile(2907, 3461))
    val AREA_RIGHT_TURNING_AREA = Area(Tile(2929, 3461), Tile(2932, 3465))
    val AREA_FOUNTAIN_AREA = Area(Tile(2908, 3466), Tile(2913, 3476))
    val AREA_SHACK = Area(Tile(2934, 3459), Tile(2937, 3467))
}