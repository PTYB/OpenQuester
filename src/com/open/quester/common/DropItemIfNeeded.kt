package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.count
import org.powbot.api.Condition
import org.powbot.api.rt4.Inventory

class DropItemsIfNeeded(vararg items: String,val conditions: () -> Boolean ) : BaseQuestStep() {
    private val items = items.toList().toTypedArray()

    override fun shouldExecute(): Boolean {
        return conditions.invoke() && Inventory.count(*items) > 0
    }

    override fun run() {
        val items = Inventory.stream().name(*items).toList()
        items.forEach {
            if (it.interact("drop")) {
                Condition.wait{!it.valid()}
            }
        }
    }

    override fun stepName(): String {
        return "Dropping $items"
    }
}