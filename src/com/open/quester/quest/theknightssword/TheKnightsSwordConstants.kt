package com.open.quester.quest.theknightssword

import org.powbot.api.Area
import org.powbot.api.Tile

object TheKnightsSwordConstants {

    const val NAME_SQUIRE = "Squire"
    const val NAME_RELDO = "Reldo"
    const val NAME_THURGO = "Thurgo"
    const val NAME_SIR_VYVIN = "Sir Vyvin"

    const val ITEM_PORTRAIT = "Portrait"

    const val ITEM_REDBERRY_PIE = "Redberry pie"
    const val ITEM_IRON_BAR = "Iron bar"
    const val ITEM_BLURITE_ORE = "Blurite ore"
    const val ITEM_BLURITE_SWORD = "Blurite sword"

    val TILE_SQUIRE = Tile(2978, 3341, 0)
    val TILE_RELDO = Tile(3211, 3494, 0)
    val TILE_THURGO = Tile(3000, 3145, 0)

    val TILE_PICTURE_ROOM = Tile(2985, 3335, 2)
    val TILE_IN_DUNGEON = Tile(3006,9549)
    val TILE_BLURITE_TILE = Tile(3049, 9566, 0)
    const val NAME_BLURITE_ROCK = "Rocks"

    val AREA_ICE_DUNGEON = Area(Tile(2978, 9601), Tile(3086, 9531))

    val AREA_PICTURE_ROOM = Area(
        Tile(2981, 3331, 2),
        Tile(2981, 3337, 2),
        Tile(2987, 3337, 2),
        Tile(2987, 3334, 2),
        Tile(2984, 3331, 2)
    )

    val CONVERSATION_TALK_TO_SQUIRE = arrayOf(
        "Talk about other things.", // Christmas event
        "And how is life as a squire?",
        "I can make a new sword if you like...",
        "So would these dwarves make another one?",
        "Ok, I'll give it a go.",
        "Yes.",
    )

    val CONVERSATION_RELDO = arrayOf(
        "What do you know about the Imcando dwarves?",
        "Ok, I'll give it a go.",
    )

    val CONVERSATION_THURGO = arrayOf(
        "Would you like a redberry pie?",
    )

    val CONVERSATION_THURGO_AGAIN = arrayOf(
        "Can you make a special sword for me?",
    )

    val CONVERSATION_THURGO_PORTRAIT = arrayOf(
        "About that sword...",
    )

    val CONVERSATION_THURGO_ORE = arrayOf(
        "Can you make that replacement sword now?"
    )
}