package com.open.quester.quest.plaguecity

import org.powbot.api.Area
import org.powbot.api.Tile

object PlagueCityConstants {

    val AREA_UNDERGROUND = Area(Tile(2504, 9776), Tile(2522, 9736))
    val AREA_WEST_ARDOUGNE = Area(Tile(2457, 3264), Tile(2558, 3335))
    val AREA_EAST_ARDOUGNE = Area(Tile(2558, 3341), Tile(2690, 3264))
    val AREA_PLAGUE_HOUSE_UPSTAIRS = Area(Tile(2532, 3271), Tile(2541, 3268))
    val AREA_PLAGUE_HOUSE_DOWNSTAIRS = Area(Tile(2534, 9674), Tile(2543, 9668))

    const val VARP_UNDERGROUND = 667 //could be tunnel to back?
    const val VARP_VALUE_DUG = 5
    const val VARP_VALUE_PULLED = 13
    const val VARP_VALUE_ROPED = 29

    val TILE_PLAGUE_DOOR = Tile(2533,3272,0)
    val TILE_NEAR_MANHOLE = Tile(2529, 3304, 0)

    const val NAME_EDMOND = "Edmond"
    val TILE_EDMOND = Tile(2567, 3332, 0)
    val TILE_EDMOND_UNDERGROUND = Tile(2517, 9756, 0)
    val CONVERSATION_EDMOND_START = arrayOf("What's happened to her?", "Yes.")

    const val NAME_ALRENA = "Alrena"
    val TILE_AlRENA = Tile(2573, 3333, 0)

    const val NAME_JETHICK = "Jethick"
    val TILE_JETHICK = Tile(2540, 3305, 0)
    val CONVERSATION_JETHICK = arrayOf("Yes, I'll return it for you.")

    const val NAME_MARTHA = "Martha Rehnison"
    val TILE_MARTHA = Tile(2531, 3331)

    const val NAME_MILLI = "Milli Rehnison"
    val TILE_MILLI = Tile(2531, 3331, 1)

    val TILE_CLERK = Tile(2526, 3315, 0)
    val CONVERSATION_CLERK =
        arrayOf("I need permission to enter a plague house.", "This is urgent though! Someone's been kidnapped!")

    val TILE_PLAGUE_HOUSE = Tile(2533, 3272)
    val CONVERSATION_PLAGUE_HOUSE = arrayOf("I fear not a mere plague.", "I want to check anyway.")

    val TILE_BACK_HOUSE = Tile(2569, 3332, 0)

    const val NAME_MUDPATCH = "Mud patch"
    val TILE_MUDPATCH = Tile(2566, 3333, 0)

    const val NAME_BRAVEK = "Bravek"
    val TILE_BRAVEK = Tile(2534, 3314)
    val CONVERSATION_BRAVEK = arrayOf("This is really important though!", "Do you know what's in the cure?")
    val CONVERSATION_BRAVEK_AGAIN = arrayOf("They won't listen to me!")

    const val NAME_PIPE = "Pipe"
    const val NAME_GRILL = "Grill"
    const val ACTION_GRILL = "Open"
    val TILE_GRILL = Tile(2514, 9740, 0)

    const val NAME_ELENA = "Elena"
    val TILE_ELENA = Tile(2537, 9670, 0)

    val TILE_BY_BARREL = Tile(2535, 3268, 0)

    const val ITEM_DWELLBERRIES = "Dwellberries"
    const val ID_DWELLBERRIES = 2126

    const val ITEM_CHOCOLATE_DUST = "Chocolate dust"
    const val ID_CHOCOLATE_DUST = 1975

    const val ITEM_SNAPE_GRASS = "Snape grass"
    const val ID_SNAPE_GRASS = 231

    const val ITEM_PICTURE_OF_ELENA = "Picture"
    const val ITEM_GAS_MASK = "Gas Mask"
    const val ITEM_BOOK = "Book"
    const val ITEM_BUCKET_OF_MILK = "Bucket of Milk"
    const val ITEM_CHOCOLATE_MILK = "Chocolatey milk"
    const val ITEM_HANGOVER_CURE = "Hangover Cure"
    const val ITEM_WARRANT = "Warrant"
    const val ITEM_KEY = "A small key"
}