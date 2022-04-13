package com.open.quester.quest.insearchofthemyreeque

import com.open.quester.models.ItemRequirement
import com.open.quester.models.ItemRequirementCondition
import org.powbot.api.Area
import org.powbot.api.Tile

object InSearchOfTheMyrequeConstants {

    const val ITEM_STEEL_LONGSWORD = "Steel longsword"
    const val ITEM_STEEL_SWORD = "Steel sword"
    const val ITEM_STEEL_MACE = "Steel mace"
    const val ITEM_STEEL_WARHAMMER = "Steel warhammer"
    const val ITEM_STEEL_DAGGER = "Steel dagger"
    const val ITEM_STEEL_NAILS = "Steel nails"
    const val ITEM_DRUID_POUCH = "Druid pouch"
    const val ITEM_HAMMER = "Hammer"
    const val ITEM_COINS = "Coins"
    const val ITEM_PLANK = "Plank"

    val REQ_STEEL_LONGSWORD = ItemRequirementCondition(ITEM_STEEL_LONGSWORD, false, 1)
    val REQ_STEEL_SWORD = ItemRequirementCondition(ITEM_STEEL_SWORD, false, 2)
    val REQ_STEEL_MACE = ItemRequirementCondition(ITEM_STEEL_MACE, false, 1)
    val REQ_STEEL_WARHAMMER = ItemRequirementCondition(ITEM_STEEL_WARHAMMER, false, 1)
    val REQ_STEEL_DAGGER = ItemRequirementCondition(ITEM_STEEL_DAGGER, false, 1)
    val REQ_DRUID_POUCH = ItemRequirementCondition(ItemRequirement(ITEM_DRUID_POUCH, false, 5, stackable = true))
    val REQ_HAMMER = ItemRequirementCondition(ITEM_HAMMER, false, 1)
    val REQ_PLANK = ItemRequirementCondition(ITEM_PLANK, false, 6)
    val REQ_STEEL_NAILS = ItemRequirementCondition(ItemRequirement(ITEM_STEEL_NAILS, false, 225, stackable = true))
    val REQ_COINS = ItemRequirementCondition(ItemRequirement(ITEM_COINS, false, 10, stackable = true))

    val allItemRequirements = listOf(
        REQ_STEEL_LONGSWORD, REQ_STEEL_SWORD, REQ_STEEL_WARHAMMER, REQ_STEEL_MACE,
        REQ_STEEL_DAGGER, REQ_DRUID_POUCH, REQ_HAMMER, REQ_PLANK, REQ_STEEL_NAILS, REQ_COINS
    )

    const val NAME_VANSTROM = "Vanstrom Klause"
    val TILE_VANSTORM_PUB = Tile(3503, 3477)
    val CONVERSATION_VANSTORM = arrayOf("Yes.")

    const val NAME_CRYEG = "Cyreg Paddlehorn"
    val TILE_CYREG = Tile(3522, 3284)
    val CONVERSATION_CYREG = arrayOf(
        "I'd better be off.",
        "Well, I guess they'll just die without weapons.",
        "Resourceful enough to get their own steel weapons?",
        "If you don't tell me, their deaths are on your head!",
        "What kind of a man are you to say that you don't care?",
        "Here are some planks for you."
    )

    const val NAME_BOATY = "Swamp boaty"
    val TILE_BOATY = Tile(3522, 3285)
    val CONVERSATION_BOATY = arrayOf("Yes. I'll pay the ten gold.")

    val AREA_BRIDGE = Area(Tile(3501, 3431), Tile(3503, 3426))
    val AREA_NORTH_OF_BRIDGE = Area(Tile(3493, 3460), Tile(3515, 3431))
    val TILE_NEAR_TREE_SOUTH = Tile(3502, 3425)

    // 3182, 3183,  100
    const val NAME_CURPILE = "Curpile Fyod"
    val TILE_CURPILE = Tile(3508, 3440)
    val CONVERSATION_CURPILE_QUESTION = arrayOf(
        "I've come to help the Myreque. I've brought weapons.", "Sani Piliu.",
        "Ivan Strom.", "Veliaf Hurtz.", "Cyreg Paddlehorn.", "Drakan.", "Polmafi Ferdygris."
    )

    const val NAME_HAROLD = "Harold Evans"
    val TILE_HAROLD = Tile(3504, 9833)

    const val NAME_RADIGAD = "Radigad Ponfit"
    val TILE_RAGIGAD = Tile(3510, 9833)

    const val NAME_SANI = "Sani Piliu"
    val TILE_SANI = Tile(3511, 9838)

    const val NAME_POLMAFI = "Polmafi Ferdygris"
    val TILE_POLMAFI = Tile(3512, 9839)

    const val NAME_IVAN = "Ivan Strom"
    val TILE_IVAN = Tile(3512, 9843)

    const val NAME_VELIAF = "Veliaf Hurtz"
    val TILE_VELIAF = Tile(3506, 9837)
    val CONVERSATION_VELIAF = arrayOf("Let's talk about the weapons.")
    val LEAVE_VELIAF = arrayOf("Ok, thanks.")

    val TILE_BASE_ENTRANCE = Tile(3510, 3449)
    const val NAME_BASE_DOOR = "Wooden doors"
    const val ACTION_BASE_DOOR = "Open"

    val AREA_BASE = Area(
        Tile(3466, 9849), Tile(3468, 9830), Tile(3491, 9797),
        Tile(3510, 9797), Tile(3508, 9817), Tile(3493, 9833), Tile(3493, 9850)
    )
    val AREA_HIDDEN_BASE = Area(Tile(3499, 9847), Tile(3517, 9826))
    val AREA_UNDER_PUB = Area(Tile(3469, 9848), Tile(3489, 9837))
    val TILE_NEAR_STALAG = Tile(3491, 9825, 0)
    val TILE_WALL = Tile(3479, 9836)

    /**
     * Varpbits
     * 5 = started quest
     *
     */

    const val VARP_TALKING = 388
    const val MASK_TALK = 0x1
    const val VARP_SHIFT_SANI = 10
    const val VARP_SHIFT_HAROLD = 11
    const val VARP_SHIFT_RADIGAD = 12
    const val VARP_SHIFT_POLMAFI = 13
    const val VARP_SHIFT_IVAN = 14
}