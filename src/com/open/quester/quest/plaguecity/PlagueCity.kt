package com.open.quester.quest.plaguecity

import com.open.quester.common.*
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleNpcStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.Conditions
import com.open.quester.extensions.nearestGameObject
import com.open.quester.helpers.InteractionsHelper
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_NORTH_ARDOUGNE
import com.open.quester.quest.Constants.ITEM_BUCKET_OF_WATER
import com.open.quester.quest.Constants.ITEM_MILK
import com.open.quester.quest.Constants.ITEM_ROPE
import com.open.quester.quest.Constants.ITEM_SPADE
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import com.open.quester.quest.plaguecity.PlagueCityConstants.ACTION_GRILL
import com.open.quester.quest.plaguecity.PlagueCityConstants.AREA_EAST_ARDOUGNE
import com.open.quester.quest.plaguecity.PlagueCityConstants.AREA_PLAGUE_HOUSE_DOWNSTAIRS
import com.open.quester.quest.plaguecity.PlagueCityConstants.AREA_PLAGUE_HOUSE_UPSTAIRS
import com.open.quester.quest.plaguecity.PlagueCityConstants.AREA_UNDERGROUND
import com.open.quester.quest.plaguecity.PlagueCityConstants.AREA_WEST_ARDOUGNE
import com.open.quester.quest.plaguecity.PlagueCityConstants.CONVERSATION_BRAVEK
import com.open.quester.quest.plaguecity.PlagueCityConstants.CONVERSATION_BRAVEK_AGAIN
import com.open.quester.quest.plaguecity.PlagueCityConstants.CONVERSATION_CLERK
import com.open.quester.quest.plaguecity.PlagueCityConstants.CONVERSATION_EDMOND_START
import com.open.quester.quest.plaguecity.PlagueCityConstants.CONVERSATION_JETHICK
import com.open.quester.quest.plaguecity.PlagueCityConstants.CONVERSATION_PLAGUE_HOUSE
import com.open.quester.quest.plaguecity.PlagueCityConstants.ITEM_BOOK
import com.open.quester.quest.plaguecity.PlagueCityConstants.ITEM_CHOCOLATE_DUST
import com.open.quester.quest.plaguecity.PlagueCityConstants.ITEM_DWELLBERRIES
import com.open.quester.quest.plaguecity.PlagueCityConstants.ITEM_GAS_MASK
import com.open.quester.quest.plaguecity.PlagueCityConstants.ITEM_KEY
import com.open.quester.quest.plaguecity.PlagueCityConstants.ITEM_PICTURE_OF_ELENA
import com.open.quester.quest.plaguecity.PlagueCityConstants.ITEM_SNAPE_GRASS
import com.open.quester.quest.plaguecity.PlagueCityConstants.ITEM_WARRANT
import com.open.quester.quest.plaguecity.PlagueCityConstants.NAME_ALRENA
import com.open.quester.quest.plaguecity.PlagueCityConstants.NAME_BRAVEK
import com.open.quester.quest.plaguecity.PlagueCityConstants.NAME_EDMOND
import com.open.quester.quest.plaguecity.PlagueCityConstants.NAME_ELENA
import com.open.quester.quest.plaguecity.PlagueCityConstants.NAME_GRILL
import com.open.quester.quest.plaguecity.PlagueCityConstants.NAME_JETHICK
import com.open.quester.quest.plaguecity.PlagueCityConstants.NAME_MARTHA
import com.open.quester.quest.plaguecity.PlagueCityConstants.NAME_MILLI
import com.open.quester.quest.plaguecity.PlagueCityConstants.NAME_MUDPATCH
import com.open.quester.quest.plaguecity.PlagueCityConstants.NAME_PIPE
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_AlRENA
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_BACK_HOUSE
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_BRAVEK
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_BY_BARREL
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_CLERK
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_EDMOND
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_EDMOND_UNDERGROUND
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_ELENA
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_GRILL
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_JETHICK
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_MARTHA
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_MILLI
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_MUDPATCH
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_NEAR_MANHOLE
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_PLAGUE_DOOR
import com.open.quester.quest.plaguecity.PlagueCityConstants.TILE_PLAGUE_HOUSE
import com.open.quester.quest.plaguecity.PlagueCityConstants.VARP_UNDERGROUND
import com.open.quester.quest.plaguecity.PlagueCityConstants.VARP_VALUE_DUG
import com.open.quester.quest.plaguecity.PlagueCityConstants.VARP_VALUE_PULLED
import com.open.quester.quest.plaguecity.tasks.CreateMedicine

