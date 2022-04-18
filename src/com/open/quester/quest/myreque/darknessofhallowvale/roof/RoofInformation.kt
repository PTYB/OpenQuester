package com.open.quester.quest.myreque.darknessofhallowvale.roof

import org.powbot.api.Area
import org.powbot.api.Tile

object RoofInformation {
    const val NAME_FLOORBOARDS = "Floorboards"
    const val NAME_WALL = "Wall"
    const val NAME_FLOOR = "Floor"
    const val NAME_LADDER = "Ladder"
    const val NAME_TABLE = "Table"
    const val NAME_STAIRS_DOWN = "Stairs down"
    const val NAME_TRAPDOOR_TABLE = "Trapdoor table"
    const val NAME_TRAPDOOR_TUNNEL = "Trapdoor tunnel"
    const val NAME_SHELF = "Shelf"
    const val NAME_POTS = "Pots"
    const val NAME_DOOR = "Door"
    const val NAME_WASHING_LINE = "Washing line"

    const val ACTION_FLOORBOARDS = "Jump-to"
    const val ACTION_WALL = "Push"
    const val ACTION_WALK_ACROSS = "Walk-across"
    const val ACTION_WALL_CRAWL = "Crawl-under"
    const val ACTION_CLIMB_DOWN = "Climb-down"
    const val ACTION_CLIMB_INTO = "Climb-into"
    const val ACTION_SEARCH = "Search"
    const val ACTION_OPEN = "Open"
    const val ACTION_CLIMB_UP = "Climb-up"

    val AREA_FIRST_ROOF = Area(Tile(3595, 3203, 1), Tile(3599, 3205, 1))
    val TILE_FIRST_ROOF_JUMP = Tile(3598, 3201, 1)

    val AREA_SECOND_ROOF = Area(Tile(3596, 3201, 1), Tile(3599, 3200, 1))
    val TILE_SECOND_ROOF_JUMP = Tile(3601, 3200, 1)

    val AREA_THIRD_ROOF = Area(Tile(3601, 3203, 1), Tile(3605, 3198, 1))
    val TILE_THIRD_ROOF_JUMP_ONE = Tile(3605, 3203, 1)
    val TILE_THIRD_ROOF_JUMP_TWO = Tile(3605, 3203, 1)

    val AREA_FOURTH_ROOF = Area(Tile(3604, 3205, 1), Tile(3607, 3207, 1))
    val TILE_FOURTH_ACTION = Tile(3606, 3208, 1)

    val AREA_FIFTH_ROOF = Area(Tile(3603, 3217, 1), Tile(3608, 3208, 1))
    val TILE_FIFTH_ACTION = Tile(3604, 3214, 1)

    val AREA_SIXTH_ROOF = Area(Tile(3598, 3213, 1), Tile(3602, 3217, 1))
    val TILE_SIXTH_ACTION = Tile(3601, 3215, 1)

    val AREA_SEVENTH_ROOF = Area(Tile(3598, 3213, 0), Tile(3602, 3217, 0))
    val TILE_SEVENTH_ACTION = Tile(3598, 3216, 0)

    val AREA_EIGHTH_ROOF = Area(Tile(3593, 3219), Tile(3598, 3223))
    val TILE_EIGHTH_ACTION = Tile(3594, 3223, 0)

    val AREA_NINTH_ROOF = Area(Tile(3592, 3221, 1), Tile(3595, 3223, 1))
    val TILE_NINTH_ACTION = Tile(3596, 3223, 1)

    val AREA_TENTH_ROOF = Area(Tile(3596, 3223, 1), Tile(3598, 3219, 1))
    val TILE_TENTH_ACTION = Tile(3601, 3222, 1)

    val AREA_ELEVEN_ROOF = Area(Tile(3600, 3224, 1), Tile(3606, 3218, 1))
    val TILE_ELEVEN_ACTION = Tile(3603, 3222, 1)

    val AREA_TWELVE_ROOF = Area(Tile(3601, 3223), Tile(3607, 3219))
    val TILE_TWELVE_ACTION = Tile(3608, 3222)
    val TILE_TWELVE_ACTION2 = Tile(3608, 3221)

    val AREA_THIRTEEN_ROOF = Area(Tile(3609, 3224), Tile(3619, 3218))
    val TILE_THIRTEEN_ACTION = Tile(3618, 3219)

