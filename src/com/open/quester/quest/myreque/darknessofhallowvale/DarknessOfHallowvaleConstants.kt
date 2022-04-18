package com.open.quester.quest.myreque.darknessofhallowvale

import com.open.quester.models.ItemRequirement
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.quest.Constants
import org.powbot.api.Area
import org.powbot.api.Tile

object DarknessOfHallowvaleConstants {

    const val ITEM_STEEL_NAILS = "Steel nails"
    const val ITEM_PLANK = "Plank"
    const val ITEM_AIR_RUINE = "Air rune"
    const val ITEM_LAW_RUNE = "Law rune"
    const val ITEM_FIRE_RUNE = "Fire rune"

    const val ITEM_CHARCOAL = "Charcoal"
    const val ITEM_PAPYRUS = "Papyrus"

    const val ITEM_DOOR_KEY = "Door key"
    const val ITEM_LADDER_PIECE = "Ladder top"
    const val ITEM_MESSAGE = "Message"
    const val ITEM_SKETCH_1 = "Castle sketch 1"
    const val ITEM_SKETCH_2 = "Castle sketch 2"
    const val ITEM_SKETCH_3 = "Castle sketch 3"
    const val ITEM_ORNATE_KEY = "Large ornate key"
    const val ITEM_HAEM_BOOK = "Haemalchemy volume 1"
    const val ITEM_SEALED_MESSAGE = "Sealed message"

    val TILE_NEAR_BOAT = Tile(3522, 3180)
    val TILE_NEAR_BOAT_CHUTE = Tile(3523, 3175)
    val TILE_NEAR_BOAT_BOARD = Tile(3525, 3170)
    val CONVERSATION_BOAT = arrayOf("Yes.", "Okay, thanks.")

    val TILE_NEAR_FLOORBOARD = Tile(3590, 3173, 1)

    val AREA_FIRST_AREA = Area(Tile(3587, 3224), Tile(3628, 3173))
    val TILE_BEFORE_AGILITY = Tile(3596, 3204)
    val AREA_UPSTAIRS = Area(Tile(3584, 3179, 1), Tile(3607, 3161, 1))
    val AREA_KNIFE_ROOM = Area(Tile(3622, 3256, 1), Tile(3628, 3248, 1))
    val AREA_BASE = Area(Tile(3636, 3258), Tile(3640, 3248))
    val AREA_INSIDE_BASE_DOOR = Area(Tile(3636, 3252), Tile(3640, 3248))
    val AREA_UNDERGROUND_BASE = Area(Tile(3613, 9648), Tile(3645, 9613))
    val AREA_FIRST_WALL = Area(
        Tile(3608, 3161, 1),
        Tile(3581, 3169, 1),
        Tile(3586, 3214, 1),
        Tile(3591, 3214, 1),
        Tile(3591, 3200, 1),
        Tile(3595, 3200, 1)
    )
    val TILE_JUST_OFF_BOAT = Tile(3605, 3163, 1)
    val TILE_JUST_ON_BOAT = Tile(3605, 3161, 1)
    val TILE_BELOW_KNIFE = Tile(3626, 3250, 0)
    val TILE_BASE_LADDER_FINAL = Tile(3631, 3258, 0)

    const val NAME_TAPESTRY = "Tapestry"
    val TILE_NEAR_TAPESTRY = Tile(3638, 3304)
    const val NAME_STATUE = "Vampyre statue"
    val AREA_STATUE_ROOM = Area(
        Tile(3635, 3307),
        Tile(3638, 3311),
        Tile(3641, 3311),
        Tile(3643, 3310),
        Tile(3643, 3304),
        Tile(3640, 3304),
        Tile(3640, 3305),
        Tile(3635, 3305)
    )

    val AREA_LAB = Area(Tile(3622, 9700), Tile(3642, 9681))

    const val NAME_CITIZEN = "Meiyerditch citizen"
    val TILE_NEAR_CITIZEN = Tile(3596, 3193)
    val CONVERSATION_CITIZEN = arrayOf(
        "(whisper) Do you know about the Myreque?",
        "(whisper) I really need to meet the Myreque.",
        "How can Old Man Ral help me?"
    )

    val CONVERSATION_SEND_ME_TO_MINE =
        arrayOf("Send me to the mines! (Do a bit of menial work)", "Send me to the mines.")

    const val NAME_VYREWATCH = "Vyrewatch"
    const val NAME_VANSTROM_KALUSE = "Vanstrom Kaluse"

