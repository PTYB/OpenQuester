package com.open.quester.quest.myreque.inaidofthemyreque

import com.open.quester.models.ItemRequirement
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.quest.Constants
import org.powbot.api.Area
import org.powbot.api.Tile

object InAidOfTheMyrequeConstants {

    const val ITEM_PLANK = "Plank"
    const val ITEM_STEEL_NAILS = "Steel nails"
    const val ITEM_SWAMP_PASTE = "Swamp paste"
    const val ITEM_RAW_SNAIL = "Thin snail"
    const val ITEM_RAW_MACKEREL = "Raw mackerel"
    const val ITEM_BRONZE_AXE = "Bronze axe"
    const val ITEM_TINDERBOX = "Tinderbox"
    const val ITEM_STEEL_BAR = "Steel bar"
    const val ITEM_COAL = "Coal"
    const val ITEM_SOFT_CLAY = "Soft clay"
    const val ITEM_ROPE = "Rope"
    const val ITEM_SILVER_BAR = "Silver bar"
    const val ITEM_MITHRIL_BAR = "Mithril bar"
    const val ITEM_SAPPHIRE = "Sapphire"
    const val ITEM_COSMIC_RUNE = "Cosmic rune"
    const val ITEM_WATER_RUNE = "Water rune"
    const val ITEM_STEEL_MED = "Steel med helm"
    const val ITEM_STEEL_CHAINBODY = "Steel chainbody"
    const val ITEM_STEEL_PLATELEGS = "Steel Platelegs"
    const val ITEM_SILVER_SICKLE = "Silver sickle"
    const val ITEM_BLESSED_SICKLE = "Silver sickle (b)"
    const val ITEM_CRATE = "Crate"
    const val ITEM_SALMON = "Salmon"

    const val ITEM_BOOK = "The Sleeping Seven"
    const val ITEM_BUCKET_OF_RUBBLE = "Bucket of rubble"
    const val ITEM_ROD_MOULD = "Rod mould"
    const val ITEM_SILVERTHRILL_ROD = "Silvthrill rod"
    const val ITEM_ROD_OF_IVANDIS = "Rod of ivandis (10)"

    const val NAME_PLAQUE = "Plaque"
    const val NAME_RUBBLE_PILE = "Rubble Pile"
    val TILE_NEAR_RUBBLE_PILE = Tile(3489, 3226)

    val AREA_BURG = Area(Tile(3469, 3243), Tile(3541, 3159))
    val AREA_SECRET_BASE = Area(Tile(3498, 9848), Tile(3518, 9828))
    val AREA_HIDDEN_LIBRARY = Area(Tile(3349, 9906), Tile(3363, 9898))
    val AREA_UNDER_PUB = Area(Tile(3488, 9634), Tile(3502, 9620))
    val AREA_BASE = Area(
        Tile(3466, 9849), Tile(3468, 9830), Tile(3491, 9797),
        Tile(3510, 9797), Tile(3508, 9817), Tile(3493, 9833), Tile(3493, 9850)
    )
    const val NAME_CAVE_ENTRANCE = "Cave entrance"

    val AREA_TOMB = Area(Tile(3487, 9879), Tile(3515, 9855))
    const val NAME_TOMB = "Coffin"
    val TILE_NEAR_TOMB = Tile(3510, 9863)
    val TILE_NEAR_EXIT_TOMB = Tile(3491, 9876)

    val TILE_NEAR_EDGEVILLE_FURNACE = Tile(3109, 3498, 0)

    val TILE_VELIAF = Tile(3506, 9837)
    val CONVERSATION_START_QUEST = arrayOf("Yes.")

    const val NAME_FLORIN = "Florin"
    val TILE_FLORIN = Tile(3484, 3242)

    const val NAME_CHEST = "Open chest"
    val TILE_NEAR_OPEN_CHEST = Tile(3483, 3245)
    val CONVERSATION_CLOSE = arrayOf("Okay, goodbye.")

    const val NAME_RAZVAN = "Razvan"
    val TILE_RAZVAN = Tile(3493, 3235)
    val CONVERSATION_RAZVAN = arrayOf("Are there any 'out of the way' places here?", "Okay, thanks.")
    val CONVERSATION_DO_NEXT = arrayOf("What should I do now?")

    const val NAME_RUBBLE = "Rubble"
    val TILE_RUBBLE = Tile(3491, 3231)

    const val NAME_AUREL = "Aurel"
    val TILE_AUREL = Tile(3515, 3241)
    val CONVERSATION_AUREL = arrayOf("I'd like to help fix up the town.")
    val TILE_AUREL_LADDER = Tile(3513, 3237)
    val TILE_DAMAGED_WALL = Tile(3517, 3238)
    val CONVERSATION_AUREL_CRATE = arrayOf("What should I do now?")
    val CONVERSATION_AUREL_CRATE_DONE = arrayOf("Ok thanks.")

