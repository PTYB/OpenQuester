package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import org.powbot.api.Condition
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Item

class InteractWithItem(
    val itemName: String,
    val action: String,
    val shouldExecute: () -> Boolean,
    val validated: () -> Boolean
) : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return shouldExecute.invoke()
    }

    override fun run() {
        val item = Inventory.stream().name(itemName).first()
        if (item != Item.Nil && item.interact(action)) {
            Condition.wait(validated)
        }
    }

    override fun stepName(): String {
        return "Interacting with $itemName"
    }
}