package com.open.quester.quest.naturespirit

import com.open.quester.common.*
import com.open.quester.common.base.*
import com.open.quester.extensions.count
import com.open.quester.extensions.nearestNpc
import com.open.quester.helpers.InteractionsHelper
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_CANIFIS
import com.open.quester.quest.naturespirit.NatureSpiritConstants.ACTION_SEARCH_GROTTO
import com.open.quester.quest.naturespirit.NatureSpiritConstants.ANIMATION_BLOOM
import com.open.quester.quest.naturespirit.NatureSpiritConstants.AREA_INSIDE_GROTTO
import com.open.quester.quest.naturespirit.NatureSpiritConstants.AREA_OUTSIDE_GROTTO
import com.open.quester.quest.naturespirit.NatureSpiritConstants.CONVERSATION_DREZEL
import com.open.quester.quest.naturespirit.NatureSpiritConstants.CONVERSATION_FILLIMAN
import com.open.quester.quest.naturespirit.NatureSpiritConstants.CONVERSATION_FILLIMANCAST
import com.open.quester.quest.naturespirit.NatureSpiritConstants.CONVERSATION_GET_SPELL
import com.open.quester.quest.naturespirit.NatureSpiritConstants.CONVERSATION_GROTTO
import com.open.quester.quest.naturespirit.NatureSpiritConstants.CONVERSATION_NATURE_SPIRIT
import com.open.quester.quest.naturespirit.NatureSpiritConstants.ID_POUCH_WITH
import com.open.quester.quest.naturespirit.NatureSpiritConstants.ITEM_BLESSED_SICKLE
import com.open.quester.quest.naturespirit.NatureSpiritConstants.ITEM_FUNGUS
import com.open.quester.quest.naturespirit.NatureSpiritConstants.ITEM_GHOSTSPEAK_AMULET
import com.open.quester.quest.naturespirit.NatureSpiritConstants.ITEM_JOURNAL
import com.open.quester.quest.naturespirit.NatureSpiritConstants.ITEM_MIRROR
import com.open.quester.quest.naturespirit.NatureSpiritConstants.ITEM_POUCH
import com.open.quester.quest.naturespirit.NatureSpiritConstants.ITEM_SILVER_SICKLE
import com.open.quester.quest.naturespirit.NatureSpiritConstants.ITEM_SPELL
import com.open.quester.quest.naturespirit.NatureSpiritConstants.ITEM_USED_SPELL
import com.open.quester.quest.naturespirit.NatureSpiritConstants.ITEM_WASHING_BOWL
import com.open.quester.quest.naturespirit.NatureSpiritConstants.MISC_FILLIMAN
import com.open.quester.quest.naturespirit.NatureSpiritConstants.NAME_DREZEL
import com.open.quester.quest.naturespirit.NatureSpiritConstants.NAME_FILLMAN
import com.open.quester.quest.naturespirit.NatureSpiritConstants.NAME_FUNGUS_ON_LOG
import com.open.quester.quest.naturespirit.NatureSpiritConstants.NAME_GROTTO
import com.open.quester.quest.naturespirit.NatureSpiritConstants.NAME_GROTTO_TREE
import com.open.quester.quest.naturespirit.NatureSpiritConstants.NAME_NATURE_SPIRIT
import com.open.quester.quest.naturespirit.NatureSpiritConstants.TILE_ALTAR
import com.open.quester.quest.naturespirit.NatureSpiritConstants.TILE_DREZEL
import com.open.quester.quest.naturespirit.NatureSpiritConstants.TILE_FILLMAN
import com.open.quester.quest.naturespirit.NatureSpiritConstants.TILE_GROTTO
import com.open.quester.quest.naturespirit.NatureSpiritConstants.TILE_INSIDE_GROTTO
import com.open.quester.quest.naturespirit.NatureSpiritConstants.TILE_LOTS_LOGS
import com.open.quester.quest.naturespirit.NatureSpiritConstants.TILE_MUSHROOM
import com.open.quester.quest.naturespirit.NatureSpiritConstants.TILE_NATURE_SPIRIT
import com.open.quester.quest.naturespirit.NatureSpiritConstants.TILE_ORANGE_STONE
import com.open.quester.quest.naturespirit.NatureSpiritConstants.TILE_PUCH
import com.open.quester.quest.naturespirit.NatureSpiritConstants.TILE_SINGLE
import com.open.quester.quest.naturespirit.NatureSpiritConstants.TILE_SPELL
import com.open.quester.quest.naturespirit.NatureSpiritConstants.TILE_WASHING_BOWL
import org.powbot.api.Condition
import org.powbot.api.Point
import org.powbot.api.rt4.*

