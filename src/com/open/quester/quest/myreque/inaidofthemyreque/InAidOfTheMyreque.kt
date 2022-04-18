package com.open.quester.quest.myreque.inaidofthemyreque

import com.open.quester.common.*
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleNpcStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.count
import com.open.quester.extensions.nearestGameObject
import com.open.quester.extensions.nearestNpc
import com.open.quester.helpers.InteractionsHelper
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.models.SkillRequirement
import com.open.quester.quest.Constants.BANK_BURG
import com.open.quester.quest.Constants.BANK_CANIFIS
import com.open.quester.quest.Constants.ITEM_HAMMER
import com.open.quester.quest.myreque.MyrequeConstants.NAME_VELIAF
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.AREA_BASE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.AREA_BURG
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.AREA_HIDDEN_LIBRARY
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.AREA_SECRET_BASE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.AREA_TOMB
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.AREA_UNDER_PUB
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.COMPONENT_BOOK_CLOSE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.CONVERSATION_AUREL
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.CONVERSATION_AUREL_CRATE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.CONVERSATION_AUREL_CRATE_DONE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.CONVERSATION_CLOSE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.CONVERSATION_CORNELIUS
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.CONVERSATION_DO_NEXT
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.CONVERSATION_DREZEL
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.CONVERSATION_FIX
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.CONVERSATION_FOOD
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.CONVERSATION_RAZVAN
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.CONVERSATION_START_QUEST
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_BLESSED_SICKLE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_BOOK
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_BRONZE_AXE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_BUCKET_OF_RUBBLE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_CRATE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_MITHRIL_BAR
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_PLANK
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_RAW_MACKEREL
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_RAW_SNAIL
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_ROD_MOULD
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_SALMON
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_SILVERTHRILL_ROD
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_SILVER_SICKLE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_SOFT_CLAY
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_STEEL_BAR
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_STEEL_CHAINBODY
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_STEEL_MED
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_STEEL_PLATELEGS
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.ITEM_TINDERBOX
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_AUREL
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_BANK_BOOTH
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_BOARDS
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_BROKEN_FURNACE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_CAVE_ENTRANCE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_CHEST
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_CORNELIUS
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_DAMAGED_WALL
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_DREZEL
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_FLORIN
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_GADDERANKS
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_IVAN
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_JUVINATE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_KEYHOLE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_PLAQUE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_POLAMFI
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_RAZVAN
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_REPAIRED_FURNACE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_RUBBLE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_RUBBLE_PILE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_TOMB
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_WISKIT
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_AUREL
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_AUREL_LADDER
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_BANK_BOOTH
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_BANK_WALL
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_BOARDS
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_CORNELIUS
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_DAMAGED_WALL
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_DREZEL
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_FLORIN
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_FURNACE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_GENERAL_STORE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_IVAN
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_NEAR_EDGEVILLE_FURNACE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_NEAR_EXIT_TOMB
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_NEAR_OPEN_CHEST
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_NEAR_RUBBLE_PILE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_NEAR_TOMB
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_NEAR_WELL
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_POLMAFI
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_RAZVAN
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_RUBBLE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.TILE_VELIAF
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.VARP_MASK_BRONZE_AXE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.VARP_MASK_TINDERBOX
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.VARP_SHIFT_BRONZE_AXE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.VARP_SHIFT_GADDERANKS
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.VARP_SHIFT_JUVINATES
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.VARP_SHIFT_SECOND_ITEM
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.VARP_SHIFT_TINDERBOX
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.VARP_SHIFT_WISKIT
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.WIDGET_BOOK
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.allRequirements
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.coinsRequirement
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.fillItemRequirement
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.firstBankRequirements
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.furnaceBankRequirement
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.mackRequirement
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.makeRodRequirements
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.rodRequirement
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.secondBankRequirement
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.setupNpc
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.snailRequirement
import com.open.quester.quest.myreque.inaidofthemyreque.leafs.EnchantRod
import com.open.quester.quest.myreque.inaidofthemyreque.leafs.FigureOutCrateStatus
import com.open.quester.quest.myreque.inaidofthemyreque.leafs.KillVampyres
import org.powbot.api.Point
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.api.rt4.walking.model.Skill
import org.powbot.mobile.script.ScriptManager

