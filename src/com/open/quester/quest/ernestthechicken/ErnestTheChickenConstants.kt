package com.open.quester.quest.ernestthechicken

import org.powbot.api.Area
import org.powbot.api.Tile

object ErnestTheChickenConstants {

    const val ITEM_FISH_FOOD = "Fish food"
    const val ITEM_POISON = "Poison"
    const val ITEM_SPADE = "Spade"
    const val ITEM_POISONED_FISH_FOOD = "Poisoned fish food"
    const val ITEM_OIL_CAN = "Oil can"
    const val ITEM_PRESSURE_GAUGE = "Pressure gauge"
    const val ITEM_RUBBER_TUBE = "Rubber tube"
    const val ITEM_KEY = "Key"

    const val VARPBIT_LEVERS = 33

    val TILE_LEVER_A = Tile(3108, 9745, 0)
    val TILE_LEVER_B = Tile(3118, 9752, 0)
    val TILE_LEVER_C = Tile(3112, 9761, 0)
    val TILE_LEVER_D = Tile(3108, 9767, 0)
    val TILE_LEVER_E = Tile(3096, 9765, 0)
    val TILE_LEVER_F = Tile(3097, 9767, 0)

    val AREA_UNDERGROUND = Area(Tile(3089, 9744), Tile(3119, 9767))

    val AREA_MAIN_AREA = Area(Tile(3101, 9745), Tile(3118, 9757))
    val AREA_CD_LEVER_ROOM = Area(Tile(3105, 9758), Tile(3112, 9767))
    val AREA_MIDDLE_BOTTOM = Area(Tile(3100, 9762), Tile(3104, 9758))
    val AREA_MIDDLE_TOP = Area(Tile(3100, 9763), Tile(3104, 9767))
    val AREA_BOTTOM_LEFT = Area(Tile(3096, 9758), Tile(3099, 9762))
    val AREA_EF_LEVER_ROOM = Area(Tile(3096, 9763), Tile(3099, 9767))
    val AREA_OIL_CAN = Area(Tile(3090, 9757), Tile(3099, 9753))

    val TILE_GATE_ONE = Tile(3108, 9757)
    val TILE_GATE_TWO = Tile(3104, 9760)
    val TILE_GATE_THREE = Tile(3102, 9757)
    val TILE_GATE_FOUR = Tile(3099, 9760)
    val TILE_GATE_FIVE = Tile(3097, 9762)
    val TILE_GATE_SIX = Tile(3099, 9765)
    val TILE_GATE_SEVEN = Tile(3104, 9765)
    val TILE_GATE_EIGHT = Tile(3102, 9762)

    val TILE_GATE_CAN = Tile(3101,9755)

    const val NAME_VERONICA = "Veronica"
    val TILE_VERONICA = Tile(3109, 3329, 0)
    val CONVERSATION_VERONICA = arrayOf("Aha, sounds like a quest. I'll help.", "Yes.")

    val TILE_POISON = Tile(3097, 3366, 0)
    val TILE_FISH_FOOD = Tile(3108, 3356, 1)

    val ID_FOUNTAIN = 153
    const val NAME_FOUNTAIN = "Fountain"
    val TILE_FOUNTAIN = Tile(3087, 3337, 0)

    val TILE_SPADE = Tile(3120, 3359, 0)

    const val NAME_COMPOST_HEAP = "Compost heap"
    val TILE_COMPOST_HEAP = Tile(3086, 3360, 0)

    val TILE_GAUGE = Tile(3106, 3373, 0)

    const val NAME_ODDENSTEIN = "Professor Oddenstein"
    val TILE_ODDENSTEIN = Tile(3110,3364,2)
    val CONVERSATION_ODDENSTEIN = arrayOf("I'm looking for a guy called Ernest.","Change him back this instant!")
}