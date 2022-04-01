package com.open.quester.quest.entertheabyss

import org.powbot.api.Tile

object EnterTheAbyssConstants {
    const val ITEM_SCRYING_ORB = "Scrying orb"

    const val NAME_MAGE_OF_ZAMORAK = "Mage of Zamorak"
    val TILE_MAGE_IN_WILDY = Tile(3102, 3557)
    val TILE_MAGE_IN_VARROCK = Tile(3259, 3383)

    val CONVERSATION_WILDERNESS = arrayOf("Alright, I'll go.")
    val CONVERSATION_VARROCK = arrayOf("Where do you get your runes from?", "Yes", "Deal.",
        "Maybe I could make it worth your while?",
        "Alright, I'll go.")// Backup

    const val NAME_AUBURY = "Aubury"
    val TILE_AUBURY = Tile(3253, 3401)

    const val NAME_SEDRIDOR = "Archmage Sedridor"
    val TILE_SEDRIDOR = Tile(3104, 9571)

    const val NAME_COMPERTY = "Wizard Cromperty"
    val TILE_CROMPERTY = Tile(2684, 3323)

    const val VARPBIT_TELEPORTS = 491
    const val VARPBIT_MASK_TELEPORT = 0x1
    const val VARPBIT_SHIFT_VARROCK = 14 //   16384 100000000000000
    const val VARPBIT_SHIFT_WIZ_TOWER = 13 // 24576 110000000000000
    const val VARPBIT_SHIFT_ARDOUGNE = 15 // 57344 1110000000000000
}