class NatureSpirit(information: QuestInformation) : BaseQuest(information) {
    private val amuletOfGhostSpeak = ItemRequirementCondition(ITEM_GHOSTSPEAK_AMULET, true, 1)
    private val sickleRequirement = ItemRequirementCondition(ITEM_SILVER_SICKLE, false, 1)

    private val startQuest = QuestTaskList(
        BankStep(
            listOf(amuletOfGhostSpeak, sickleRequirement, ItemRequirementCondition.emptySlots(6)),
            BANK_CANIFIS, information, foodRequired = true, combat = true
        ),
        SimpleConversationStep(NAME_DREZEL, TILE_DREZEL, CONVERSATION_DREZEL, "Talking to Drezel", information)
    )

    private val enterGrotto = QuestTaskList(
        SimpleObjectStep(
            TILE_GROTTO, CONVERSATION_GROTTO, NAME_GROTTO, "Enter",
            { Npcs.nearestNpc(NAME_FILLMAN) != Npc.Nil }, "Spawning Fillman", information,
            { Npcs.nearestNpc(NAME_FILLMAN) == Npc.Nil }, forceWeb = true
        ),
        EquipItemStep(ITEM_GHOSTSPEAK_AMULET, "Wear", Equipment.Slot.NECK),
        SimpleConversationStep(
            NAME_FILLMAN, TILE_FILLMAN, CONVERSATION_FILLIMAN, "Talking to Fillman", information,
            shouldExecute = { Npcs.nearestNpc(NAME_FILLMAN) != Npc.Nil && Varpbits.varpbit(getQuestVarpbit()) != 20 })
    )

    private val showUsingMirror = QuestTaskList(
        PickupItemStep(TILE_WASHING_BOWL, { GroundItems.stream().name(ITEM_WASHING_BOWL).nearest().first() }, {
            Inventory.count(ITEM_WASHING_BOWL) == 0
        }, "Take", "Picking up Bowl", information),
        PickupItemStep(TILE_WASHING_BOWL, { GroundItems.stream().name(ITEM_MIRROR).nearest().first() }, {
            Inventory.count(ITEM_WASHING_BOWL) == 1 && Inventory.count(ITEM_MIRROR) == 0
        },
            "Take", "Picking up Mirror", information
        ),
        SimpleObjectStep(TILE_GROTTO, MISC_FILLIMAN, NAME_GROTTO, "Enter",
            { getFilliman() != Npc.Nil }, "Spawning Fillman", information,
            { getFilliman() == Npc.Nil }),
        SimpleNpcStep(TILE_FILLMAN, MISC_FILLIMAN, { getFilliman() },
            { npc: Npc -> InteractionsHelper.useItemOnInteractive(ITEM_MIRROR, npc) },
            { Chat.chatting() }, "Using mirror on Filliman", shouldExecute = { Inventory.count(ITEM_MIRROR) == 1 },
            questInformation = information
        )
    )

    private val searchGrotto = SimpleObjectStep(
        TILE_GROTTO, MISC_FILLIMAN, NAME_GROTTO_TREE, ACTION_SEARCH_GROTTO,
        { Inventory.count(ITEM_JOURNAL) == 1 }, "Getting journal", information,
        { Inventory.count(ITEM_JOURNAL) == 0 }, forceWeb = true
    )
        .also {
            it.pointVariance = Point(0, -4)
        }

    private val getJournal = QuestTaskList(
        DropFoodIfNeeded(information) { Inventory.isFull() && Inventory.count(ITEM_JOURNAL) == 0 },
        searchGrotto,
        SimpleObjectStep(TILE_GROTTO, MISC_FILLIMAN, NAME_GROTTO, "Enter",
            { getFilliman() != Npc.Nil }, "Spawning Fillman", information,
            { getFilliman() == Npc.Nil }),
        SimpleNpcStep(TILE_FILLMAN, CONVERSATION_GET_SPELL, { getFilliman() },
            { npc: Npc -> InteractionsHelper.useItemOnInteractive(ITEM_JOURNAL, npc) },
            { Chat.chatting() }, "Using journal on Filliman", shouldExecute = { Inventory.count(ITEM_JOURNAL) == 1 },
            questInformation = information
        )
    )

