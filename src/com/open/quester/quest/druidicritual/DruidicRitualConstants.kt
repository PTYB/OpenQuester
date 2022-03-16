package com.open.quester.quest.druidicritual

import org.powbot.api.Tile

object DruidicRitualConstants {

    const val NPC_KAQEMEEX = "Kaqemeex"
    val TILE_KAQEMEEX = Tile(2925, 3486, 0)
    val FIRST_CONVERSATION = arrayOf(
        "I'm in search of a quest.", "Okay, I will try and help.", "Yes."
    )

    const val NPC_SANFEW = "Sanfew"
    val TILE_SANFEW = Tile(2899, 3429, 0)
    val TILE_SANFER_UPSTAIRS = Tile(2899, 3427, 1)
    val SANFEW_CONVERESATION = arrayOf(
        "I've been sent to help purify the Varrock stone circle.",
        "Ok, I'll do that then."
    )

    const val ITEM_ENCHANTED_BEAR = "Enchanted bear"
    const val ITEM_ENCHANTED_BEEF = "Enchanted beef"
    const val ITEM_ENCHANTED_CHICKEN = "Enchanted chicken"
    const val ITEM_ENCHANTED_RAT = "Enchanted rat"

    const val NAME_PRISON_DOOR = "Prison door"
    const val NAME_CAULDRON_DOOR = "Cauldron of Thunder"

    const val ITEM_RAW_BEEF = "Raw beef"
    const val ITEM_BEAR_MEAT = "Raw bear meat"
    const val ITEM_RAW_CHICKEN = "Raw chicken"
    const val ITEM_RAT_MEAT = "Raw rat meat"
}