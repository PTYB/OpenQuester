package com.open.quester.quest.demonslayer

import com.open.quester.common.*
import com.open.quester.common.CommonMethods.closeQuestComplete
import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.common.base.SimpleObjectStep
import com.open.quester.extensions.count
import com.open.quester.extensions.nearestGameObject
import com.open.quester.helpers.InteractionsHelper
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestInformation
import com.open.quester.models.QuestRequirements
import com.open.quester.quest.Constants.BANK_VARROCK_EAST
import com.open.quester.quest.Constants.BANK_VARROCK_WEST_SOUTH_SIDE
import com.open.quester.quest.Constants.ITEM_BUCKET
import com.open.quester.quest.demonslayer.DemonSlayerConstants.CONVERSATION_GYPSY
import com.open.quester.quest.demonslayer.DemonSlayerConstants.CONVERSATION_PRYSIN
import com.open.quester.quest.demonslayer.DemonSlayerConstants.CONVERSATION_ROVIN
import com.open.quester.quest.demonslayer.DemonSlayerConstants.CONVERSATION_TRAIBORN
import com.open.quester.quest.demonslayer.DemonSlayerConstants.ID_FIRST_KEY
import com.open.quester.quest.demonslayer.DemonSlayerConstants.ID_SECOND_KEY
import com.open.quester.quest.demonslayer.DemonSlayerConstants.ID_THIRD_KEY
import com.open.quester.quest.demonslayer.DemonSlayerConstants.ITEM_BONES
import com.open.quester.quest.demonslayer.DemonSlayerConstants.ITEM_BUCKET_OF_WATER
import com.open.quester.quest.demonslayer.DemonSlayerConstants.ITEM_SILVERLIGHT
import com.open.quester.quest.demonslayer.DemonSlayerConstants.NAME_GYPSY
import com.open.quester.quest.demonslayer.DemonSlayerConstants.NAME_PRYSIN
import com.open.quester.quest.demonslayer.DemonSlayerConstants.NAME_ROVIN
import com.open.quester.quest.demonslayer.DemonSlayerConstants.NAME_TRAIBORN
import com.open.quester.quest.demonslayer.DemonSlayerConstants.TILE_BUCKET
import com.open.quester.quest.demonslayer.DemonSlayerConstants.TILE_DRAIN
import com.open.quester.quest.demonslayer.DemonSlayerConstants.TILE_GYPSY
import com.open.quester.quest.demonslayer.DemonSlayerConstants.TILE_PRYSIN
import com.open.quester.quest.demonslayer.DemonSlayerConstants.TILE_ROVIN
import com.open.quester.quest.demonslayer.DemonSlayerConstants.TILE_SECOND_KEY
import com.open.quester.quest.demonslayer.DemonSlayerConstants.TILE_SINK
import com.open.quester.quest.demonslayer.DemonSlayerConstants.TILE_TRAIBORN
import com.open.quester.quest.demonslayer.tasks.KillDelrith
import org.powbot.api.rt4.*
import org.powbot.mobile.script.ScriptManager

class DemonSlayer(information: QuestInformation) : BaseQuest(information) {

    private val bonesRequirement = ItemRequirementCondition(ITEM_BONES, false, 25)
    private val coinsRequirement = ItemRequirementCondition("Coins", true, 1)
    private val hasFirstTwoKeys = ItemRequirementCondition("Silverlight key", true, 2)

    private val startQuest = startQuest()
    private val talkToPrysin =
        SimpleConversationStep(NAME_PRYSIN, TILE_PRYSIN, CONVERSATION_PRYSIN, "Talking to Prysin", information)
    private val getKeys = getKeys()

    override fun addRequirements(): QuestRequirements {
        return QuestRequirements(listOf(bonesRequirement, coinsRequirement), listOf())
    }

    override fun getQuestStep(stepPosition: Int): BaseQuestStep? {
        return when (val actualPosition = stepPosition and 0x3) {
            0 -> startQuest.processStep()
            1 -> talkToPrysin
            2 -> getKeys.processStep()
            3 -> {
                information.complete = true
                closeQuestComplete()
                null
            }
            else -> {
                logger.info("Missing step $actualPosition")
                ScriptManager.stop()
                null
            }
        }
    }

    private fun killDelrith(): Array<BaseQuestStep> {
        val equipSilverlight = EquipItemStep(ITEM_SILVERLIGHT, "Wield", Equipment.Slot.MAIN_HAND)
        val prepareForDelrith = BankStep(
            listOf(),
            BANK_VARROCK_WEST_SOUTH_SIDE,
            information,
            { Equipment.itemAt(Equipment.Slot.MAIN_HAND).name() == ITEM_SILVERLIGHT },
            true,
            true
        )

        return arrayOf(
            equipSilverlight,
            prepareForDelrith,
            KillDelrith(information),
        )
    }