    const val NAME_RAL = "Old man ral"
    val TILE_RAL = Tile(3604, 3208, 0)
    val CONVERSATION_RAL = arrayOf("Someone said you could help me.", "Old Man Ral, the sage of Sanguinesti.")
    val CONVERSATION_VELIAF_START = arrayOf("Is there something I can do to help out?", "Yes.")
    val CONVERSAITON_VELIAF_SECOND = arrayOf("I have a message for you from Vertida!", "What should I do now?")
    val CONVERSATION_KICK_FLOOR = arrayOf("Yes.")

    const val NAME_SAFALAAN = "Safalaan Hallow"
    val TILE_SAAFALAAN = Tile(3585, 3331)
    val AREA_SAAFALAAN_WALL = Area(Tile(3597, 3214, 1), Tile(3572, 3332, 1))
    val AREA_SAAFALAAN_WALL_GROUND = Area(Tile(3564, 3333), Tile(3596, 3307))
    val CONVERSATION_SAAFALAAN = arrayOf("Okay, lead the way.")

    val TILE_NORTH_WALL = Tile(3556, 3379)
    val TILE_WEST_WALL = Tile(3522, 3357)
    val TILE_SOUTH_WALL = Tile(3572, 3331)

    val areaRockySurface = Area(
        Tile(3587, 3214),
        Tile(3591, 3214),
        Tile(3591, 3210),
        Tile(3590, 3205),
        Tile(3587, 3205)
    )
    val areaSecondWall = Area(
        Tile(3587, 3231),
        Tile(3590, 3234),
        Tile(3594, 3234),
        Tile(3594, 3229),
        Tile(3591, 3229),
        Tile(3590, 3227),
        Tile(3590, 3219),
        Tile(3592, 3215),
        Tile(3587, 3215)
    )

    val messageFromVyre = "Hey, look over there! (Distract and sneak away)."

    const val NAME_VERTIDA = "Vertida Sefalatis"
    val TILE_VERTIDA = Tile(3627, 9644, 0)
    val CONVERSATION_VERTIDA = arrayOf("Okay, lead the way.", "Okay, thanks.")

    val hammerRequirement = ItemRequirementCondition(Constants.ITEM_HAMMER, false, 1)
    val plankRequirement = ItemRequirementCondition(ITEM_PLANK, false, 2)
    val nailsRequirement = ItemRequirementCondition(ItemRequirement(ITEM_STEEL_NAILS, false, 8, stackable = true))
    val pickaxeRequirement = ItemRequirementCondition(*Constants.ItemRequirements.REQUIREMENT_ITEM_PICKAXE)
    val airRuneRequirement = ItemRequirementCondition(ItemRequirement(ITEM_AIR_RUINE, false, 12, stackable = true))
    val lawRuneRequirement = ItemRequirementCondition(ItemRequirement(ITEM_LAW_RUNE, false, 3, stackable = true))
    val fireRuneRequirement = ItemRequirementCondition(ItemRequirement(ITEM_FIRE_RUNE, false, 2, stackable = true))
    val messageRequirement = ItemRequirementCondition(ItemRequirement(ITEM_MESSAGE, true))
    val knifeRequirement = ItemRequirementCondition(ItemRequirement("Knife", false))

    val firstBankRequirements = listOf(
        airRuneRequirement, lawRuneRequirement, fireRuneRequirement, plankRequirement,
        nailsRequirement, hammerRequirement, pickaxeRequirement
    )

    val talkInPubRequirement = listOf(messageRequirement, airRuneRequirement, lawRuneRequirement, fireRuneRequirement)

    val secondBankRequirements = listOf(
        airRuneRequirement, lawRuneRequirement, fireRuneRequirement, pickaxeRequirement,
        hammerRequirement, knifeRequirement, ItemRequirementCondition.emptySlots(5)
    )

    val allItemRequirements = listOf(
        hammerRequirement, plankRequirement, nailsRequirement, pickaxeRequirement,
        airRuneRequirement, lawRuneRequirement, fireRuneRequirement
    )

    val TILE_BUSHES = Tile(3391, 3481) // TODO Confirm

    val CONVERSATION_ROALD = arrayOf(
        "Talk to the king about Morytania.",
        "What should I do now?",
        "Yes thanks. I'll accept the free teleport.",
        "Okay, will do."
    )

    /**
     *  870
     *  0 = Boat fixed (1)
     *  1 -?(Boat available???)
     *  2 = Chute fixed (101)
     *  3 = Boat pushed (1110)
     **/
    const val VARP_INFORATION = 870
    const val VARP_SHIFT_CHUTE_FIXED = 2
    const val VARP_SHIFT_BOAT_PUSHED = 3

    const val VARP_SHIFT_MINING = 4
    const val VARP_MASK_MINING = 15

    /**
     *  Mining
     *  870
     *  1430262030 -> 1430262046
     *  1010101010000000001000100001110
     *  1010101010000000001000100011110
     */
}