package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import org.powbot.api.Condition
import org.powbot.api.rt4.*

class EquipItemStep(
    val itemName: String,
    private val equipAction: String,
    val slot: Equipment.Slot,
    private val showEquipment: Boolean = false
) : BaseQuestStep() {

    override fun shouldExecute(): Boolean {
        val item: Item = Equipment.itemAt(slot)
        val itemInInventory: Item = Inventory.stream().name(itemName).first()
        val itemEquipped = item !== Item.Nil && item.name() == itemName
        val itemInventory = itemInInventory != Item.Nil && itemInInventory.valid()
        return !itemEquipped && itemInventory
    }

    override fun run() {
        if (Store.opened()) {
            Store.close()
        }
        logger.info("Equipping Item $itemName")

        val item = getItem()
        if (item != Item.Nil && item.interact(equipAction)) {

            if (showEquipment) {
                Game.tab(Game.Tab.EQUIPMENT)
            }
            Condition.wait { Equipment.itemAt(slot).name() == itemName }
        }
    }

    private fun getItem(): Item {
        return Inventory.stream().name(itemName).first()
    }

    override fun stepName(): String {
        return "Equipping $itemName"
    }
}