    private fun getKeys(): QuestTaskList {
        val getSilverlight = SimpleConversationStep(
            NAME_PRYSIN, TILE_PRYSIN, arrayOf(), "Getting silverlight", information,
            shouldExecute = { Inventory.count(ID_FIRST_KEY, ID_SECOND_KEY, ID_THIRD_KEY) == 3 && !hasSilverlight() }
        )

        return QuestTaskList(
            *killDelrith(),
            getSilverlight,
            *getFirstKey(),
            *getSecondKey(),
            *getThirdKey(),
        )
    }

    private fun getThirdKey(): Array<BaseQuestStep> {
        val thirdKeyBones = BankStep(
            listOf(bonesRequirement, hasFirstTwoKeys),
            BANK_VARROCK_EAST,
            information,
            { !hasThirdKey() && !hasSilverlight() },
        )

        val talkToTraiborn = SimpleConversationStep(
            NAME_TRAIBORN, TILE_TRAIBORN, CONVERSATION_TRAIBORN,
            "Talking to Traiborn", information,
            shouldExecute = { !hasThirdKey() && !hasThirdKey() && Inventory.count(ITEM_BONES) == 25 },
        )

        return arrayOf(
            talkToTraiborn,
            thirdKeyBones,
        )
    }

    private fun getSecondKey(): Array<BaseQuestStep> {
        val pickupBucket = PickupItemStep(
            TILE_BUCKET, {
                GroundItems.stream().at(TILE_BUCKET).name(ITEM_BUCKET).first()
            },
            {
                !hasSilverlight() && !flushedWater() && !hasSecondKey() && Inventory.stream()
                    .name(ITEM_BUCKET, ITEM_BUCKET_OF_WATER).count() == 0L
            },
            "Take", "Picking up bucket", information
        )

        val fillBucketOfWater = SimpleObjectStep(
            TILE_SINK,
            arrayOf(),
            { Objects.nearestGameObject("Sink") },
            { go: GameObject ->
                InteractionsHelper.useItemOnInteractive(ITEM_BUCKET, go)
            },
            { Inventory.count(ITEM_BUCKET_OF_WATER) == 1 },
            "Filling bucket",
            information,
            shouldExecute = {
                !hasSilverlight() && !flushedWater() && !hasSecondKey() && Inventory.count(ITEM_BUCKET_OF_WATER) == 0 &&
                        Inventory.count(ITEM_BUCKET) == 1
            },
        )

        val useBucketOfWater = SimpleObjectStep(
            TILE_DRAIN,
            arrayOf(),
            { Objects.stream().name("Drain").at(TILE_DRAIN).first() },
            { go: GameObject ->
                InteractionsHelper.useItemOnInteractive(
                    ITEM_BUCKET_OF_WATER,
                    go
                )
            },
            { Inventory.count(ITEM_BUCKET_OF_WATER) == 0 },
            "Flushing the key",
            information,
            shouldExecute = {
                !hasSilverlight() && !flushedWater() && !hasSecondKey() && Inventory.count(
                    ITEM_BUCKET_OF_WATER
                ) == 1
            },
        )
        val dropBucket = DropItemsIfNeeded(
            ITEM_BUCKET, conditions = { flushedWater() || hasSecondKey() }
        )

        val pickupKey = SimpleObjectStep(
            TILE_SECOND_KEY,
            arrayOf(),
            { Objects.nearestGameObject(17429) },
            { go: GameObject -> go.interact("Take") },
            { Inventory.count(ID_SECOND_KEY) == 1 },
            "Picking 2nd key",
            information,
            { flushedWater() && !hasSecondKey() && !hasSilverlight() },
        )

        return arrayOf(
            pickupKey,
            dropBucket,
            useBucketOfWater,
            fillBucketOfWater,
            pickupBucket,
        )
    }

    private fun flushedWater(): Boolean {
        return Varpbits.varpbit(getQuestVarpbit(), 21, 0x1) == 1
    }

    private fun getFirstKey(): Array<BaseQuestStep> {
        val talkToRovin = SimpleConversationStep(
            NAME_ROVIN, TILE_ROVIN, CONVERSATION_ROVIN, "Talking to Rovin", information,
            shouldExecute = { !hasSilverlight() && !hasFirstKey() }
        )

        return arrayOf(
            talkToRovin
        )
    }

    private fun hasThirdKey(): Boolean {
        return Inventory.count(ID_THIRD_KEY) == 1
    }

    private fun hasSecondKey(): Boolean {
        return Inventory.count(ID_SECOND_KEY) == 1
    }

    private fun hasFirstKey(): Boolean {
        return Inventory.count(ID_FIRST_KEY) == 1
    }

    private fun hasSilverlight(): Boolean {
        return Inventory.stream().name(ITEM_SILVERLIGHT).count() == 1L || Equipment.itemAt(Equipment.Slot.MAIN_HAND)
            .name() == ITEM_SILVERLIGHT
    }

    private fun startQuest(): QuestTaskList {
        val bankStep = BankStep(listOf(coinsRequirement), BANK_VARROCK_WEST_SOUTH_SIDE, information, { true })
        val talkToAris = SimpleConversationStep(
            NAME_GYPSY, TILE_GYPSY, CONVERSATION_GYPSY, "Talking to Gypsy",
            information
        )

        return QuestTaskList(
            bankStep,
            talkToAris
        )
    }

}