    val AREA_FOURTEEN_ROOF = Area(Tile(3610, 3224, 1), Tile(3619, 3218, 1))
    val TILE_FOURTEEN_ACTION = Tile(3615, 3216, 1)

    val AREA_FIFTEEN_ROOF = Area(Tile(3610, 3216, 1), Tile(3619, 3208, 1))
    val TILE_FIFTEEN_ACTION = Tile(3615, 3210, 1)

    val AREA_SIXTEEN_ROOF = Area(Tile(3609, 3216, 2), Tile(3617, 3208, 2))
    val TILE_SIXTEEN_ACTION = Tile(3610, 3210, 2)

    // Roof jump
    val AREA_SEVENTEEN_ROOF = Area(Tile(3608, 3212, 3), Tile(3615, 3207, 3))
    val TILE_SEVENTEEN_ACTION = Tile(3613, 3205, 3)

    val AREA_EIGHTTEEN_ROOF = Area(Tile(3609, 3205, 3), Tile(3616, 3201, 3))
    val TILE_EIGHTTEEN_ACTION = Tile(3612, 3203, 3)

    val AREA_NINETEEN_ROOF = Area(Tile(3608, 3205, 2), Tile(3616, 3198, 2))
    val TILE_NINETEEN_ACTION = Tile(3617, 3202, 2)

    val AREA_TWENTY_ROOF = Area(Tile(3621, 3208, 2), Tile(3626, 3200, 2))
    val TILE_TWENTY_ACTION = Tile(3625, 3203, 2)

    val AREA_TWENTYONE_ROOF = Area(Tile(3621, 3208, 1), Tile(3632, 3201, 1))
    val TILE_TWENTYONE_ACTION = Tile(3623, 3207, 1)

    val AREA_TWENTYTWO_ROOF = Area(Tile(3621, 3221, 1), Tile(3624, 3210, 1))
    val TILE_TWENTYTWO_ACTION = Tile(3623, 3217, 1)

    val AREA_TWENTYTHREE_ROOF = Area(Tile(3620, 3222, 2), Tile(3626, 3217, 2))
    val TILE_TWENTYTHREE_ACTION = Tile(3625, 3221, 2)

    val AREA_TWENTYFOUR_ROOF = Area(Tile(3621, 3223, 1), Tile(3627, 3219, 1))
    val TILE_TWENTYFOUR_ACTION = Tile(3623, 3226, 1)

    val AREA_TWENTYFIVE_ROOF = Area(Tile(3619, 3230, 1), Tile(3624, 3226, 1))
    val TILE_TWENTYFIVE_ACTION = Tile(3622, 3232, 1)

    val AREA_TWENTYSIX_ROOF = Area(Tile(3619, 3232, 1), Tile(3624, 3241, 1))
    val TILE_TWENTYSIX_ACTION = Tile(3626, 3240, 1)

    val AREA_TWENTYSEVEN_ROOF = Area(Tile(3625, 3241, 1), Tile(3631, 3238, 1))
    val TILE_TWENTYSEVEN_ACTION = Tile(3630, 3239, 1)

    val AREA_TWENTYEIGHT_ROOF = Area(Tile(3625, 3241, 2), Tile(3631, 3238, 2))
    val TILE_TWENTYEIGHT_ACTION = Tile(3626, 3240, 2)
    val TILE_TWENTYEIGHT_DOWN_ACTION = Tile(3631, 3239, 2)

    val AREA_BEFORE_BASE = Area(Tile(3628, 3260, 1), Tile(3633, 3255,1))
    val TILE_BEFORE_BASE_ACTION = Tile(3636,3256,1)
    val TILE_BEFORE_BASE_LADDER = Tile(3631,3259, 1)

    val AREA_ABOVE_BASE = Area(Tile(3635, 3252, 1), Tile(3640, 3259,1))
    val TILE_ABOVE_BASE_ACTION = Tile(3639,3256,1)
    val TILE_ABOVE_BASE_REVERSE_ACTION = Tile(3633,3256,1)

    val AREA_HIDEOUT_BASE = Area(
        Tile(3621, 3263),
        Tile(3628, 3264),
        Tile(3640, 3264),
        Tile(3641, 3243),
        Tile(3636, 3235),
        Tile(3625, 3233),
        Tile(3625, 3241),
        Tile(3623, 3241),
        Tile(3623, 3258)
    )
}