package com.open.quester.quest.princealirescue

import org.powbot.api.Area
import org.powbot.api.Tile

object PrinceAliRescueConstants {

    const val ITEM_SOFT_CLAY = "Soft clay"
    const val ITEM_BALLS_OF_WOOOL = "Ball of wool"
    const val ITEM_YELLOW_DYE = "Yellow dye"
    const val ITEM_REDBERRIES = "Redberries"
    const val ITEM_ASHES = "Ashes"
    const val ITEM_BUCKET_OF_WATER = "Bucket of water"
    const val ITEM_POT_OF_FLOUR = "Pot of flour"
    const val ITEM_BRONZE_BAR = "Bronze bar"
    const val ITEM_PINK_SKIRT = "Pink skirt"
    const val ITEM_BEERS = "Beer"
    const val ITEM_ROPE = "Rope"

    const val ITEM_WIG = "Wig"
    const val ITEM_DYED_WIG = "Wig (dyed)"
    const val ITEM_PASTE = "Paste"
    const val ITEM_KEY_PRINT = "Key print"
    const val ITEM_BRONZE_KEY = "Bronze key"

    val AREA_INSIDE_JAIL = Area(Tile(3121, 3246), Tile(3130, 3240))
    val TILE_INSIDE_JAIL = Tile(3128, 3246)
    val AREA_PRISON = Area(Tile(3121, 3240), Tile(3125, 3243))

    val NAME_PRISON_DOOR = "Prison Gate"

    const val NAME_HASSAN = "Hassan"
    val TILE_HASSAN = Tile(3298, 3163)
    val CONVERSATION_HASSAN = arrayOf("Is there anything I can help you with?", "Yes.")

    const val Name_OSMAN = "Osman"
    val TILE_OSMAN = Tile(3286, 3180)
    val CONVERSATION_OSMAN = arrayOf("What is the first thing I must do?")

    const val NAME_NED = "Ned"
    val TILE_NED = Tile(3097, 3257)
    val CONVERSATION_NED = arrayOf(
        "Could you make other things apart from rope?", "How about some sort of wig?",
        "I have them here. Please make me a wig.",
        "Okay, I better go find some things."
    )

    const val NAME_AGGIE = "Aggie"
    val TILE_AGGIE = Tile(3086, 3257)
    val CONVERSATION_AGGIE =
        arrayOf("Can you make skin paste?", "Yes please. Mix me some skin paste.")

    const val NAME_KELI = "Lady Keli"
    val TILE_KELI = Tile(3127, 3244)
    val CONVERSATION_KELI = arrayOf(
        "Heard of you? You're famous in Gielinor!",
        "What's your latest plan then?",
        "How do you know someone won't try to free him?",
        "Could I see the key please?",
        "Could I touch the key for a moment please?"
    )


    const val NAME_LEELA = "Leela"
    val TILE_LEELA = Tile(3113, 3262)
    val CONVERSATION_LEELA = arrayOf("I will go and get the rest of the escape equipment.")

    const val NAME_JOE = "Joe"
    val TILE_JOE = Tile(3124, 3245)
    val CONVERSATION_JOE = arrayOf("I have some beer here. Fancy one?")

    const val ID_DOOR = 2881
    val TILE_DOOR = Tile(32123, 3243)

    const val NAME_PRINCE_ALI = "Prince Ali"
    val TILE_PRINCE_ALI = Tile(3136, 3245)
}