package org.powbot.quests.quest.hazeelcult

import org.powbot.api.Area
import org.powbot.api.Tile

object HazeelCultConstants {

    const val ITEM_ARMOUR = "Carnillean armour"

    const val NAME_CERIL = "Ceril Carnillean"
    val TILE_CERIL = Tile(2569, 3275, 0)
    val TILE_CERIL_UPSTAIRS = Tile(2572, 3268, 1)
    val CONVERSATION_CERIL = arrayOf("What's wrong?", "Yes, of course, I'd be happy to help.", "Yes.")

    const val NAME_BUTLER_JONES = "Butler Jones"
    val TILE_BUTLER_JONES = Tile(2568, 3267, 1)

    const val ID_CAVE = 2852
    val TILE_CAVE = Tile(2587, 3235, 0)

    const val NAME_CLIVET = "Clivet"
    val TILE_CLIVET = Tile(2569, 9682, 0)
    val CONVERSATION_SIDE_CERIL = arrayOf(
        "What do you mean?",
        "You're crazy, I'd never help you.",
        "No. I won't do it.",
    )

    const val NAME_RAFT = "Raft"
    val TILE_RAFT = Tile(2568, 9679, 0)
    val ACTIONR_AFT = "Board"

    const val NAME_ALOMONE = "Alomone"
    val TILE_ALOMONE = Tile(2607, 9673, 0)
    val AREA_ALOMONE = Area(Tile(2599, 9693), Tile(2619, 9665))

    val TILE_VALVE1 = Tile(2562, 3247, 0)
    val TILE_VALVE2 = Tile(2572, 3263, 0)
    val TILE_VALVE3 = Tile(2585, 3246, 0)
    val TILE_VALVE4 = Tile(2597, 3263, 0)
    val TILE_VALVE5 = Tile(2611, 3242, 0)
}