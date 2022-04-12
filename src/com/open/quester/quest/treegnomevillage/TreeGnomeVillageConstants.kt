package org.powbot.quests.quest.treegnomevillage

import org.powbot.api.Area
import org.powbot.api.Tile

object TreeGnomeVillageConstants {

    const val VARP_TRACKER = 279

    val NAME_TRACKER_GNOME_1 = "Tracker gnome 1"
    val NAME_TRACKER_GNOME_2 = "Tracker gnome 2"
    val NAME_TRACKER_GNOME_3 = "Tracker gnome 3"
    val NAME_BALLISTA = "Ballista"
    val NAME_KHAZARD_WARLORD = "Khazard warlord"

    val TILE_TRACKER_GNOME_1 = Tile(2501, 3261, 0)
    val TILE_TRACKER_GNOME_2 = Tile(2524, 3256, 0)
    val TILE_TRACKER_GNOME_3 = Tile(2497, 3234, 0)
    val TILE_BALLISTA = Tile(2509, 3209, 0)
    val TILE_FIRST_LURE_SPOT = Tile(2447, 3302, 0)
    val TILE_LURE_SAFESPOT = Tile(2444,3300, 0)
    val TILE_KHAZARD_WARLORD = Tile(2456, 3301, 0)

    val VARP_TRACKER_GNOME_1_SHIFT = 12
    val VARP_TRACKER_GNOME_2_SHIFT = 13
    val VARP_TRACKER_GNOME_3_SHIFT = 14

    val AREA_OUTSIDE_TRACKER_GNOME_2 = Area(Tile(2522, 3255), Tile(2526, 3256))

    const val NAME_BOLREN = "King Bolren"
    val TILE_BOLREN = Tile(2541, 3170, 0)
    val CONVERSATION_BOLREN = arrayOf("Can I help at all?", "I would be glad to help.", "Yes.", "Yes")
    val CONVERSATION_BOLREN_2 = arrayOf("I will find the warlord and bring back the orbs.")

    const val NAME_MONTAI = "Commander Montai"
    val TILE_MONTAI = Tile(2525, 3207, 0)
    val CONVERSATION_MONTAI = arrayOf("Ok, I'll gather some wood.", "I'll try my best.")

    val NAME_ELKOY = "Elkoy"
    val TILE_ELKOY = Tile(2502, 3192, 0)
    val TILE_ELKOY_INSIDE = Tile(2515, 3159, 0)
    val CONVERSATION_ELKOY = arrayOf("Yes please")

    const val ITEM_LOGS = "Logs"
    const val ITEM_ORBS_OF_PROTECTION = "Orbs of protection"
    const val ID_LOGS = 1511

    val TILE_UPSTAIRS_TOWER = Tile(2504, 3255, 1)
    val TILE_CLOSED_CHEST = Tile(2506, 3259, 1)

    val TILE_INSIDE_VILLAGE = Tile(2539, 3169, 0)
    val AREA_INSIDE_VILLAGE = Area(
        Tile(2514, 3176),
        Tile(2514, 3162),
        Tile(2515, 3161),
        Tile(2522, 3161),
        Tile(2522, 3158),
        Tile(2543, 3158),
        Tile(2543, 3167),
        Tile(2548, 3167),
        Tile(2548, 3173),
        Tile(2543, 3173),
        Tile(2543, 3176)
    )
    val AREA_INNER_GNOME = Area(Tile(2514, 3175), Tile(2547, 3154))
}