class PlagueCity(information: QuestInformation) : BaseQuest(information) {

    private val spade = ItemRequirementCondition(ITEM_SPADE, false, 1)
    private val dwellBerries = ItemRequirementCondition(ITEM_DWELLBERRIES, false, 1)
    private val rope = ItemRequirementCondition(ITEM_ROPE, false, 1)
    private val milkRequirement = ItemRequirementCondition(ITEM_MILK, false, 1)
    private val chocolateDust = ItemRequirementCondition(ITEM_CHOCOLATE_DUST, false, 1)
    private val snapeGrass = ItemRequirementCondition(ITEM_SNAPE_GRASS, false, 1)
    private val bucketOfWater = ItemRequirementCondition(ITEM_BUCKET_OF_WATER, false, 4)

    val talkToEdmond = SimpleConversationStep(
        NAME_EDMOND, TILE_EDMOND, CONVERSATION_EDMOND_START,
        "Talking to Edmond", information
    )

    private val enterMudHole = SimpleObjectStep(
        Tile(2566, 3331, 0),
        arrayOf(),
        "Dug hole",
        "Climb-down",
        { !AREA_EAST_ARDOUGNE.contains(Players.local()) },
        "Going down under",
        information,
        { AREA_EAST_ARDOUGNE.contains(Players.local()) },
    )

    // East ardy to underground
    private val startQuest = startQuestSteps()
    private val talkToAlrena =
        SimpleConversationStep(NAME_ALRENA, TILE_AlRENA, arrayOf(), "Talking to Alrena", information)
    private val getPicture = getPicture()
    private val makingMud = mudTime()
    private val digHole = digHole()
    private val pullGrill = pullGrill()
    private val openGrill = talkToEdmonToPullGrill()
    private val enterWestArdy = climbUpPipe()
    private val enterHouse = enterHouse()
    private val talkToMartha = talkToFamily()
    private val talkToMilli = SimpleConversationStep(NAME_MILLI, TILE_MILLI, arrayOf(), "Talking to Milli", information)
    private val enterPlagueHouse = enterPlagueHouse()
    private val talkToClerk =
        SimpleNpcStep(
            TILE_CLERK,
            CONVERSATION_CLERK,
            { Npcs.stream().id(4255).first() },
            { npc: Npc -> npc.interact("Talk") },
            { Chat.chatting() },
            "Talking to Clerk",
            questInformation = information
        )
    private val talkToBravek =
        SimpleConversationStep(NAME_BRAVEK, TILE_BRAVEK, CONVERSATION_BRAVEK, "Talking to Bravek", information)
    private val giveMedicine = giveMedicine()
    private val searchPlagueHouse = searchPlagueHouse()
    private val finishQuest = finishQuest()

