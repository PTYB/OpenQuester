package com.open.quester.quest.therestlessghost.requirements

import com.open.quester.common.base.BaseQuestStep
import org.powbot.api.Condition
import org.powbot.api.rt4.Equipment
import org.powbot.api.rt4.Equipment.Slot
import org.powbot.api.rt4.Game
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Item
import com.open.quester.quest.therestlessghost.TheRestlessGhostConstants.ITEM_GHOSTSPEAK

class EquipAmulet : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        val item: Item = Equipment.itemAt(Slot.NECK)
        val ghostspeakInvo: Item = Inventory.stream().name(ITEM_GHOSTSPEAK).first()
        val hasGhostSpeakOn = item !== Item.Nil && item.name() == ITEM_GHOSTSPEAK
        val hasGhostSpeakInventory = ghostspeakInvo != Item.Nil && ghostspeakInvo.valid()
        return !hasGhostSpeakOn && hasGhostSpeakInventory
    }

    override fun run() {
        if (!Game.tab(Game.Tab.INVENTORY)) {
            return
        }
        val amulet = getGhostspeakAmulet()
        if (amulet != Item.Nil && amulet.interact("Wear")) {
            Condition.wait {
                val ammy = getGhostspeakAmulet()
                ammy == Item.Nil
            }
        }
    }

    private fun getGhostspeakAmulet(): Item {
        return Inventory.stream().name(ITEM_GHOSTSPEAK).first()
    }

    override fun stepName(): String {
        return "Equipping Amulet"
    }
}