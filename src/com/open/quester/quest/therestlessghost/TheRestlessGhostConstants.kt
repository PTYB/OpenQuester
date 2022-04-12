package com.open.quester.quest.therestlessghost

import org.powbot.api.Area
import org.powbot.api.Tile

object TheRestlessGhostConstants {

    const val NAME_FATHER_AERECK = "Father Aereck"
    const val NAME_FATHER_URHNEY = "Father Urhney"
    const val NAME_ALTAR = "Altar"

    const val VARPBIT_SKULL = 728

    val CONVERSATION_FATHER_AERECK = arrayOf("I'm looking for a quest!", "Ok, let me help then.", "Yes.", "Continue.")
    val CONVERSATION_FATHER_URHNEY = arrayOf(
        "Father Aereck sent me to talk to you.", "He's got a ghost haunting his graveyard.",
        "I've lost the Amulet of Ghostspeak."
    )
    val CONVERSATION_GHOST = arrayOf(
        "Yep, now tell me what the problem is.",
        "Yep, clever aren't I?.",
        "Yes, ok. Do you know WHY you're a ghost?",
        "Yes, ok. Do you know why you're a ghost?"
    )

    const val ID_COFFIN = 2145
    const val ID_SEARCH_COFFIN = 15061
    const val ID_SEARCH_COFFIN2 = 15052

    const val ID_RESTLESS_GHOST = 922
    val TILE_URHNEY = Tile(3147, 3175)
    val TILE_AERECK = Tile(3243, 3209)
    val TILE_GHOST = Tile(3249,3194)

    const val ITEM_SKULL = "Ghost's skull"
    const val ITEM_GHOSTSPEAK = "Ghostspeak amulet"
    const val ACTION_OPEN = "Open"

    val TILE_COFFIN = Tile(3250, 3193)
    val TILE_LADDER_GROUND_FLOOR = Tile(3104, 3162)

    val TILE_SKULL = Tile(3118, 9566)
    val TILE_DOOR_UNDERGROUND = Tile(3111, 9559)

    val AREA_UNDERGROUND: Area = Area(Tile(3122, 9554), Tile(3095, 9578))
}