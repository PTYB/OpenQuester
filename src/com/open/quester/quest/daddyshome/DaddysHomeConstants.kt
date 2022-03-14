package com.open.quester.quest.daddyshome

import org.powbot.api.Tile

object DaddysHomeConstants {
    const val ITEM_PLANK = "Plank"
    const val ITEM_BOLT_OF_CLOTH = "Bolt of cloth"
    const val ITEM_STEEL_NAILS = "Steel nails"
    const val ITEM_BRONZE_NAILS = "Bronze nails"
    const val ITEM_HAMMER = "Hammer"
    const val ITEM_SAW = "Saw"
    const val ITEM_WAXWOOD_LOG = "Waxwood log"
    const val ITEM_WAXWOOD_PLANKS = "Waxwood plank"

    val TILE_SAW = Tile(3241, 3470, 0)

    const val NAME_MARLO = "Marlo"
    val TILE_MARLO = Tile(3241, 3471, 0)
    val CONVERSATION_MARLO = arrayOf(
        "What kind of favour do you want me to do?",
        "Tell me more about the job.",
        "Tell me where he lives, and I'll do the job."
    )
    val CONVERSATION_MARLO_FINISH = arrayOf("Yeah, what have you got for me?")

    const val NAME_YARLO = "Old Man Yarlo"
    val TILE_YARLO = Tile(3240, 3395, 0)
    val CONVERSATION_YARLO = arrayOf("Skip Yarlo's lecture. He'll offer it later if you like.")

    val TILE_CRATE = Tile(3243, 3398, 0)
    const val ID_CRATE = 40214

    val FAR_ROOM_TILE = Tile(3245,3395,0)

    const val NAME_SAWMILL_OPERATOR = "Sawmill Operator"
    val TILE_SAWMILl = Tile(3302, 3490, 0)
    val CONVERSATION_SAWMILL = arrayOf("I need some waxwood planks for Old Man Yarlo.")

    const val NAME_ROTTEN_CARPET = "Rotten Carpet"
    const val NAME_BROKEN_STOOL = "Broken stool"
    const val NAME_BROKEN_TABLE = "Broken table"
    const val NAME_BROKEN_CHAIR = "Broken chair"
    const val ID_BROKEN_CHAIR = 40302
    const val NAME_CAMPBED = "Campbed"
    const val ID_BROKEN_BED = 40303

    const val SHIFT_QUEST_INDEX = 19

    const val HOME_ITEM_MASK = 3
    const val FAR_STOOL_SHIFT = 5
    const val CLOSE_STOOL_SHIFT = 7
    const val CHAIR_SHIFT = 9
    const val FAR_TABLE_SHIFT = 11
    const val CLOSE_TABLE_SHIFT = 13
    const val CAMPBED_SHIFT = 15
    const val CARPET_SHIFT = 17

    const val NAME_CONST_STOOL = "Wooden stool"
    const val NAME_CONST_TABLE = "Wooden table"
    const val NAME_CONST_CHAIR = "Wooden chair"
    const val NAME_CONST_CARPET = "Carpet"
    const val NAME_CONST_BED = "Waxwood bed"
    const val ID_STOOL = 40300
    const val ID_TABLE = 40223
    const val ID_CHAIR = 40302
    const val ID_CARPET = 40304
    const val ID_BED = 40303


    const val WIDGET_CONSTRUCTION = 458

    // 1021 0 -> 512
    // 0 -> 524288

    // 524288 -> 1223328
    //  10000000000000000000
    // 100101010101010100000
    // 100101010101011000000
    // 111010101010101000000

    // Removed stool 2
    // 1223360
    // 100101010101011000000

    // Removed table 2
    // 1225408
    // 100101011001011000000

    // 1922368
    // 111010101010101000000
    // 2446656
    // 2970944
}