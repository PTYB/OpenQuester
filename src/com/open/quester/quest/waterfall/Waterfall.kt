package com.open.quester.quest.waterfall

import com.open.quester.common.*
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleNpcStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.Conditions
import com.open.quester.extensions.count
import com.open.quester.extensions.nearestGameObject
import com.open.quester.extensions.nearestNpc
import com.open.quester.helpers.CombatHelper
import com.open.quester.helpers.InteractionsHelper
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_NORTH_ARDOUGNE
import com.open.quester.quest.Constants.ITEM_ROPE
import com.open.quester.quest.Constants.ItemRequirements.ITEM_RUNE_AIR
import com.open.quester.quest.Constants.ItemRequirements.ITEM_RUNE_EARTH
import com.open.quester.quest.Constants.ItemRequirements.ITEM_RUNE_WATER
import com.open.quester.quest.waterfall.WaterfallConstants.AREA_DEAD_TREE_ISLAND
import com.open.quester.quest.waterfall.WaterfallConstants.AREA_GNOME_BASEMENT
import com.open.quester.quest.waterfall.WaterfallConstants.AREA_HUDON_ISLAND
import com.open.quester.quest.waterfall.WaterfallConstants.AREA_INSIDE_MAZE
import com.open.quester.quest.waterfall.WaterfallConstants.AREA_INSIDE_TOMB
import com.open.quester.quest.waterfall.WaterfallConstants.AREA_INSIDE_WATERFALL
import com.open.quester.quest.waterfall.WaterfallConstants.AREA_LEDGE
import com.open.quester.quest.waterfall.WaterfallConstants.CONDITION_HAS_AMULET
import com.open.quester.quest.waterfall.WaterfallConstants.CONDITION_HAS_BAX_KEY
import com.open.quester.quest.waterfall.WaterfallConstants.CONDITION_HAS_URN
import com.open.quester.quest.waterfall.WaterfallConstants.CONDITION_NO_AMULET
import com.open.quester.quest.waterfall.WaterfallConstants.CONDITION_NO_BAX_KEY
import com.open.quester.quest.waterfall.WaterfallConstants.CONDITION_NO_BOOK
import com.open.quester.quest.waterfall.WaterfallConstants.CONDITION_NO_URN
import com.open.quester.quest.waterfall.WaterfallConstants.CONDITION_ON_LEDGE
import com.open.quester.quest.waterfall.WaterfallConstants.CONVERSATION_ALMERA
import com.open.quester.quest.waterfall.WaterfallConstants.FINAL_ROOM
import com.open.quester.quest.waterfall.WaterfallConstants.ID_BAX_KEY
import com.open.quester.quest.waterfall.WaterfallConstants.ID_BOOKCASE
import com.open.quester.quest.waterfall.WaterfallConstants.ID_CHEST
import com.open.quester.quest.waterfall.WaterfallConstants.ID_CRATE
import com.open.quester.quest.waterfall.WaterfallConstants.ID_CRATE2
import com.open.quester.quest.waterfall.WaterfallConstants.ID_KEY
import com.open.quester.quest.waterfall.WaterfallConstants.ITEM_AMULET
import com.open.quester.quest.waterfall.WaterfallConstants.ITEM_BOOK
import com.open.quester.quest.waterfall.WaterfallConstants.ITEM_PEBBLE
import com.open.quester.quest.waterfall.WaterfallConstants.ITEM_URN
import com.open.quester.quest.waterfall.WaterfallConstants.NAME_ALMERA
import com.open.quester.quest.waterfall.WaterfallConstants.NAME_BARREL
import com.open.quester.quest.waterfall.WaterfallConstants.NAME_COFFIN
import com.open.quester.quest.waterfall.WaterfallConstants.NAME_ELKOY
import com.open.quester.quest.waterfall.WaterfallConstants.NAME_GLORIE
import com.open.quester.quest.waterfall.WaterfallConstants.NAME_HUDON
import com.open.quester.quest.waterfall.WaterfallConstants.NAME_ROCK
import com.open.quester.quest.waterfall.WaterfallConstants.NAME_STATUE
import com.open.quester.quest.waterfall.WaterfallConstants.NAME_TREE
import com.open.quester.quest.waterfall.WaterfallConstants.TILE_ALMERA
import com.open.quester.quest.waterfall.WaterfallConstants.TILE_BOOKCASE
import com.open.quester.quest.waterfall.WaterfallConstants.TILE_CHEST
import com.open.quester.quest.waterfall.WaterfallConstants.TILE_COFFIN
import com.open.quester.quest.waterfall.WaterfallConstants.TILE_CRATE
import com.open.quester.quest.waterfall.WaterfallConstants.TILE_ELKOY
import com.open.quester.quest.waterfall.WaterfallConstants.TILE_GLORIE
import com.open.quester.quest.waterfall.WaterfallConstants.TILE_HUDON
import com.open.quester.quest.waterfall.WaterfallConstants.TILE_OUTSIDE_TOMB
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.mobile.script.ScriptManager
import com.open.quester.quest.waterfall.tasks.BoardRaft
import com.open.quester.quest.waterfall.tasks.CloseBook
import com.open.quester.quest.waterfall.tasks.ReadBook
import org.powbot.api.Point