    private val offerHelp = QuestTaskList(
        SimpleObjectStep(TILE_GROTTO, CONVERSATION_GET_SPELL, NAME_GROTTO, "Enter",
            { getFilliman() != Npc.Nil }, "Spawning Fillman", information,
            { getFilliman() == Npc.Nil }),
        SimpleConversationStep(
            NAME_FILLMAN, TILE_FILLMAN, CONVERSATION_GET_SPELL, "Talking to Fillman", information,
            shouldExecute = { Npcs.nearestNpc(NAME_FILLMAN) != Npc.Nil })
    )

    private val getBlessing =
        SimpleConversationStep(NAME_DREZEL, TILE_DREZEL, arrayOf(), "Talking to Drezel", information)

    private val getFungus = QuestTaskList(
        DropItemsIfNeeded("Rotten food") { true },
        DropFoodIfNeeded(information) { Inventory.isFull() && Inventory.count(ITEM_FUNGUS) == 0 },
        SimpleObjectStep(TILE_SINGLE, arrayOf(), NAME_FUNGUS_ON_LOG, "Pick",
            { Inventory.count(ITEM_FUNGUS) == 1 }, "Picking fungus", information,
            { Objects.stream().name(NAME_FUNGUS_ON_LOG).within(3).nearest().first() != GameObject.Nil }),
        WalkToExactTile(
            TILE_SINGLE, "Walking to log",
            {
                !AREA_OUTSIDE_GROTTO.contains(Players.local().tile()) && Players.local().tile() != TILE_SINGLE
                        && Inventory.count(ITEM_FUNGUS) == 0
            }, information
        ),
        InteractWithItem(ITEM_SPELL,
            "Cast",
            { Inventory.count(ITEM_FUNGUS) == 0 && Players.local().tile() == TILE_SINGLE },
            {
                Condition.wait { Players.local().animation() == ANIMATION_BLOOM } && Condition.wait {
                    Players.local().animation() == -1
                }
            }
        ),
        SimpleObjectStep(TILE_GROTTO, MISC_FILLIMAN, NAME_GROTTO, "Enter",
            { getFilliman() != Npc.Nil }, "Spawning Fillman", information,
            { getFilliman() == Npc.Nil }),
        SimpleObjectStep(
            TILE_MUSHROOM, null,
            { Objects.stream().at(TILE_MUSHROOM).name("Stone").first() },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_FUNGUS, go) },
            { Inventory.count(ITEM_FUNGUS) == 0 }, "Using mushroom", information,
            shouldExecute = { Inventory.count(ITEM_FUNGUS) == 1 }
        ),
        SimpleObjectStep(
            TILE_SPELL, null,
            { Objects.stream().at(TILE_SPELL).name("Stone").first() },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_USED_SPELL, go) },
            { Inventory.count(ITEM_USED_SPELL) == 0 }, "Using spell", information,
            shouldExecute = { Inventory.count(ITEM_USED_SPELL) == 1 }
        ),
        WalkToExactTile(
            TILE_ORANGE_STONE, "Walking to stone", { Players.local().tile() != TILE_ORANGE_STONE }, information
        ),
        SimpleConversationStep(
            NAME_FILLMAN, TILE_FILLMAN, CONVERSATION_FILLIMANCAST, "Talking to Fillman", information,
            shouldExecute = { Npcs.nearestNpc(NAME_FILLMAN) != Npc.Nil })
    )

    private val talkInGrotto = QuestTaskList(
        SimpleObjectStep(TILE_GROTTO, CONVERSATION_GROTTO, NAME_GROTTO, "Enter",
            { AREA_INSIDE_GROTTO.contains(Players.local()) }, "Entering grotto", information,
            { !AREA_INSIDE_GROTTO.contains(Players.local()) }),
        SimpleObjectStep(
            TILE_ALTAR, arrayOf(), "Grotto", "Search", { Npcs.nearestNpc(NAME_FILLMAN) != Npc.Nil },
            "Searching altar", information,
            { AREA_INSIDE_GROTTO.contains(Players.local()) }
        )
    )

    private val getBlessedSickle = QuestTaskList(
        SimpleObjectStep(TILE_GROTTO, CONVERSATION_GROTTO, NAME_GROTTO, "Enter",
            { AREA_INSIDE_GROTTO.contains(Players.local()) }, "Entering grotto", information,
            { !AREA_INSIDE_GROTTO.contains(Players.local()) }),
        SimpleObjectStep(
            TILE_ALTAR, arrayOf(), "Grotto", "Search",
            { Npcs.nearestNpc(NAME_NATURE_SPIRIT) != Npc.Nil }, "Searching altar", information,
            { Npcs.nearestNpc(NAME_NATURE_SPIRIT) == Npc.Nil }
        ),
        SimpleConversationStep(
            NAME_NATURE_SPIRIT, TILE_NATURE_SPIRIT, CONVERSATION_NATURE_SPIRIT, "Nature spirit", information,
            shouldExecute = { Npcs.nearestNpc(NAME_NATURE_SPIRIT) != Npc.Nil })
    )

    private val killGhosts = QuestTaskList(
        CombatStep(TILE_LOTS_LOGS, information, "Ghast", "Killing ghast")
        { Npcs.stream().name("Ghast").action("Attack").within(8).first() != Npc.Nil },
        SimpleNpcStep(
            TILE_LOTS_LOGS,
            arrayOf(),
            { Npcs.stream().name("Ghast").nearest().first() },
            { npc: Npc -> InteractionsHelper.useItemOnInteractive(ITEM_POUCH, npc) },
            { npc: Npc -> !npc.valid() },
            "Making ghast attackable",
            shouldExecute = { Inventory.count(ID_POUCH_WITH) > 0 },
            questInformation = information
        ),
        PickupItemStep(TILE_PUCH, { GroundItems.stream().name(ITEM_POUCH).nearest().first() }, {
            Inventory.count(
                ITEM_POUCH
            ) == 0
        }, "Take", "Picking up pouch", information),
        SimpleObjectStep(TILE_INSIDE_GROTTO, arrayOf(), NAME_GROTTO, "Exit",
            { !AREA_INSIDE_GROTTO.contains(Players.local()) }, "Exiting grotto", information,
            { AREA_INSIDE_GROTTO.contains(Players.local()) }),
        SimpleObjectStep(TILE_LOTS_LOGS, arrayOf(), NAME_FUNGUS_ON_LOG,
            "Pick",
            { go: GameObject -> !go.valid() },
            "Picking fungus",
            information,
            { Objects.stream().name(NAME_FUNGUS_ON_LOG).within(3).nearest().first() != GameObject.Nil }),
        WalkToExactTile(
            TILE_LOTS_LOGS, "Walking to multiple logs",
            {
                !hasItemsInPouch() && Players.local().tile() != TILE_LOTS_LOGS
            },
            information,
        ),
        InteractWithItem(ITEM_BLESSED_SICKLE, "Cast Bloom", { !hasItemsInPouch() }, {
            Inventory.count(ITEM_FUNGUS) == 0
        }),
        InteractWithItem(ITEM_POUCH, "Fill", { Inventory.count(ITEM_FUNGUS) > 0 },
            { Inventory.count(ITEM_FUNGUS) == 0 })
    )

    private val finishQuest = QuestTaskList(
        SimpleObjectStep(TILE_GROTTO, CONVERSATION_GROTTO, NAME_GROTTO, "Enter",
            { AREA_INSIDE_GROTTO.contains(Players.local()) }, "Entering grotto", information,
            { !AREA_INSIDE_GROTTO.contains(Players.local()) }),
        SimpleObjectStep(
            TILE_ALTAR, arrayOf(), "Grotto", "Search",
            { Npcs.nearestNpc(NAME_NATURE_SPIRIT) != Npc.Nil }, "Searching altar", information,
            { Npcs.nearestNpc(NAME_NATURE_SPIRIT) == Npc.Nil }
        ),
        SimpleConversationStep(
            NAME_NATURE_SPIRIT, TILE_ALTAR, CONVERSATION_NATURE_SPIRIT, "Nature spirit", information,
            shouldExecute = { Npcs.nearestNpc(NAME_NATURE_SPIRIT) != Npc.Nil })
    )

    private fun hasItemsInPouch(): Boolean {
        return Inventory.count(ITEM_FUNGUS) >= 3
    }

    private fun getFilliman(): Npc {
        return Npcs.stream().name(NAME_FILLMAN).nearest().first()
    }

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(listOf(amuletOfGhostSpeak, sickleRequirement), listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuest.processStep()
            1, 2, 3, 4, 5, 10, 15 -> enterGrotto.processStep()
            20 -> showUsingMirror.processStep()
            25 -> getJournal.processStep()
            30 -> offerHelp.processStep()
            35 -> getBlessing
            40, 45, 50, 55 -> getFungus.processStep()
            60 -> talkInGrotto.processStep()
            65, 70 -> getBlessedSickle.processStep()
            75, 80, 85, 90, 95, 100 -> killGhosts.processStep()
            105 -> finishQuest.processStep()
            110 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                null
            }
            else -> TODO("Unable to find step $stepPosition")
        }
    }
}