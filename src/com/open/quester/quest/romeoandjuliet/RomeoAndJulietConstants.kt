package com.open.quester.quest.romeoandjuliet

import org.powbot.api.Tile

object RomeoAndJulietConstants {
    const val ITEM_CADAVA_BERRIES = "Cadava berries"
    const val ITEM_CADAVA_POTION = "Cadava potion"

    const val NAME_ROMEO = "Romeo"
    const val NAME_JULIET = "Juliet"
    const val NAME_APOTHECARY = "Apothecary"
    const val NAME_FATHER_LAWRENCE = "Father Lawrence"

    val CONVERSATION_START_QUEST = arrayOf("Yes, I have seen her actually!", "Yes, ok, I'll let her know.", "Yes.")
    val CONVERSATION_FIRST_JULIET = arrayOf("Ok, thanks.")
    val CONVERSATION_APOTHECARY = arrayOf("Talk about something else.", "Talk about Romeo & Juliet.")
    val CONVERSATION_FATHER_LAWRENCE = arrayOf("Ok, thanks.")

    val TILE_ROMEO: Tile = Tile(3211, 3422, 0)
    val TILE_JULIET: Tile = Tile(3158, 3427, 1)
    val TILE_APOTHECARY: Tile = Tile(3195, 3405, 0)
    val TILE_FATHER_LAWRENCE: Tile = Tile(3254, 3483, 0)

}