class InAidOfTheMyreque(information: QuestInformation) : BaseQuest(information) {

    private val crateStatus = FigureOutCrateStatus()

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(
            allRequirements, listOf(
                SkillRequirement(Skill.Agility.index, 25),
                SkillRequirement(Skill.Crafting.index, 25),
                SkillRequirement(Skill.Mining.index, 15),
                SkillRequirement(Skill.Magic.index, 7),
            )
        )
    }

    private val startQuest = QuestTaskList(
        BankStep(firstBankRequirements, BANK_CANIFIS, information, combat = true, foodRequired = true),
        SimpleConversationStep(NAME_VELIAF, TILE_VELIAF, CONVERSATION_START_QUEST, "Talking to Veliaf", information)
    )
    private val talkToFlorin = SimpleConversationStep(
        NAME_FLORIN, TILE_FLORIN, CONVERSATION_CLOSE, "Talking to Florin", information
    ).also { it.pointVariance = Point(0, 6) }

    private val putFoodInChest = SimpleObjectStep(
        TILE_NEAR_OPEN_CHEST, CONVERSATION_CLOSE,
        { Objects.stream(TILE_NEAR_OPEN_CHEST, 5, GameObject.Type.INTERACTIVE).name(NAME_CHEST).nearest().first() },
        { go: GameObject -> InteractionsHelper.useItemOnInteractive(information.foodName.first(), go) },
        { Chat.chatting() },
        "Putting food in chest", information
    )

    private val talkToRazvan = SimpleConversationStep(
        NAME_RAZVAN, TILE_RAZVAN, CONVERSATION_RAZVAN, "Talking to Razvan",
        information
    )

    private val mineRubble = SimpleObjectStep(
        TILE_RUBBLE, arrayOf(), { Objects.nearestGameObject(NAME_RUBBLE) },
        { go: GameObject ->
            val pick = Inventory.stream().filtered { it.name().contains(" pickaxe") }.first()
            InteractionsHelper.useItemOnInteractive(pick, go)
        },
        { Varpbits.varpbit(getQuestVarpbit()) == 80 }, "Mining rubble", information
    )

    private val mineShitUnderground = QuestTaskList(
        DropItemsIfNeeded("Rock", "Broken glass", conditions = { true }),
        SimpleObjectStep(TILE_RUBBLE,
            arrayOf(),
            { Objects.nearestGameObject("Trapdoor") },
            { go: GameObject ->
                val action = if (go.actions().contains("Climb-down")) "Climb-down" else "Open"
                go.interact(action)
            },
            { go: GameObject -> !go.valid() },
            "Going down under",
            information,
            { AREA_BURG.contains(Players.local()) }
        ),
        SimpleObjectStep(Tile.Nil, arrayOf(), { Objects.stream().name("Rubble").reachable().nearest().first() },
            { go: GameObject ->
                val action = if (go.actions().contains("Mine")) "Mine" else "Remove"
                go.interact(action)
            },
            { go: GameObject ->
                val action = if (go.actions().contains("Mine")) "Mine" else "Remove"
                val newObject = Objects.stream(go.tile, 0).name("Rubble").first()
                newObject == GameObject.Nil || !newObject.actions().contains(action)
            }, "Removing rubble",
            information, { Objects.stream().name("Rubble").action("Mine", "Remove").first() != GameObject.Nil })
    )

    private val talkToRazAfterClearing = QuestTaskList(
        SimpleObjectStep(Tile.Nil,
            arrayOf(),
            "Ladder",
            "Climb-up",
            { AREA_BURG.contains(Players.local()) },
            "Leaving pub",
            information,
            { Objects.nearestGameObject(NAME_PLAQUE) != GameObject.Nil }),
        getRubble(5), getRubble(4), getRubble(3), getRubble(2), getRubble(1),
        SimpleConversationStep(NAME_AUREL, TILE_AUREL, CONVERSATION_AUREL, "Talking to Aurel", information),
    )

    private val fixRoof = QuestTaskList(
        SimpleObjectStep(
            TILE_AUREL_LADDER, arrayOf("Ok thanks."), { Objects.stream(TILE_AUREL_LADDER, 3).name("Ladder").first() },
            { go: GameObject -> go.interact("Climb-up") }, { Players.local().floor() == 2 }, "Climbing ladder",
            information, { Players.local().floor() != 2 }
        ),
        SimpleObjectStep(
            Tile.Nil, arrayOf(), { Objects.nearestGameObject("Broken Roof") },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_PLANK, go) },
            { Inventory.count(ITEM_PLANK) < 6 }, "Fixing roof", information
        )
    )

    private val fixWall = QuestTaskList(
        SimpleObjectStep(
            TILE_AUREL_LADDER, arrayOf(), "Ladder", "Climb-down",
            { Players.local().floor() == 0 }, "Climbing ladder", information, { Players.local().floor() == 2 }
        ),
        SimpleObjectStep(
            TILE_DAMAGED_WALL, arrayOf(), { Objects.nearestGameObject("Damaged wall") },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_PLANK, go) },
            { Inventory.count(ITEM_PLANK) == 0 }, "Climbing ladder", information, { Players.local().floor() == 0 }
        ),
    )
    private val askForCrate = SimpleConversationStep(
        NAME_AUREL, TILE_AUREL, CONVERSATION_AUREL_CRATE,
        "Talking to Aurel", information
    )

    private val bankForCrate = QuestTaskList(
        crateStatus,
        BankStep(fillItemRequirement, BANK_CANIFIS, information,
            {
                Varpbits.varpbit(getQuestVarpbit(), VARP_SHIFT_BRONZE_AXE, VARP_MASK_BRONZE_AXE) != 10 ||
                        Varpbits.varpbit(getQuestVarpbit(), VARP_SHIFT_TINDERBOX, VARP_MASK_TINDERBOX) != 3
            }),
        CombineItemStep(
            ITEM_BRONZE_AXE, ITEM_CRATE, "Putting axes in crate", { Inventory.count(ITEM_BRONZE_AXE) > 0 }, false,
            arrayOf("Yes, I'll fill the crate.")
        ),
        CombineItemStep(
            ITEM_TINDERBOX, ITEM_CRATE, "Putting tinderbox in crate", { Inventory.count(ITEM_TINDERBOX) > 0 }, false,
            arrayOf("Yes, I'll fill the crate.")
        ),
        BankStep(mackRequirement, BANK_CANIFIS, information,
            {
                crateStatus.status == CrateStatus.MACKEREL && Varpbits.varpbit(
                    getQuestVarpbit(),
                    VARP_SHIFT_SECOND_ITEM,
                    VARP_MASK_BRONZE_AXE
                ) != 10
            }),
        BankStep(
            snailRequirement, BANK_CANIFIS, information,
            {
                crateStatus.status == CrateStatus.SNAIL && Varpbits.varpbit(
                    getQuestVarpbit(),
                    VARP_SHIFT_SECOND_ITEM,
                    VARP_MASK_BRONZE_AXE
                ) != 10
            }),

        CombineItemStep(
            ITEM_RAW_MACKEREL,
            ITEM_CRATE,
            "Putting mackerel in crate",
            { Inventory.count(ITEM_RAW_MACKEREL) > 0 },
            false,
            arrayOf("Yes, I'll fill the crate."),
        ),
        CombineItemStep(
            ITEM_RAW_SNAIL, ITEM_CRATE, "Putting axes in crate", { Inventory.count(ITEM_RAW_SNAIL) > 0 }, false,
            arrayOf("Yes, I'll fill the crate."),
        ),

        BankStep(secondBankRequirement, BANK_CANIFIS, information, {
            Varpbits.varpbit(getQuestVarpbit(), VARP_SHIFT_BRONZE_AXE, VARP_MASK_BRONZE_AXE) == 10 &&
                    Varpbits.varpbit(getQuestVarpbit(), VARP_SHIFT_TINDERBOX, VARP_MASK_TINDERBOX) == 3 &&
                    Varpbits.varpbit(getQuestVarpbit(), VARP_SHIFT_SECOND_ITEM, VARP_MASK_BRONZE_AXE) == 10
        }, combat = true, foodRequired = true),
        SimpleConversationStep(
            NAME_AUREL, TILE_AUREL, CONVERSATION_AUREL_CRATE_DONE,
            "Talking to Aurel", information
        )
    )

    private val repairBooth = SimpleObjectStep(
        TILE_BANK_BOOTH, CONVERSATION_FIX,
        { Objects.stream().name(NAME_BANK_BOOTH).action("Inspect").first() },
        { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_PLANK, go) },
        { go: GameObject -> !go.valid() },
        "Fixing bank booth", information
    )

    private val repairBankWall = SimpleObjectStep(
        TILE_BANK_WALL, CONVERSATION_FIX,
        { Objects.stream().name(NAME_DAMAGED_WALL).action("Inspect").first() },
        { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_PLANK, go) },
        { go: GameObject -> !go.valid() },
        "Fixing bank wall", information
    )

    private val talkToCornelius = SimpleConversationStep(
        NAME_CORNELIUS, TILE_CORNELIUS, CONVERSATION_CORNELIUS, "Talking to Cornelius",
        information
    )


    private val talkToRazvanNext = QuestTaskList(
        BankStep(
            furnaceBankRequirement, BANK_BURG, information,
            { Equipment.itemAt(Equipment.Slot.MAIN_HAND).name() != ITEM_BLESSED_SICKLE }, foodRequired = true
        ),
        EquipItemStep(ITEM_BLESSED_SICKLE, "Wield", Equipment.Slot.MAIN_HAND, false),
        SimpleConversationStep(
            NAME_RAZVAN, TILE_RAZVAN, CONVERSATION_DO_NEXT, "Talking to Razvan",
            information
        )
    )

    private val fixFurnace =
        SimpleObjectStep(TILE_FURNACE, arrayOf("Okay, thanks."), { Objects.nearestGameObject(NAME_BROKEN_FURNACE) },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_STEEL_BAR, go) },
            { Inventory.count(ITEM_STEEL_BAR) == 0 }, "Fixing furnace", information,
            { Inventory.count(ITEM_STEEL_BAR) >= 2 })


    private val lightFurnace = QuestTaskList(
        SimpleObjectStep(TILE_FURNACE, arrayOf("Okay, thanks."), { Objects.nearestGameObject(NAME_REPAIRED_FURNACE) },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive(InAidOfTheMyrequeConstants.ITEM_COAL, go) },
            { Inventory.count(InAidOfTheMyrequeConstants.ITEM_COAL) == 0 }, "Fixing furnace", information,
            { Inventory.count(InAidOfTheMyrequeConstants.ITEM_COAL) >= 1 }
        ),
        SimpleObjectStep(
            TILE_FURNACE, arrayOf(), { Objects.nearestGameObject(NAME_REPAIRED_FURNACE) },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_TINDERBOX, go) },
            { Chat.chatting() }, "Lighting the furnace", information
        )
    )

    private val talkToShop = QuestTaskList(
        EquipItemStep(ITEM_BLESSED_SICKLE, "Wield", Equipment.Slot.MAIN_HAND, false),
        SimpleConversationStep(NAME_WISKIT, TILE_GENERAL_STORE, arrayOf(), "Talking to Wiskit", information,
            shouldExecute = { Varpbits.varpbit(getQuestVarpbit(), VARP_SHIFT_WISKIT, 0x1) == 0 }),
        SimpleConversationStep(
            NAME_GADDERANKS, TILE_GENERAL_STORE, arrayOf(), "Talking to Gadderanks", information,
            shouldExecute = { Varpbits.varpbit(getQuestVarpbit(), VARP_SHIFT_GADDERANKS, 0x1) == 0 }),
        SimpleConversationStep(
            NAME_JUVINATE, TILE_GENERAL_STORE, arrayOf(), "Talking to Juvinate", information,
            shouldExecute = { Varpbits.varpbit(getQuestVarpbit(), VARP_SHIFT_JUVINATES, 0x1) == 0 }),
        WaitStep({ true }, "Waiting for the battle"),
    )

    private val killingGadderank = QuestTaskList(
        KillNpcStep(
            Tile.Nil,
            { Npcs.stream().name(NAME_JUVINATE).action("Attack").first() },
            null,
            null,
            { Npcs.stream().name(NAME_JUVINATE).action("Attack").first() != Npc.Nil },
            information,
            "Killing Juvinates"
        ),
        KillNpcStep(
            Tile.Nil,
            { Npcs.stream().name(NAME_GADDERANKS).action("Attack").first() },
            null,
            null,
            { Npcs.stream().name(NAME_GADDERANKS).action("Attack").first() != Npc.Nil },
            information,
            "Killing Gadderank"
        ),
        SimpleConversationStep(NAME_GADDERANKS, Tile.Nil, arrayOf(), "Talking to Gadderanks", information),
    )
    private val talkToVeliaf = QuestTaskList(
        SimpleConversationStep(
            NAME_VELIAF,
            TILE_GENERAL_STORE,
            arrayOf(),
            "Talking to Veliaf",
            information,
            shouldExecute = { Npcs.nearestNpc(NAME_VELIAF) != Npc.Nil }
        ),
        BankStep(
            listOf(coinsRequirement), BANK_CANIFIS, information, { AREA_BURG.contains(Players.local()) },
            foodRequired = true,
        ),
        BankStep(
            setupNpc, BANK_CANIFIS, information, { !AREA_SECRET_BASE.contains(Players.local()) },
            foodRequired = true
        ),
        SimpleConversationStep(
            NAME_VELIAF,
            TILE_VELIAF,
            arrayOf(),
            "Talking to Veliaf",
            information,
        ),
    )

    private val talkToPolamafi = SimpleConversationStep(
        NAME_POLAMFI,
        TILE_POLMAFI,
        arrayOf("I'd best be off."),
        "Talking to Polamfi",
        information
    )

    private val combatTime = QuestTaskList(
        KillVampyres(information),
        InteractWithWidget(53, 28, "Select"),
        UseItemsOnIvan(ITEM_STEEL_PLATELEGS) { Inventory.count(ITEM_STEEL_PLATELEGS) == 1 },
        UseItemsOnIvan(ITEM_STEEL_CHAINBODY) { Inventory.count(ITEM_STEEL_CHAINBODY) == 1 },
        UseItemsOnIvan(ITEM_STEEL_MED) { Inventory.count(ITEM_STEEL_MED) == 1 },
        UseItemsOnIvan(ITEM_SILVER_SICKLE) { Inventory.count(ITEM_SILVER_SICKLE) == 1 },
        UseItemsOnIvan(ITEM_SALMON) { Inventory.count(ITEM_SALMON) >= 15 },
        SimpleConversationStep(NAME_IVAN, TILE_IVAN, arrayOf(), "Starting temple trekking", information)
    )

    private fun UseItemsOnIvan(itemName: String, condition: () -> Boolean): SimpleNpcStep {
        return SimpleNpcStep(
            TILE_IVAN, CONVERSATION_FOOD, { Npcs.nearestNpc(NAME_IVAN) },
            { npc: Npc -> InteractionsHelper.useItemOnInteractive(itemName, npc) },
            { Chat.chatting() }, "Giving $itemName to Ivan", shouldExecute = condition, questInformation = information
        )
    }

    private val talkToDrezel = SimpleConversationStep(
        NAME_DREZEL, TILE_DREZEL, CONVERSATION_DREZEL, "Talknig to Drezel",
        information
    )

    private val enterRoom = QuestTaskList(
        SimpleObjectStep(TILE_DREZEL, arrayOf(), "Trapdoor", "Climb-down",
            { go: GameObject -> !go.valid() }, "Opening trap door", information,
            { Objects.nearestGameObject("Trapdoor") != GameObject.Nil }
        ),
        SimpleObjectStep(
            TILE_DREZEL, arrayOf(), { Objects.nearestGameObject(NAME_KEYHOLE) },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive("Temple library key", go) },
            { Objects.nearestGameObject("Trapdoor") != GameObject.Nil }, "Opening trap door", information
        )
    )

    private val readBook = QuestTaskList(
        CloseWidget(WIDGET_BOOK, COMPONENT_BOOK_CLOSE),
        InteractWithItem(ITEM_BOOK, "Read",
            { Inventory.count(ITEM_BOOK) > 0 },
            { Widgets.component(WIDGET_BOOK, COMPONENT_BOOK_CLOSE).visible() }),
        SimpleObjectStep(Tile.Nil, arrayOf(), "Bookcase", "Search",
            { Inventory.count(ITEM_BOOK) == 1 }, "Getting book", information, { Inventory.count(ITEM_BOOK) == 0 })
    )

    private val enterTomb = QuestTaskList(
        CloseWidget(WIDGET_BOOK, COMPONENT_BOOK_CLOSE),
        SimpleObjectStep(Tile.Nil, arrayOf(), "Ladder", "Climb-up", { !AREA_HIDDEN_LIBRARY.contains(Players.local()) },
            "Exiting library", information, { AREA_HIDDEN_LIBRARY.contains(Players.local()) }),
        BankStep(
            makeRodRequirements, BANK_CANIFIS, information, { Inventory.count(ITEM_ROD_MOULD) == 0 },
            false, false
        ),
        WalkToExactTile(
            TILE_BOARDS, "Walking to boards",
            { !AREA_BASE.contains(Players.local()) && Inventory.count(ITEM_ROD_MOULD) == 0 }, information
        ),
        SimpleObjectStep(
            TILE_BOARDS,
            arrayOf("Yes."),
            { Objects.stream().name(NAME_BOARDS).within(TILE_BOARDS, 3).first() },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_HAMMER, go) },
            { go: GameObject -> !go.valid() },
            "Breaking boards",
            information,
            {
                AREA_BASE.contains(Players.local()) && Objects.stream().name(NAME_BOARDS).within(TILE_BOARDS, 3)
                    .first() != GameObject.Nil
            }
        ),
        SimpleObjectStep(TILE_BOARDS,
            arrayOf(),
            { Objects.stream().name(NAME_CAVE_ENTRANCE).within(TILE_BOARDS, 3).first() },
            { go: GameObject -> go.interact("Enter") },
            { !AREA_BASE.contains(Players.local()) },
            "Getting mould",
            information,
            { AREA_BASE.contains(Players.local()) && Inventory.count(ITEM_ROD_MOULD) == 0 })
    )

    private val getMould = SimpleObjectStep(TILE_NEAR_TOMB, arrayOf(), { Objects.nearestGameObject(NAME_TOMB) },
        { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_SOFT_CLAY, go) },
        { Chat.chatting() }, "Getting Mould", information, {
            AREA_TOMB.contains(Players.local()) && Inventory.count(
                ITEM_ROD_MOULD
            ) == 0
        })


    private val makeStaff = QuestTaskList(
        InteractWithWidget(6, 29, "Craft", { Inventory.count(ITEM_SILVERTHRILL_ROD) == 1 }),
        SimpleObjectStep(TILE_NEAR_EXIT_TOMB, arrayOf(), { Objects.nearestGameObject(NAME_CAVE_ENTRANCE) },
            { go: GameObject -> go.interact("Enter") },
            { !AREA_TOMB.contains(Players.local()) }, "Exiting Tomb", information, {
                AREA_TOMB.contains(Players.local()) && Inventory.count(
                    ITEM_ROD_MOULD
                ) == 1
            }),
        SimpleObjectStep(TILE_NEAR_EDGEVILLE_FURNACE,
            arrayOf(),
            "Furnace",
            "Smelt",
            { Widgets.component(6, 29).visible() },
            "Making rod",
            information,
            { Inventory.count(ITEM_MITHRIL_BAR) == 1 }),
    )

    private val enchantRod = EnchantRod()

    private val dipRod = SimpleObjectStep(
        TILE_NEAR_WELL, arrayOf(), { Objects.stream().name("Well").first() },
        { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_SILVERTHRILL_ROD, go) },
        { Inventory.count(7369) == 1 }, "Dipping rod in well", information
    )

    private val finishQuest = QuestTaskList(
        SimpleConversationStep(NAME_VELIAF, Tile.Nil, arrayOf(), "Talking to Veliaf", information,
            shouldExecute = { AREA_UNDER_PUB.contains(Players.local()) }),
        BankStep(listOf(rodRequirement), BANK_CANIFIS, information, foodRequired = true),
        SimpleObjectStep(
            TILE_RUBBLE,
            arrayOf(),
            { Objects.nearestGameObject("Trapdoor") },
            { go: GameObject ->
                val action = if (go.actions().contains("Climb-down")) "Climb-down" else "Open"
                go.interact(action)
            },
            { go: GameObject -> !go.valid() },
            "Going down under",
            information,
        ),
    )

    private fun getRubble(count: Int): SimpleObjectStep {
        return SimpleObjectStep(TILE_NEAR_RUBBLE_PILE,
            arrayOf(),
            { Objects.nearestGameObject(NAME_RUBBLE_PILE) },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_BUCKET_OF_RUBBLE, go) },
            { Inventory.count(ITEM_BUCKET_OF_RUBBLE) == count - 1 },
            "Emptying bucket of rubble",
            information,
            { Inventory.count(ITEM_BUCKET_OF_RUBBLE) == count })
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition and 511) {
            0 -> startQuest.processStep()
            20 -> talkToFlorin
            30 -> putFoodInChest
            40, 50 -> talkToRazvan
            60, 70 -> mineRubble
            80, 90, 100 -> mineShitUnderground.processStep()
            110 -> talkToRazAfterClearing.processStep()
            140 -> fixRoof.processStep()
            150 -> fixWall.processStep()
            160 -> askForCrate
            165 -> bankForCrate.processStep()
            170 -> repairBooth
            180 -> repairBankWall
            190 -> talkToCornelius
            200 -> talkToRazvanNext.processStep()
            205 -> fixFurnace
            210, 220 -> lightFurnace.processStep()
            230 -> talkToShop.processStep()
            240, 250, 260 -> killingGadderank.processStep()
            280 -> talkToVeliaf.processStep()
            290 -> talkToPolamafi
            300, 310 -> combatTime.processStep() // TODO Press travel button
            315, 320, 330, 340 -> talkToDrezel
            350 -> enterRoom.processStep()
            360, 370 -> readBook.processStep()
            375 -> enterTomb.processStep()
            380 -> getMould
            390 -> makeStaff.processStep()
            400 -> enchantRod
            410 -> dipRod
            420, 425 -> finishQuest.processStep()
            430 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                null
            }
            else -> {
                logger.info("Missing step $stepPosition, ${stepPosition and 511}")
                ScriptManager.stop()
                null
            }
        }
    }


    enum class CrateStatus {
        UNKNOWN, SNAIL, MACKEREL
    }
}