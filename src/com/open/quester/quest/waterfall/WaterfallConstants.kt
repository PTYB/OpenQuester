package com.open.quester.quest.waterfall

import com.open.quester.extensions.count
import org.powbot.api.Area
import org.powbot.api.Tile
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Players

object WaterfallConstants {

    val CONDITION_NO_URN = { Inventory.count(ITEM_URN) == 0 }
    val CONDITION_HAS_URN = { Inventory.count(ITEM_URN) > 0 }
    val CONDITION_NO_BAX_KEY = { Inventory.count(ID_BAX_KEY) == 0 }
    val CONDITION_HAS_BAX_KEY = { Inventory.count(ID_BAX_KEY) > 0 }
    val CONDITION_HAS_AMULET = { Inventory.count(ITEM_AMULET) > 0 }
    val CONDITION_NO_AMULET = { Inventory.count(ITEM_AMULET) == 0 }
    val CONDITION_NO_BOOK = { Inventory.count(ITEM_BOOK) == 0 }
    val CONDITION_ON_LEDGE = {AREA_LEDGE.contains(Players.local())}

    const val ITEM_BOOK = "Book on baxtorian"
    val ID_BOOKCASE = 1989
    val TILE_BOOKCASE = Tile(2519, 3426, 1)

    const val ITEM_PEBBLE = "Glarial's pebble"
    const val ITEM_URN = "Glarial's urn"
    const val ITEM_AMULET = "Glarial's amulet"

    const val ID_KEY = 293
    const val ID_BAX_KEY = 298

    const val WIDGET_BOOK = 392
    const val COMPONENT_CLOSE = 8

    const val NAME_ALMERA = "Almera"
    val TILE_ALMERA = Tile(2521, 3495)
    val CONVERSATION_ALMERA = arrayOf("How can I help?", "Yes.")

    const val NAME_RAFT = "Log raft"
    val TILE_RAFT = Tile(2511, 3494)

    const val NAME_HUDON = "Hudon"
    val TILE_HUDON = Tile(2511, 3484)

    const val NAME_GLORIE = "Golrie"
    val TILE_GLORIE = Tile(2514, 9580, 0)

    const val NAME_STATUE = "Statue of Glarial"

    val TILE_OUTSIDE_TOMB = Tile(2557, 3444, 0)
    val AREA_INSIDE_TOMB = Area(Tile(2524, 9801, 0), Tile(2557, 9849, 0))

    val AREA_HUDON_ISLAND = Area(Tile(2510, 3476, 0), Tile(2515, 3482, 0))
    val AREA_DEAD_TREE_ISLAND = Area(Tile(2512, 3465, 0), Tile(2515, 3482, 0))
    val AREA_LEDGE = Area(Tile(2510, 3462, 0), Tile(2513, 3464, 0))
    val AREA_INSIDE_MAZE = Area(Tile(2500, 3141), Tile(2555, 3187))
    val AREA_GNOME_BASEMENT = Area(Tile(2497, 9552, 0), Tile(2559, 9593, 0))
    val AREA_INSIDE_WATERFALL = Area(Tile(2559, 9859), Tile(2596, 9918))

    val FINAL_ROOM = Area(
        Tile(2563, 9918),
        Tile(2569, 9918),
        Tile(2571, 9916),
        Tile(2571, 9910),
        Tile(2569, 9908),
        Tile(2569, 9907),
        Tile(2568, 9906),
        Tile(2568, 9904),
        Tile(2567, 9903),
        Tile(2567, 9902),
        Tile(2566, 9902),
        Tile(2566, 9903),
        Tile(2564, 9905),
        Tile(2564, 9906),
        Tile(2563, 9907),
        Tile(2563, 9908),
        Tile(2561, 9910),
        Tile(2561, 9916)
    )

    const val NAME_ROCK = "Rock"
    const val NAME_TREE = "Dead Tree"
    const val NAME_BARREL = "Barrel"

    const val ID_CRATE = 1990
    const val ID_CRATE2 = 1999
    val TILE_CRATE = Tile(2548, 9566, 0)

    val ID_CHEST = 1994
    val TILE_CHEST = Tile(2530, 9844, 0)

    const val NAME_COFFIN = "Glarial's Tomb"
    val TILE_COFFIN = Tile(2542, 9812, 0)

    const val NAME_ELKOY = "Elkoy"
    val TILE_ELKOY = Tile(2502, 3192, 0)
}