    const val NAME_BANK_BOOTH = "Bank Booth"
    val TILE_BANK_BOOTH = Tile(3494, 3211)
    const val NAME_DAMAGED_WALL = "Damaged wall"
    val TILE_BANK_WALL = Tile(3491, 3211)
    val CONVERSATION_FIX = arrayOf("Ok thanks.", "Yes.")

    const val NAME_CORNELIUS = "Cornelius"
    val TILE_CORNELIUS = Tile(3496, 3211)
    val CONVERSATION_CORNELIUS = arrayOf("What should I do now?")

    val NAME_BROKEN_FURNACE = "Broken furnace"
    val NAME_REPAIRED_FURNACE = "Repaired furnace"
    val TILE_FURNACE = Tile(3526, 3210)

    val TILE_GENERAL_STORE = Tile(3514, 3241)
    const val NAME_WISKIT = "Wiskit"
    const val NAME_GADDERANKS = "Gadderanks"
    const val NAME_JUVINATE = "Vampyre Juvinate"

    const val NAME_POLAMFI = "Polmafi Ferdygris"
    val TILE_POLMAFI = Tile(3506, 9837, 0)

    const val NAME_DREZEL = "Drezel"
    val TILE_DREZEL = Tile(3439, 9896)
    val CONVERSATION_DREZEL = arrayOf(
        "Veliaf told me about Ivandis.", "Is there somewhere that I might get more information on Ivandis?",
        "The lives of those pitiful few left in Morytania could rest on this!"
    )

    const val NAME_KEYHOLE = "Keyhole"

    const val NAME_BOARDS = "Wooden boards"
    val TILE_BOARDS = Tile(3482, 9832)

    val TILE_NEAR_WELL = Tile(3421, 9890)

    val spadeRequirement = ItemRequirementCondition(Constants.ITEM_SPADE, false, 1)
    val bucketRequirement = ItemRequirementCondition(Constants.ITEM_BUCKET, false, 5)
    val hammerRequirement = ItemRequirementCondition(Constants.ITEM_HAMMER, false, 1)
    val coinsRequirement = ItemRequirementCondition(ItemRequirement("Coins", false, 10, stackable = true))

    val plankRequirement = ItemRequirementCondition(ITEM_PLANK, false, 11)
    val firstBankPlankRequirement = ItemRequirementCondition(ITEM_PLANK, false, 6)
    val secondBankPlankRequirement = ItemRequirementCondition(ITEM_PLANK, false, 5)

    val nailsRequirement = ItemRequirementCondition(ItemRequirement(ITEM_STEEL_NAILS, false, 44, stackable = true))
    val secondNailsRequirement =
        ItemRequirementCondition(ItemRequirement(ITEM_STEEL_NAILS, false, 20, stackable = true))
    val swampPasteRequirement = ItemRequirementCondition(ITEM_SWAMP_PASTE, false, 1)
    val rawSnailRequirement = ItemRequirementCondition(ITEM_RAW_SNAIL, false, 10)
    val rawMackerelRequirement = ItemRequirementCondition(ITEM_RAW_MACKEREL, false, 10)
    val bronzeAxeRequirement = ItemRequirementCondition(ITEM_BRONZE_AXE, false, 10)
    val tinderboxRequirement = ItemRequirementCondition(ITEM_TINDERBOX, false, 4)
    val crateTinderboxRequirement = ItemRequirementCondition(ITEM_TINDERBOX, false, 3)
    val singleTinderboxRequirement = ItemRequirementCondition(ITEM_TINDERBOX, false, 1)
    val steelBarRequirement = ItemRequirementCondition(ITEM_STEEL_BAR, false, 2)
    val coalRequirement = ItemRequirementCondition(ITEM_COAL, false, 1)
    val salmonRequirement = ItemRequirementCondition(ITEM_SALMON, false, 15)
    val softClayRequirement = ItemRequirementCondition(ITEM_SOFT_CLAY, false, 1)
    val ropeRequirement = ItemRequirementCondition(ITEM_ROPE, false, 1)
    val silverBarRequirement = ItemRequirementCondition(ITEM_SILVER_BAR, false, 1)
    val mithrilBarRequirement = ItemRequirementCondition(ITEM_MITHRIL_BAR, false, 1)
    val sapphireRequirement = ItemRequirementCondition(ITEM_SAPPHIRE, false, 1)
    val cosmicRequirement = ItemRequirementCondition(ItemRequirement(ITEM_COSMIC_RUNE, false, 1, stackable = true))
    val waterRequirement = ItemRequirementCondition(ItemRequirement(ITEM_WATER_RUNE, false, 1, stackable = true))

