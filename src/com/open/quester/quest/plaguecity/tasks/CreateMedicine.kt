package com.open.quester.quest.plaguecity.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.Conditions
import com.open.quester.helpers.InteractionsHelper
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Item
import com.open.quester.quest.plaguecity.PlagueCityConstants.ITEM_BUCKET_OF_MILK
import com.open.quester.quest.plaguecity.PlagueCityConstants.ITEM_CHOCOLATE_DUST
import com.open.quester.quest.plaguecity.PlagueCityConstants.ITEM_CHOCOLATE_MILK
import com.open.quester.quest.plaguecity.PlagueCityConstants.ITEM_HANGOVER_CURE
import com.open.quester.quest.plaguecity.PlagueCityConstants.ITEM_SNAPE_GRASS
import org.powbot.api.Condition

class CreateMedicine : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return Inventory.stream().name(ITEM_HANGOVER_CURE).first() == Item.Nil
    }

    override fun run() {

        val bucketOfChocolateMilk = Inventory.stream().name(ITEM_CHOCOLATE_MILK).first()
        if (bucketOfChocolateMilk == Item.Nil) {
            val chocolateDust = Inventory.stream().name(ITEM_CHOCOLATE_DUST).first()
            val bucketOfMilk = Inventory.stream().name(ITEM_BUCKET_OF_MILK).first()

            if (InteractionsHelper.useItemOn(chocolateDust, bucketOfMilk)) {
                Condition.wait(Conditions.waitUntilItemLeavesInventory(ITEM_CHOCOLATE_DUST, 1))
            }
        } else {
            val snapeGrass = Inventory.stream().name(ITEM_SNAPE_GRASS).first()

            if (InteractionsHelper.useItemOn(bucketOfChocolateMilk, snapeGrass)) {
                Condition.wait(Conditions.waitUntilItemEntersInventory(ITEM_HANGOVER_CURE, 0))
            }
        }
    }

    override fun stepName(): String {
        return "Create hangover cure"
    }
}