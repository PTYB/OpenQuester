package org.powbot.quests.quest.witcheshouse

import com.open.quester.common.*
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleNpcStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.count
import com.open.quester.extensions.nearestGameObject
import com.open.quester.extensions.nearestNpc
import com.open.quester.helpers.InteractionsHelper
import com.open.quester.models.ItemRequirement
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_WEST_FALADOR
import com.open.quester.quest.witcheshouse.tasks.*
import org.powbot.api.rt4.*
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.AREA_UNDER_WITCHES_HOUSE
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.CONVERSATION_BOY
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.ID_CUPBOARD_CLOSED
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.ID_CUPBOARD_OPEN
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.ITEM_CHEESE
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.ITEM_HOUSE_KEY
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.ITEM_LEATHER_GLOVES
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.ITEM_MAGNET
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.NAME_BOY
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.NAME_MOUSE
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.NAME_MOUSE_HOLE
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.NAME_PLANT
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.TILE_BOY
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.TILE_INFRONT_CUPBOARD
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.TILE_INFRONT_HOUSE
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.TILE_MOUSE_ROOM
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.TILE_PLANT

class WitchesHouse(information: QuestInformation) : BaseQuest(information) {

    private val cheeseRequirement = ItemRequirementCondition(ItemRequirement(ITEM_CHEESE, false, 1))
    private val leatherGlovesRequirement = ItemRequirementCondition(ItemRequirement(ITEM_LEATHER_GLOVES, false, 1))

    private val startQuest = startQuest()

    private val getTheMagnet = getTheMagnet()

    private val killExperiment = killExperiment()

    private val finishQuest = finishQuest()

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(listOf(cheeseRequirement, leatherGlovesRequirement), listOf())
    }

    private fun startQuest(): QuestTaskList {
        val getItems = BankStep(
            listOf(cheeseRequirement, leatherGlovesRequirement, ItemRequirementCondition.emptySlots(2)),
            BANK_WEST_FALADOR,
            information,
            foodRequired = true,
            combat = true,
        )
        val talkToBoy = SimpleConversationStep(NAME_BOY, TILE_BOY, CONVERSATION_BOY, "Starting quest", information)
        return QuestTaskList(getItems, talkToBoy)
    }

    private fun getTheMagnet(): QuestTaskList {
        val equipGloves = EquipItemStep(ITEM_LEATHER_GLOVES, "Wear", Equipment.Slot.HANDS)
        val getHouseKey = SimpleObjectStep(
            TILE_INFRONT_HOUSE,
            arrayOf(),
            { Objects.stream().name(NAME_PLANT).at(TILE_PLANT).nearest().first() },
            { go: GameObject -> go.interact("Look-under") },
            { Chat.chatting() || Inventory.count(ITEM_HOUSE_KEY) == 1 },
            "Getting house key",
            information,
            { Inventory.stream().name(ITEM_HOUSE_KEY).count() < 1 },
        )
        val getMagnet = SimpleObjectStep(
            TILE_INFRONT_CUPBOARD,
            arrayOf(),
            { Objects.stream().id(ID_CUPBOARD_OPEN, ID_CUPBOARD_CLOSED).within(AREA_UNDER_WITCHES_HOUSE).first() },
            { go: GameObject ->
                val action = if (go.id() == ID_CUPBOARD_OPEN) "Search" else "Open"
                go.interact(action)
            },
            { go: GameObject ->
                if (go.id() == ID_CUPBOARD_OPEN) {
                    Chat.chatting()
                } else {
                    !go.valid()
                }
            },
            "Getting Magnet",
            information,
            { Inventory.stream().name(ITEM_MAGNET).count() < 1 },
        )

        val useItemOnMouse = SimpleNpcStep(
            TILE_MOUSE_ROOM,
            arrayOf(),
            { Npcs.nearestNpc(NAME_MOUSE) },
            { InteractionsHelper.useItemOnInteractive(Inventory.stream().name(ITEM_MAGNET).first(), it) },
            { Chat.chatting() },
            "Using magnet on mouse",
            shouldExecute = { Inventory.count(ITEM_MAGNET) > 0 && Npcs.nearestNpc(NAME_MOUSE) != Npc.Nil },
            questInformation = information
        )

        val useCheeseOnHole = SimpleObjectStep(
            TILE_MOUSE_ROOM,
            arrayOf(),
            { Objects.nearestGameObject(NAME_MOUSE_HOLE) },
            { InteractionsHelper.useItemOnInteractive(Inventory.stream().name(ITEM_CHEESE).first(), it) },
            { Npcs.nearestNpc(NAME_MOUSE) != Npc.Nil },
            "Using Cheese on mouse hole",
            information,
            shouldExecute = { Inventory.count(ITEM_MAGNET) > 0 && Npcs.nearestNpc(NAME_MOUSE) === Npc.Nil },
        )

        return QuestTaskList(equipGloves, getHouseKey, getMagnet, useItemOnMouse, useCheeseOnHole)
    }

    private fun killExperiment(): QuestTaskList {
        return QuestTaskList(SetupWeaponStep(information), KillInShack(information), GetShedKey(), EnterShack())
    }

    private fun finishQuest(): QuestTaskList {
        return QuestTaskList(KillInShack(information), LeaveShack(), FinishQuest(information))
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuest.processStep()
            1 -> getTheMagnet.processStep()
            2 -> getTheMagnet.processStep()
            3 -> killExperiment.processStep()
            6 -> finishQuest.processStep()
            7 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                return null
            }
            else -> TODO("Missing ID $stepPosition")
        }
    }
}
