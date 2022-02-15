package com.open.quester.quest.sheepshearer

import org.powbot.api.Area
import org.powbot.api.Tile

object SheepShearerConstants {
    const val ITEM_SHEARS = "Shears"
    const val ACTION_SHEAR = "Shear"
    const val ACTION_SPIN = "Spin"

    const val ITEM_BALL_OF_WOOL = "Ball of wool"
    const val ITEM_WOOL = "Wool"

    const val NAME_FRED = "Fred the Farmer"

    val TILE_FRED = Tile(3189, 3272)
    val TILE_SPINNING_WHEEL = Tile(3210, 3214, 1)

    const val NAME_SPINNING_WHEEL = "Spinning wheel"

    val CONVERSATION_FRED = arrayOf(
        "I'm looking for a quest.",
        "Yes okay. I can do that.",
        "Of course!",
        "I'm something of an expert actually!",
        "How do I shear sheep, again?",
        "Yes."
    )

    val AREA_SHEEP_PEN = Area(
        Tile(3198, 3267),
        Tile(3209, 3260),
    )
}