class Waterfall(information: QuestInformation) : BaseQuest(information) {
    private val airRuneRequirement = ItemRequirementCondition(ITEM_RUNE_AIR, false, 6)
    private val waterRuneRequirement = ItemRequirementCondition(ITEM_RUNE_WATER, false, 6)
    private val earthRuneRequirement = ItemRequirementCondition(ITEM_RUNE_EARTH, false, 6)
    private val ropeRequirement = ItemRequirementCondition(ITEM_ROPE, false, 1)
    private val amuletRequirement = ItemRequirementCondition(ITEM_AMULET, false, 1)
    private val urnRequirrement = ItemRequirementCondition(ITEM_URN, false, 1)
    private val pebbleRequirrement = ItemRequirementCondition(ITEM_PEBBLE, false, 1)

    private val pillarTiles = arrayOf(
        Tile(2562, 9914), Tile(2562, 9912), Tile(2562, 9910), // Left Side
        Tile(2569, 9914), Tile(2569, 9912), Tile(2569, 9910), // Right
    )

    private val useRopeOnRock = useRopeOnRock()
    private val useRopeOnTree = useRopeOnTree()

    private val startQuest = startQuest()
    private val talkToHudon = talkToHudon()
    private val readBook = readBook()
    private val getPebble = getGlarialStuff()
    private val enterRoom = enterRoom()
    private val useRunes = useAmuletOnStatue()
    private val finishQuest = useUrn()

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(
            listOf(
                airRuneRequirement,
                waterRuneRequirement,
                earthRuneRequirement,
                ropeRequirement
            ), listOf()
        )
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (stepPosition) {
            0 -> startQuest.processStep()
            1 -> talkToHudon.processStep()
            2 -> readBook.processStep()
            3, 4 -> getPebble.processStep()
            5 -> enterRoom.processStep()
            6 -> useRunes.processStep()
            8 -> finishQuest
            10 -> {
                information.complete = true
                CommonMethods.closeQuestComplete()
                return null
            }
            else -> TODO("Unable to find step $stepPosition")
        }
    }

    private fun useUrn(): SimpleObjectStep {
        return SimpleObjectStep(
            Tile(2603, 9912),
            arrayOf(),
            { Objects.nearestGameObject("Chalice") },
            { go: GameObject ->
                InteractionsHelper.useItemOnInteractive(
                    Inventory.stream().name(ITEM_URN).first(),
                    go,
                )
            },
            { Chat.chatting() },
            "Using urn",
            information,
            CONDITION_HAS_URN,
        )
    }

    private fun getPillarStep(
        pillarTile: Tile,
        runeName: String,
        airCount: Int,
        waterCount: Int,
        earthCount: Int
    ): SimpleObjectStep {
        return SimpleObjectStep(
            if (airCount < 4) pillarTile.derive(1, 0) else pillarTile.derive(-1, 0),
            null,
            { Objects.stream().name("Pillar").at(pillarTile).first() },
            { go: GameObject ->
                InteractionsHelper.useItemOnInteractive(
                    Inventory.stream().name(runeName).first(),
                    go,
                )
            },
            {
                val runeCount = when (runeName) {
                    ITEM_RUNE_AIR -> airCount
                    ITEM_RUNE_WATER -> waterCount
                    ITEM_RUNE_EARTH -> earthCount
                    else -> TODO()
                }
                Inventory.count(runeName) != runeCount
            },
            "Using $runeName on Pillar",
            information,
            {
                Inventory.count(ITEM_RUNE_AIR) == airCount &&
                        Inventory.count(ITEM_RUNE_WATER) == waterCount &&
                        Inventory.count(ITEM_RUNE_EARTH) == earthCount
            },
        )
    }

    private fun useAmuletOnStatue(): QuestTaskList {
        val step = SimpleObjectStep(
            Tile(2565, 9915, 0),
            arrayOf(),
            { Objects.nearestGameObject(NAME_STATUE) },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_AMULET, go) },
            { Inventory.count(ITEM_AMULET) == 0 },
            "Using amulet on statue",
            information,
            { Inventory.stream().name(ITEM_AMULET).count() > 0 },
        )
        val steps = mutableListOf<SimpleObjectStep>()
        for (i in 0 until 6) {
            steps.add(getPillarStep(pillarTiles[i], ITEM_RUNE_AIR, 6 - i, 6 - i, 6 - i))
            steps.add(getPillarStep(pillarTiles[i], ITEM_RUNE_WATER, 5 - i, 6 - i, 6 - i))
            steps.add(getPillarStep(pillarTiles[i], ITEM_RUNE_EARTH, 5 - i, 5 - i, 6 - i))
        }
        return QuestTaskList(
            *steps.toTypedArray(),
            UnequipGear(ITEM_AMULET, Equipment.Slot.NECK, information),
            step
        )
    }

    private fun enterRoom(): QuestTaskList {
        val getFirstCrate = SimpleObjectStep(
            Tile(2589, 9888), // Crates key
            arrayOf(),
            { Objects.nearestGameObject(ID_CRATE2) },
            { go: GameObject -> go.interact("Search") },
            {
                Conditions.waitUntilItemEntersInventory(ID_BAX_KEY, 0).invoke() ||
                        CombatHelper.shouldEat(*information.foodName)
            },
            "Getting Key",
            information,
            CONDITION_NO_BAX_KEY,
        )
        val walkToRoom = WalkToArea(
            FINAL_ROOM,
            FINAL_ROOM.randomTile,
            "Walking to final room",
            { !FINAL_ROOM.contains(Players.local()) && CONDITION_HAS_BAX_KEY.invoke() },
            information,
        )
        return QuestTaskList(
            getFirstCrate,
            walkToRoom
        )
    }

    private fun canUseElkoy(): Boolean {
        return Varpbits.varpbit(com.open.quester.Varpbits.TREE_GNOME_VILLAGE.questVarbit) >= 6
    }

    private fun hasPebble(): Boolean {
        return Inventory.count(ITEM_PEBBLE) > 0
    }

    private fun hasAmuletAndUrn(): Boolean {
        val count = Inventory.stream().name(ITEM_URN, ITEM_AMULET).count()
        if (count == 2L) {
            return true
        } else if (count == 0L) {
            return false
        }

        return Equipment.itemAt(Equipment.Slot.NECK).name() == ITEM_AMULET
    }

    private fun getGlarialStuff(): QuestTaskList {
        return QuestTaskList(
            *getPebbelSteps(),
            *exitMaze(),
            *getAmuletAndUrn(),
            *enterTomb(),
        )
    }

    private fun enterTomb(): Array<BaseQuestStep> {
        val enterTomb = SimpleObjectStep(
            Tile.Nil,
            arrayOf(),
            { Objects.nearestGameObject("Door") },
            { go: GameObject -> go.interact("Open") },
            { AREA_INSIDE_WATERFALL.contains(Players.local()) },
            "Entering waterfall",
            information,
            CONDITION_ON_LEDGE,
        )
        return arrayOf(
            BankStep(
                listOf(
                    airRuneRequirement, waterRuneRequirement, earthRuneRequirement, ropeRequirement,
                    amuletRequirement, urnRequirrement, pebbleRequirrement,
                    ItemRequirementCondition.emptySlots(1)
                ),
                BANK_NORTH_ARDOUGNE,
                information,
                {
                    Inventory.stream().name(ITEM_RUNE_AIR).count(true) != 6L ||
                            Inventory.stream().name(ITEM_RUNE_WATER).count(true) != 6L ||
                            Inventory.stream().name(ITEM_RUNE_EARTH).count(true) != 6L
                },
                false,
                true
            ),
            EquipItemStep(ITEM_AMULET, "Wear", Equipment.Slot.NECK),
            enterTomb,
            useRopeOnRock,
            useRopeOnTree,
            BoardRaft({ hasAmuletAndUrn() }, information)
        )
    }

    private fun getAmuletAndUrn(): Array<BaseQuestStep> {
        return arrayOf(
            SimpleObjectStep(
                TILE_COFFIN,
                arrayOf(),
                { Objects.nearestGameObject(NAME_COFFIN) },
                { go: GameObject -> go.interact("Search") },
                { CONDITION_HAS_URN.invoke() },
                "Getting Urn",
                information,
                { AREA_INSIDE_TOMB.contains(Players.local()) && CONDITION_NO_URN.invoke() },
            ),
            SimpleObjectStep(
                TILE_CHEST,
                arrayOf(),
                { Objects.nearestGameObject(ID_CHEST) },
                { go: GameObject -> go.interact("Open") },
                { CONDITION_HAS_AMULET.invoke() },
                "Getting Amulet",
                information,
                { AREA_INSIDE_TOMB.contains(Players.local()) && CONDITION_NO_AMULET.invoke() },
            )
        )
    }

    private fun exitMaze(): Array<BaseQuestStep> {
        val enterTomb = SimpleObjectStep(
            TILE_OUTSIDE_TOMB,
            arrayOf(),
            { Objects.nearestGameObject("Glarial's Tombstone") },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_PEBBLE, go) },
            { AREA_INSIDE_TOMB.contains(Players.local()) },
            "Entering Tomb",
            information,
            { hasPebble() && !hasAmuletAndUrn() && !AREA_INSIDE_TOMB.contains(Players.local()) },
        )
        return arrayOf(
            enterTomb
        )
    }

    private fun getPebbelSteps(): Array<BaseQuestStep> {
        val talkToElkoyToEnter = SimpleNpcStep(
            TILE_ELKOY,
            arrayOf(),
            { Npcs.nearestNpc(NAME_ELKOY) },
            { npc -> npc.interact("Follow") },
            { AREA_INSIDE_MAZE.contains(Players.local()) },
            "Talking to Elkoy",
            shouldExecute = {
                !hasPebble() && canUseElkoy() && !AREA_INSIDE_MAZE.contains(Players.local()) &&
                        !AREA_GNOME_BASEMENT.contains(Players.local())
            },
            questInformation = information
        )

        val getKey = SimpleObjectStep(
            TILE_CRATE,
            arrayOf(),
            { Objects.nearestGameObject(ID_CRATE) },
            { go: GameObject -> go.interact("Search") },
            { Inventory.count("Key") == 1 || CombatHelper.shouldEat(*information.foodName) },
            "Getting Key",
            information,
            shouldExecute = { !hasPebble() && Inventory.stream().id(ID_KEY, ID_BAX_KEY).first() == Item.Nil },
        )

        val talkToGlorie = SimpleConversationStep(
            NAME_GLORIE,
            TILE_GLORIE,
            arrayOf(),
            "Talking to Glorie",
            information,
            shouldExecute = { !hasPebble() && Inventory.stream().id(ID_KEY).count() > 0 },
        )

        return arrayOf(
            CloseBook(),
            talkToElkoyToEnter,
            getKey,
            talkToGlorie
        )
    }

    private fun readBook(): QuestTaskList {
        val getBook = SimpleObjectStep(
            TILE_BOOKCASE,
            arrayOf(),
            { Objects.nearestGameObject(ID_BOOKCASE) },
            { go -> go.interact("Search") },
            { Inventory.count(ITEM_BOOK) == 1 },
            "Getting book",
            information,
            CONDITION_NO_BOOK,
        )
        return QuestTaskList(
            useRopeOnRock,
            useRopeOnTree,
            enterBarrel(),
            getBook,
            ReadBook(),
        )
    }

    private fun talkToHudon(): QuestTaskList {
        return QuestTaskList(
            BoardRaft({ !AREA_HUDON_ISLAND.contains(Players.local()) }, information),
            SimpleConversationStep(NAME_HUDON, TILE_HUDON, arrayOf(), "Talking To Hudon", information)
        )
    }

    private fun startQuest(): QuestTaskList {
        return QuestTaskList(
            BankStep(
                listOf(ropeRequirement, ItemRequirementCondition.emptySlots(4)),
                BANK_NORTH_ARDOUGNE, information, { true }, false, true
            ),
            SimpleConversationStep(NAME_ALMERA, TILE_ALMERA, CONVERSATION_ALMERA, "Talking to Almera", information)
        )
    }

    private fun useRopeOnRock(): SimpleObjectStep {
        val step = SimpleObjectStep(
            Tile(2512, 3476, 0),
            arrayOf(),
            { Objects.nearestGameObject(NAME_ROCK) },

            { go ->
                val item = Inventory.stream().name(ITEM_ROPE).first()
                InteractionsHelper.useItemOnInteractive(item, go)
            },
            { AREA_DEAD_TREE_ISLAND.contains(Players.local()) },
            "Using rope on island",
            information,
            shouldExecute = { AREA_HUDON_ISLAND.contains(Players.local()) },
        )
        step.pointVariance = Point(0, 10)
        return step
    }

    private fun useRopeOnTree(): SimpleObjectStep {
        val step = SimpleObjectStep(
            Tile.Nil,
            arrayOf(),
            { Objects.nearestGameObject(NAME_TREE) },
            { go: GameObject -> InteractionsHelper.useItemOnInteractive(ITEM_ROPE, go) },
            { AREA_LEDGE.contains(Players.local()) },
            "Using rope on tree",
            information,
            { AREA_DEAD_TREE_ISLAND.contains(Players.local()) },
        )
        step.pointVariance = Point(0, 1)
        return step
    }

    private fun enterBarrel(): SimpleObjectStep {
        return SimpleObjectStep(
            Tile.Nil,
            arrayOf(),
            { Objects.nearestGameObject(NAME_BARREL) },
            { go: GameObject -> go.interact("Get in") },
            { !AREA_LEDGE.contains(Players.local()) },
            "Entering barrel",
            information,
            CONDITION_ON_LEDGE,
        )
    }
}