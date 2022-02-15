package com.open.quester.quest.xmarksthespot

import org.powbot.api.Tile

object XMarksTheSpotConstants {

    const val NAME_VEOS = "Veos"

    val VEOS_START_QUEST = arrayOf(
        "Can I help?", "I'm looking for a quest.", "Sounds good, what should I do?",
        "Okay, thanks Veos.", "Yes."
    )

    val TILE_LUMB_VEOS = Tile(3228, 3241, 0)
    val TILE_PORT_VEOS = Tile(3054, 3245, 0)
    val TILE_DIG_BOBS = Tile(3230, 3209)
    val TILE_DIG_DRAYNOR = Tile(3109, 3264)
    val TILE_DIG_CASTLE = Tile(3203, 3212)
    val TILE_DIG_PIG_PEN = Tile(3078, 3259)

    const val VARPBIT_STATE_MASK = 0x3F
}