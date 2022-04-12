package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Item

class DropFoodIfNeeded(val questInformation: QuestInformation, val condition: () -> Boolean) : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return condition.invoke()
    }

    override fun run() {
        val food = Inventory.stream().name(*questInformation.foodName).first()
        if (food != Item.Nil && food.interact("Drop")) {
            Condition.wait{!food.valid()}
        }
    }

    override fun stepName(): String {
        return "Dropping food"
    }
}