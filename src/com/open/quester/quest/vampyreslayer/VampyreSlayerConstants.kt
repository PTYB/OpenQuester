package com.open.quester.quest.vampyreslayer

import org.powbot.api.Tile

object VampyreSlayerConstants {

    const val NAME_STAKE = "Stake"

    const val NAME_MORGAN = "Morgan"
    val TILE_MORGAN = Tile(3098, 3268, 0)
    val CONVERSATION_MORGAN = arrayOf("Yes.", "Ok, I'm up for an adventure.", "Accept quest")

    const val NAME_HARLOW = "Dr Harlow"
    val TILE_HARLOW = Tile(3222,3399,0)
    val CONVERSATION_HARLOW = arrayOf("Morgan needs your help!")

    const val NAME_BARTENDER = "Bartender"
    val TILE_BARTENDER = Tile(3226,3401,0)
    val CONVERSATION_BARTENDER = arrayOf("A glass of your finest ale please.")

    const val NAME_DRACULA = "Count Draynor"

    const val NAME_COFFIN = "Coffin"
    val TILE_COFFIN = Tile(3077,9773, 0)
}