    val steelMedRequirement = ItemRequirementCondition(ITEM_STEEL_MED, false, 1)
    val steelChainbodyRequirement = ItemRequirementCondition(ITEM_STEEL_CHAINBODY, false, 1)
    val steelPlatelegsRequirement = ItemRequirementCondition(ITEM_STEEL_PLATELEGS, false, 1)
    val silverSickleRequirement = ItemRequirementCondition(ITEM_SILVER_SICKLE, false, 1)
    val blessedSickleRequirement = ItemRequirementCondition(ITEM_BLESSED_SICKLE, false, 1)
    val pickaxeRequirement = ItemRequirementCondition(*Constants.ItemRequirements.REQUIREMENT_ITEM_PICKAXE)
    val crateRequirement = ItemRequirementCondition(ITEM_CRATE, true, 1)

    val rodRequirement = ItemRequirementCondition(ITEM_ROD_OF_IVANDIS, true, 1)

    val firstBankRequirements = listOf(
        pickaxeRequirement, bucketRequirement, nailsRequirement, firstBankPlankRequirement, hammerRequirement,
        spadeRequirement, ItemRequirementCondition.emptySlots(8)
    )

    val fillItemRequirement = listOf(bronzeAxeRequirement, crateRequirement, crateTinderboxRequirement)
    val mackRequirement = listOf(rawMackerelRequirement, crateRequirement)
    val snailRequirement = listOf(rawSnailRequirement, crateRequirement)

    val secondBankRequirement = listOf(
        secondNailsRequirement, hammerRequirement, swampPasteRequirement, secondBankPlankRequirement, crateRequirement
    )

    val furnaceBankRequirement = listOf(
        hammerRequirement, steelBarRequirement, coalRequirement, singleTinderboxRequirement, blessedSickleRequirement
    )

    val makeRodRequirements = listOf(
        hammerRequirement, sapphireRequirement, coinsRequirement, waterRequirement, ropeRequirement,
        softClayRequirement, silverBarRequirement, mithrilBarRequirement, cosmicRequirement
    )

    val setupNpc = listOf(
        steelChainbodyRequirement,
        steelMedRequirement,
        steelPlatelegsRequirement,
        silverSickleRequirement,
        salmonRequirement
    )

    val NAME_IVAN = "Ivan Strom"
    val TILE_IVAN = Tile(3506, 9837)
    val CONVERSATION_FOOD = arrayOf("Yes, I'll offer all of this food item in my inventory to Ivan.")

    val allRequirements = listOf(
        pickaxeRequirement, bucketRequirement, nailsRequirement, plankRequirement, hammerRequirement,
        spadeRequirement, bronzeAxeRequirement, swampPasteRequirement, rawMackerelRequirement, rawSnailRequirement,
        tinderboxRequirement, steelBarRequirement, coalRequirement, blessedSickleRequirement,
        steelChainbodyRequirement, steelMedRequirement, steelPlatelegsRequirement, coinsRequirement, salmonRequirement,
        cosmicRequirement, sapphireRequirement, coinsRequirement, waterRequirement, ropeRequirement,
        softClayRequirement, silverBarRequirement, mithrilBarRequirement
    )

    const val WIDGET_BOOK = 392
    const val COMPONENT_BOOK_CLOSE = 7

    // 704 tracks mined shit

    //705 165 -> 2725 after putting 5 bronze axe
    //165 (00 axe):                10100101
    //2725(05 axe):          101  010100101
    //5285(10 axe)           1010 010100101
    //87205 (10 2nd)            10101010010100101
    // 480421 (3 tinderbox)   1110101010010100101
    // 480476 (after furnace) 1110101010011011100
    // 1529062 (Wiskit)     101110101010011100110
    // 157988340 (Veliaf not retu) 1001011010101011010111110100
    // 166365948 (Veliaf returned) 1001111010101000101011111100
    //max quest varpbit = 110100100


    const val VARP_MASK_BRONZE_AXE = 15
    const val VARP_MASK_TINDERBOX = 3
    const val VARP_SHIFT_BRONZE_AXE = 9
    const val VARP_SHIFT_SECOND_ITEM = 13
    const val VARP_SHIFT_TINDERBOX = 17

    const val VARP_SHIFT_GADDERANKS = 19
    const val VARP_SHIFT_WISKIT = 20
    const val VARP_SHIFT_JUVINATES = 21
    const val VARP_VELIAF_RETURNED = 23
}