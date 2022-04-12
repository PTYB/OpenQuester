package com.open.quester.quest.daddyshome

import com.open.quester.common.*
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.count
import com.open.quester.models.ItemRequirement
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_VARROCK_EAST
import com.open.quester.quest.daddyshome.DaddysHomeConstants.CAMPBED_SHIFT
import com.open.quester.quest.daddyshome.DaddysHomeConstants.CARPET_SHIFT
import com.open.quester.quest.daddyshome.DaddysHomeConstants.CHAIR_SHIFT
import com.open.quester.quest.daddyshome.DaddysHomeConstants.CLOSE_STOOL_SHIFT
import com.open.quester.quest.daddyshome.DaddysHomeConstants.CLOSE_TABLE_SHIFT
import com.open.quester.quest.daddyshome.DaddysHomeConstants.CONVERSATION_MARLO
import com.open.quester.quest.daddyshome.DaddysHomeConstants.CONVERSATION_MARLO_FINISH
import com.open.quester.quest.daddyshome.DaddysHomeConstants.CONVERSATION_SAWMILL
import com.open.quester.quest.daddyshome.DaddysHomeConstants.CONVERSATION_YARLO
import com.open.quester.quest.daddyshome.DaddysHomeConstants.FAR_ROOM_TILE
import com.open.quester.quest.daddyshome.DaddysHomeConstants.FAR_STOOL_SHIFT
import com.open.quester.quest.daddyshome.DaddysHomeConstants.FAR_TABLE_SHIFT
import com.open.quester.quest.daddyshome.DaddysHomeConstants.HOME_ITEM_MASK
import com.open.quester.quest.daddyshome.DaddysHomeConstants.ID_BED
import com.open.quester.quest.daddyshome.DaddysHomeConstants.ID_BROKEN_BED
import com.open.quester.quest.daddyshome.DaddysHomeConstants.ID_BROKEN_CHAIR
import com.open.quester.quest.daddyshome.DaddysHomeConstants.ID_CHAIR
import com.open.quester.quest.daddyshome.DaddysHomeConstants.ID_CRATE
import com.open.quester.quest.daddyshome.DaddysHomeConstants.ITEM_SAW
import com.open.quester.quest.daddyshome.DaddysHomeConstants.ITEM_WAXWOOD_LOG
import com.open.quester.quest.daddyshome.DaddysHomeConstants.ITEM_WAXWOOD_PLANKS
import com.open.quester.quest.daddyshome.DaddysHomeConstants.NAME_BROKEN_STOOL
import com.open.quester.quest.daddyshome.DaddysHomeConstants.NAME_BROKEN_TABLE
import com.open.quester.quest.daddyshome.DaddysHomeConstants.NAME_CONST_BED
import com.open.quester.quest.daddyshome.DaddysHomeConstants.NAME_CONST_CARPET
import com.open.quester.quest.daddyshome.DaddysHomeConstants.NAME_CONST_CHAIR
import com.open.quester.quest.daddyshome.DaddysHomeConstants.NAME_CONST_STOOL
import com.open.quester.quest.daddyshome.DaddysHomeConstants.NAME_CONST_TABLE
import com.open.quester.quest.daddyshome.DaddysHomeConstants.ID_CARPET
import com.open.quester.quest.daddyshome.DaddysHomeConstants.ID_STOOL
import com.open.quester.quest.daddyshome.DaddysHomeConstants.ID_TABLE
import com.open.quester.quest.daddyshome.DaddysHomeConstants.NAME_MARLO
import com.open.quester.quest.daddyshome.DaddysHomeConstants.NAME_ROTTEN_CARPET
import com.open.quester.quest.daddyshome.DaddysHomeConstants.NAME_SAWMILL_OPERATOR
import com.open.quester.quest.daddyshome.DaddysHomeConstants.NAME_YARLO
import com.open.quester.quest.daddyshome.DaddysHomeConstants.SHIFT_QUEST_INDEX
import com.open.quester.quest.daddyshome.DaddysHomeConstants.TILE_CRATE
import com.open.quester.quest.daddyshome.DaddysHomeConstants.TILE_MARLO
import com.open.quester.quest.daddyshome.DaddysHomeConstants.TILE_SAW
import com.open.quester.quest.daddyshome.DaddysHomeConstants.TILE_SAWMILl
import com.open.quester.quest.daddyshome.DaddysHomeConstants.TILE_YARLO
import com.open.quester.quest.daddyshome.tasks.RepairItem
import org.powbot.api.Point
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import com.open.quester.quest.daddyshome.DaddysHomeConstants as Constants

