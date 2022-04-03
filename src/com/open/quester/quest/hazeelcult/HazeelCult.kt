package com.open.quester.quest.hazeelcult

import com.open.quester.common.*
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleNpcStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.count
import com.open.quester.extensions.nearestNpc
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_SOUTH_ARDOUGNE
import org.powbot.api.rt4.*
import org.powbot.mobile.script.ScriptManager
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.CONVERSATION_CERIL
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.CONVERSATION_SIDE_CERIL
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.ITEM_ARMOUR
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.NAME_ALOMONE
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.NAME_CERIL
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.NAME_CLIVET
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.TILE_ALOMONE
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.TILE_CERIL
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.TILE_CERIL_UPSTAIRS
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.TILE_CLIVET
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.TILE_VALVE1
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.TILE_VALVE2
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.TILE_VALVE3
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.TILE_VALVE4
import org.powbot.quests.quest.hazeelcult.HazeelCultConstants.TILE_VALVE5
import com.open.quester.quest.hazeelcult.steps.Rotation
import com.open.quester.quest.hazeelcult.steps.TurnWheel

class HazeelCult(information: QuestInformation) : BaseQuest(information) {

    private val startQuest = startQuest()
    private val talkToClivet = SimpleConversationStep(
        NAME_CLIVET, TILE_CLIVET, CONVERSATION_SIDE_CERIL,
        "Talking to Clivet", information
    )
    private val turnWheels = turnWheels()
    private val getTheButler = getTheButler()
    private val finishQuest = finishQuest()

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(listOf(), listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuest.processStep()
            2, 3 -> talkToClivet
            4 -> turnWheels.processStep()
            6 -> getTheButler.processStep()
            7 -> {
                CommonMethods.closeQuestComplete()
                finishQuest.processStep()
            }
            9 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                return null
            }
            else -> {
                logger.info("Missing step $stepPosition")
                ScriptManager.stop()
                null
            }
        }
    }

    private fun finishQuest(): QuestTaskList {
        val searchCupboard = SimpleObjectStep(
            TILE_CERIL_UPSTAIRS,
            arrayOf(),
            { Objects.stream().name("Cupboard").nearest().first() },
            { go: GameObject ->
                val action = if (go.actions().contains("Search")) "Search" else "Open"
                go.interact(action)
            },
            { go: GameObject ->
                if (go.actions().contains("Search")) Chat.chatting() else !go.valid()
            },
            "Searching cupboard",
            information,
        )
        return QuestTaskList(
            searchCupboard
        )
    }

    private fun getTheButler(): QuestTaskList {
        return QuestTaskList(
            PickupItemStep(
                TILE_ALOMONE, { GroundItems.stream().name(ITEM_ARMOUR).nearest().first() },
                {
                    Inventory.count(ITEM_ARMOUR) == 0 && GroundItems.stream().name(ITEM_ARMOUR)
                        .nearest().first() != GroundItem.Nil
                },
                "Take", "Picking up item"
            ),
            SimpleNpcStep(
                TILE_CERIL_UPSTAIRS, arrayOf(),
                { Npcs.stream().name(NAME_CERIL).filtered { it.floor() != 0 }.first() },
                { npc: Npc -> npc.interact("Talk-to") },
                { Chat.chatting() }, "Talking to Ceril", questInformation = information
            )
        )
    }

    private fun turnWheels(): QuestTaskList {
        return QuestTaskList(
            TurnWheel(Rotation.Right, TILE_VALVE1, information),
            TurnWheel(Rotation.Right, TILE_VALVE2, information),
            TurnWheel(Rotation.Left, TILE_VALVE3, information),
            TurnWheel(Rotation.Right, TILE_VALVE4, information),
            TurnWheel(Rotation.Right, TILE_VALVE5, information),
            PickupItemStep(
                TILE_ALOMONE, { GroundItems.stream().name(ITEM_ARMOUR).nearest().first() },
                {
                    Inventory.count(ITEM_ARMOUR) == 0 && GroundItems.stream().name(ITEM_ARMOUR)
                        .nearest().first() != GroundItem.Nil
                },
                "Take", "Picking up item"
            ),
            SetupWeaponStep(information),
            KillNpcStep(TILE_ALOMONE, { Npcs.nearestNpc(NAME_ALOMONE) }, arrayOf(ITEM_ARMOUR), {
                GroundItems.stream().name(ITEM_ARMOUR)
                    .nearest().toList()
            }, { Inventory.count(ITEM_ARMOUR) == 0 }, information, "Killing Alomone")
        )
    }

    private fun startQuest(): QuestTaskList {
        return QuestTaskList(
            BankStep(
                listOf(ItemRequirementCondition.emptySlots(1)),
                BANK_SOUTH_ARDOUGNE,
                information,
                foodRequired = true,
                combat = true
            ),
            SimpleConversationStep(NAME_CERIL, TILE_CERIL, CONVERSATION_CERIL, "Talking to Ceril", information)
        )
    }
}