package com.open.quester.quest.runemysteries

import org.powbot.api.Tile

object RuneMysteriesConstants {
    const val NAME_DUKE_HORACIO = "Duke Horacio"
    val TILE_DUKE_HORACIO = Tile(3209, 3222, 1)
    val CONVERSATION_DUKE_START = arrayOf(
        "Have you any quests for me?",
        "Sure, no problem.",
        "Yes."
    )

    const val NAME_SEDRIDOR = "Sedridor"
    var TILE_SEDRIDOR = Tile(3104, 9571, 0)
    val CONVERSATION_SEDRIDOR = arrayOf(
        "I'm looking for the head wizard.",
        "Ok, here you are.",
        "Yes, certainly."
    )

    const val ID_AUBURY = 2886
    const val NAME_AUBURY = "Aubury"
    val TILE_AUBURY = Tile(3253, 3401, 0)
    val CONVERSATION_AUBURY = arrayOf("I have been sent here with a package for you.")
}