class DaddysHome(information: QuestInformation) : BaseQuest(information) {
    private val plankRequirement = ItemRequirementCondition(Constants.ITEM_PLANK, false, 10)

    private val clothRequirement = ItemRequirementCondition(Constants.ITEM_BOLT_OF_CLOTH, false, 5)

    private val nailsRequirement = ItemRequirementCondition(
        ItemRequirement(Constants.ITEM_STEEL_NAILS, false, 30),
        ItemRequirement(Constants.ITEM_BRONZE_NAILS, false, 90)
    )
    private val hammerRequirement = ItemRequirementCondition(Constants.ITEM_HAMMER, false, 1)

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(
            listOf(plankRequirement, clothRequirement, nailsRequirement, hammerRequirement),
            listOf()
        )
    }

    private val startQuestStep = QuestTaskList(
        BankStep(
            listOf(plankRequirement, clothRequirement, nailsRequirement, hammerRequirement),
            BANK_VARROCK_EAST, information
        ),
        SimpleConversationStep(NAME_MARLO, TILE_MARLO, CONVERSATION_MARLO, "Talking to Marlo", information)
    )

    private val talkToYarlo =
        SimpleConversationStep(NAME_YARLO, TILE_YARLO, CONVERSATION_YARLO, "Talking to Yarlo", information)

    private val talkToYarloAndPickupSaw = QuestTaskList(
        PickupItemStep(
            TILE_SAW, { GroundItems.stream().name(ITEM_SAW).at(TILE_SAW).nearest().first() },
            { Inventory.count(ITEM_SAW) == 0 }, "Take", "Picking up saw"
            , information),
        talkToYarlo
    )

    private val finishQuest = SimpleConversationStep(
        NAME_MARLO, TILE_MARLO, CONVERSATION_MARLO_FINISH,
        "Talking to Marlo", information
    )

    private val removeBrokenItems = removeBrokenItems()
    private val repairItems = repairItems()

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        val varpbit = stepPosition ushr SHIFT_QUEST_INDEX
        logger.info("Varpbit is $varpbit")
        logger.info("Value is $stepPosition")
        return when (varpbit) {
            0 -> startQuestStep.processStep()
            1 -> talkToYarloAndPickupSaw.processStep()
            2 -> removeBrokenItems.processStep()
            3, 4 -> talkToYarlo
            5, 6, 7, 8, 9 -> repairItems.processStep()
            10, 11, 12 -> finishQuest
            else -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                null
            }
        }
    }

    private fun removeBrokenItems(): QuestTaskList {
        val removeBed = removeItemStep(ID_BROKEN_BED, "Remove", CAMPBED_SHIFT, TILE_YARLO)
        removeBed.pointVariance = Point(-2, -2)
        return QuestTaskList(
            removeItemStep(NAME_ROTTEN_CARPET, "Remove", CARPET_SHIFT, TILE_YARLO),
            removeItemStep(NAME_BROKEN_STOOL, "Demolish", CLOSE_STOOL_SHIFT, TILE_YARLO),
            removeItemStep(NAME_BROKEN_TABLE, "Demolish", CLOSE_TABLE_SHIFT, TILE_YARLO),
            removeItemStep(ID_BROKEN_CHAIR, "Demolish", CHAIR_SHIFT, TILE_YARLO),
            removeBed,
            removeItemStep(NAME_BROKEN_STOOL, "Demolish", FAR_STOOL_SHIFT, FAR_ROOM_TILE),
            removeItemStep(NAME_BROKEN_TABLE, "Demolish", FAR_TABLE_SHIFT, FAR_ROOM_TILE),
            talkToYarlo
        )
    }

    // TODO Remove when fixed - Chair has null name
    private fun removeItemStep(objectId: Int, action: String, shift: Int, roomTile: Tile): SimpleObjectStep {
        return SimpleObjectStep(TILE_YARLO, null, { Objects.stream().id(objectId).nearest(roomTile).first() },
            { go -> go.interact { it.action == action } },
            { Varpbits.varpbit(getQuestVarpbit(), shift, HOME_ITEM_MASK) == 2 && Players.local().animation() == -1 },
            "Removing $objectId", information,
            {
                Varpbits.varpbit(getQuestVarpbit(), shift, HOME_ITEM_MASK) == 1
            })
    }

    private fun removeItemStep(objectName: String, action: String, shift: Int, roomTile: Tile): SimpleObjectStep {
        return SimpleObjectStep(
            TILE_YARLO,
            null,
            { Objects.stream().name(objectName).nearest(roomTile).first() },
            { go -> go.interact { it.action == action } },
            { Varpbits.varpbit(getQuestVarpbit(), shift, HOME_ITEM_MASK) == 2 && Players.local().animation() == -1 },
            "Removing $objectName",
            information,
            {
                Varpbits.varpbit(getQuestVarpbit(), shift, HOME_ITEM_MASK) == 1
            })
    }

    private fun repairItems(): QuestTaskList {
        val talkToSawmill = SimpleConversationStep(
            NAME_SAWMILL_OPERATOR, TILE_SAWMILl, CONVERSATION_SAWMILL,
            "Converting logs", information,
            shouldExecute = { Inventory.count(ITEM_WAXWOOD_LOG) == 3 }
        )
        talkToSawmill.pointVariance = Point(0, -3)

        val getLogs = SimpleObjectStep(
            TILE_CRATE, arrayOf(), { Objects.stream().id(ID_CRATE).nearest(TILE_YARLO).first() },
            { go -> go.interact("Search") }, { Inventory.count(ITEM_WAXWOOD_LOG) >= 3 }, "Grabbing logs",
            information,
            {
                Varpbits.varpbit(getQuestVarpbit(), CAMPBED_SHIFT, HOME_ITEM_MASK) != 3 &&
                        Inventory.count(ITEM_WAXWOOD_LOG, ITEM_WAXWOOD_PLANKS) < 3
            }
        )

        val bed = RepairItem(TILE_YARLO, ID_BED, NAME_CONST_BED, CAMPBED_SHIFT, information, TILE_YARLO)
        bed.pointVariance = Point(-2, -2)

        return QuestTaskList(
            RepairItem(TILE_YARLO, ID_CARPET, NAME_CONST_CARPET, CARPET_SHIFT, information, TILE_YARLO),
            RepairItem(TILE_YARLO, ID_STOOL, NAME_CONST_STOOL, CLOSE_STOOL_SHIFT, information, TILE_YARLO),
            RepairItem(TILE_YARLO, ID_TABLE, NAME_CONST_TABLE, CLOSE_TABLE_SHIFT, information, TILE_YARLO),
            RepairItem(TILE_YARLO, ID_CHAIR, NAME_CONST_CHAIR, CHAIR_SHIFT, information, TILE_YARLO),
            RepairItem(TILE_YARLO, ID_TABLE, NAME_CONST_TABLE, FAR_TABLE_SHIFT, information, FAR_ROOM_TILE),
            RepairItem(TILE_YARLO, ID_STOOL, NAME_CONST_STOOL, FAR_STOOL_SHIFT, information, FAR_ROOM_TILE),
            talkToSawmill,
            getLogs,
            bed,
            talkToYarlo
        )
    }
}