    override fun addRequirements(): QuestRequirements {
        val itemRequirements = mutableListOf(
            spade, dwellBerries, rope, milkRequirement,
            chocolateDust, snapeGrass, bucketOfWater
        )

        return QuestRequirements(itemRequirements, listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuest.processStep()!!
            1 -> talkToAlrena
            2 -> getPicture.processStep()!!
            3, 4, 5, 6 -> makingMud.processStep()
            7 -> digHole
            8 -> pullGrill.processStep()!!
            9 -> openGrill
            10 -> enterWestArdy.processStep()!!
            20 -> enterHouse
            21 -> talkToMartha
            22 -> talkToMilli
            23 -> enterPlagueHouse
            24 -> talkToClerk
            25 -> talkToBravek
            26 -> giveMedicine.processStep()!!
            27 -> searchPlagueHouse.processStep()!!
            28 -> finishQuest.processStep()!!
            29 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                return null
            }
            else -> TODO("Unable to find step $stepPosition")
        }
    }

    private fun finishQuest(): QuestTaskList {
        val goUnderground = SimpleObjectStep(
            TILE_NEAR_MANHOLE,
            arrayOf(),
            { Objects.nearestGameObject("Manhole") },
            { go: GameObject ->
                val action = if (go.actions().contains("Open")) "Open" else "Climb-down"
                go.interact(action)
            },
            { go: GameObject ->
                if (go.actions().contains("Open"))
                    go.valid()
                else
                    !AREA_WEST_ARDOUGNE.contains(Players.local())
            },
            "Going down under",
            information,
            { AREA_WEST_ARDOUGNE.contains(Players.local()) }
        )
        val goUp = SimpleObjectStep(
            Tile(2517, 9760, 0),
            arrayOf(),
            { Objects.nearestGameObject("Mud pile") },
            { go -> go.interact("Climb") },
            { AREA_EAST_ARDOUGNE.contains(Players.local()) },
            "Going up in the world",
            information,
            { !AREA_WEST_ARDOUGNE.contains(Players.local()) && !AREA_EAST_ARDOUGNE.contains(Players.local()) },
        )

        return QuestTaskList(goUnderground, goUp, talkToEdmond)
    }

    private fun searchPlagueHouse(): QuestTaskList {
        val talkToBravekAgain =
            SimpleConversationStep(
                NAME_BRAVEK, TILE_BRAVEK, CONVERSATION_BRAVEK_AGAIN, "Talking to Bravek again",
                information,
                shouldExecute = { Inventory.stream().name(ITEM_WARRANT).count() == 0L }
            )

        val enterHouse = SimpleObjectStep(
            TILE_PLAGUE_DOOR,
            arrayOf(),
            { Objects.stream().name("Door").within(TILE_PLAGUE_DOOR, 2).nearest().first() },
            { go: GameObject -> go.interact("Open") },
            { Chat.chatting() },
            "Entering plague house",
            information,
            { !AREA_PLAGUE_HOUSE_UPSTAIRS.contains(Players.local()) && !AREA_PLAGUE_HOUSE_DOWNSTAIRS.contains(Players.local()) }
        )

        val getKey = SimpleObjectStep(
            TILE_BY_BARREL,
            arrayOf(),
            { Objects.stream().name("Barrel").action("Search").nearest().first() },
            { go: GameObject -> go.interact("Search") },
            { go -> Conditions.waitUntilItemEntersInventory(ITEM_KEY, 0).invoke() },
            "Retrieving Key",
            information,
            { AREA_PLAGUE_HOUSE_UPSTAIRS.contains(Players.local()) && Inventory.stream().name(ITEM_KEY).count() == 0L },
        )
        val talkToElena = SimpleConversationStep(NAME_ELENA, TILE_ELENA, arrayOf(), "Talking to Elena", information)
        return QuestTaskList(talkToBravekAgain, enterHouse, getKey, talkToElena)
    }

    private fun giveMedicine(): QuestTaskList {
        val medicine = CreateMedicine()
        val talkToBravekAgain =
            SimpleConversationStep(
                NAME_BRAVEK,
                TILE_BRAVEK,
                CONVERSATION_BRAVEK_AGAIN,
                "Talking to Bravek again",
                information
            )
        return QuestTaskList(medicine, talkToBravekAgain)
    }

    private fun enterPlagueHouse(): SimpleObjectStep {
        return SimpleObjectStep(
            TILE_PLAGUE_HOUSE,
            CONVERSATION_PLAGUE_HOUSE,
            { Objects.stream().name("Door").at(TILE_PLAGUE_HOUSE).nearest().first() },
            { go: GameObject -> go.interact("Open") },
            { Chat.chatting() },
            "Entering plague house",
            information
        )
    }

    private fun enterHouse(): SimpleObjectStep {
        return SimpleObjectStep(
            TILE_MARTHA,
            arrayOf(),
            { Objects.stream().name("Door").at(Tile(2531, 3328)).first() },
            { go: GameObject -> go.interact("Open") },
            { Chat.chatting() },
            "Returning book",
            information,
            { Inventory.stream().name(ITEM_BOOK).count() > 0 },
        )
    }

    private fun talkToFamily(): SimpleConversationStep {
        return SimpleConversationStep(NAME_MARTHA, TILE_MARTHA, arrayOf(), "Talking to Martha", information)
    }

    private fun talkToEdmonToPullGrill(): SimpleConversationStep {
        return SimpleConversationStep(
            NAME_EDMOND,
            TILE_EDMOND_UNDERGROUND,
            arrayOf(),
            "Talking to Edmond",
            information
        )
    }

    private fun climbUpPipe(): QuestTaskList {
        val equipMask = EquipItemStep(ITEM_GAS_MASK, "Wear", Equipment.Slot.HEAD)
        val climbPipe = SimpleObjectStep(
            TILE_GRILL,
            arrayOf(),
            { Objects.stream().name(NAME_PIPE).first() },
            { go -> go.interact("Climb-up") },
            { Varpbits.varpbit(getQuestVarpbit()) == 11 },
            "Climbing up the pipe",
            information,
            { AREA_UNDERGROUND.contains(Players.local()) }
        )

        val talkToJethick =
            SimpleConversationStep(NAME_JETHICK, TILE_JETHICK, CONVERSATION_JETHICK, "Talking to Jethick", information)

        return QuestTaskList(equipMask, climbPipe, talkToJethick)
    }

    private fun pullGrill(): QuestTaskList {
        val pullGrill = SimpleObjectStep(
            TILE_GRILL,
            arrayOf(),
            { Objects.stream().name(NAME_GRILL).action(ACTION_GRILL).first() },
            { Varpbits.varpbit(VARP_UNDERGROUND) == VARP_VALUE_DUG },
            { go -> go.interact(ACTION_GRILL) },
            "Pulling the grill",
            information,
            { Varpbits.varpbit(VARP_UNDERGROUND) == VARP_VALUE_PULLED }
        )
        val ropeOnGrill = SimpleObjectStep(
            TILE_GRILL,
            arrayOf(),
            { Objects.stream().name(NAME_GRILL).action(ACTION_GRILL).first() },
            { go: GameObject ->
                val rope = Inventory.stream().name(ITEM_ROPE).first()
                InteractionsHelper.useItemOnInteractive(rope, go)
            },
            { getQuestVarpbit() == 9 },
            "Using rope on grill",
            information,
            { Varpbits.varpbit(VARP_UNDERGROUND, 3, 0x1) == 1 },
        )
        val openGrill = SimpleObjectStep(
            TILE_GRILL,
            arrayOf(),
            { Objects.stream().name(NAME_GRILL).action(ACTION_GRILL).first() },
            { go: GameObject ->
                go.interact("Open")
            },
            { Varpbits.varpbit(VARP_UNDERGROUND) == VARP_VALUE_DUG },
            "Using rope on grill",
            information,

            { Varpbits.varpbit(VARP_UNDERGROUND, 3, 0x1) == 0 },
        )
        return QuestTaskList(enterMudHole, ropeOnGrill, pullGrill, openGrill)
    }

    private fun digHole(): SimpleObjectStep {
        return SimpleObjectStep(
            TILE_BACK_HOUSE,
            arrayOf(),
            { Objects.stream().name(NAME_MUDPATCH).at(TILE_MUDPATCH).first() },
            { go ->
                val bucketOfWater = Inventory.stream().name(ITEM_SPADE).first()
                InteractionsHelper.useItemOnInteractive(
                    bucketOfWater, go
                )
            },
            { Varpbits.varpbit(getQuestVarpbit()) == 8 },
            "Wetting the mud",
            information,
            { Inventory.stream().name(ITEM_SPADE).count() > 0 },
        )
    }

    private fun mudTime(): QuestTaskList {
        return QuestTaskList(GetWaterStep(4), GetWaterStep(3), GetWaterStep(2), GetWaterStep(1))
    }

    private fun GetWaterStep(waterCount: Int): SimpleObjectStep {
        return SimpleObjectStep(
            TILE_BACK_HOUSE,
            arrayOf(),
            { Objects.stream().name(NAME_MUDPATCH).at(TILE_MUDPATCH).first() },
            { go: GameObject ->
                val bucketOfWater = Inventory.stream().name(ITEM_BUCKET_OF_WATER).toList()
                InteractionsHelper.useItemOnInteractive(
                    bucketOfWater.first(), go
                )
            },
            {
                Inventory.stream().name(ITEM_BUCKET_OF_WATER).count().toInt() == waterCount - 1
            },
            "Wetting the mud",
            information,
            { Inventory.stream().name(ITEM_BUCKET_OF_WATER).count().toInt() == waterCount }
        )
    }

    private fun startQuestSteps(): QuestTaskList {
        val bank = BankStep(
            listOf(bucketOfWater, snapeGrass, chocolateDust, milkRequirement, dwellBerries, rope, spade),
            BANK_NORTH_ARDOUGNE, information
        )
        return QuestTaskList(
            bank,
            talkToEdmond
        )
    }

    private fun getPicture(): QuestTaskList {
        val pickupPicture = PickupItemStep(
            TILE_AlRENA, {
                GroundItems.stream().name(ITEM_PICTURE_OF_ELENA).nearest().first()
            }, { Inventory.stream().name(ITEM_PICTURE_OF_ELENA).count().toInt() == 0 },
            "Take", "Picking up picture, nothing sus."
        )

        return QuestTaskList(pickupPicture, talkToEdmond)
    }
}
