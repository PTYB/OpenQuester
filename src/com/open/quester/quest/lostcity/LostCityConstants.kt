package com.open.quester.quest.lostcity

import org.powbot.api.Area
import org.powbot.api.Tile

object LostCityConstants {

    const val NAME_WARRIOR = "Warrior"
    val TILE_WARRIOR = Tile(3151, 3207, 0)
    val CONVERSATIONS_WARRIOR = arrayOf(
        "What are you camped out here for?", "What makes you think it's out here?",
        "If it's hidden how are you planning to find it?", "Looks like you don't know either.",
        "Yes.", "Yes",
    )

    const val NAME_SHAMUS = "Shamus"
    val CONVERSATION_CHATTING = arrayOf("I've been in that shed, I didn't see a city.")

    val AREA_ENTRANA = Area(Tile(2798, 3393), Tile(2880, 3324))
    val AREA_LOST_CITY_DUNGEON = Area(
        Tile(2823, 9783),
        Tile(2838, 9783),
        Tile(2861, 9760),
        Tile(2879, 9751),
        Tile(2868, 9727),
        Tile(2818, 9727),
        Tile(2815, 9771)
    )
    val AREA_END_ROOM = Area(Tile(2852, 9740), Tile(2869, 9729))
    val AREA_SOUTH_TREE = Area(Tile(2860, 9731), Tile(2861, 9734))

    val TILE_SAFESPOT = Tile(2859, 9731, 0)
    val TILE_NPC_SPOT = Tile(2859, 9733, 0)
    val TILE_NEAR_ZANARIS = Tile(3201, 3169, 0)
    const val NAME_BRANCH = "Dramen branch"
    const val NAME_STAFF = "Dramen staff"
}