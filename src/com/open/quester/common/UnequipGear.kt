package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.rt4.Equipment
import org.powbot.api.rt4.Game

class UnequipGear(val gearToUnequip: String, val slot: Equipment.Slot, val information: QuestInformation) :
    BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return Equipment.itemAt(slot).name() == gearToUnequip
    }

    override fun run() {
        if (!Game.tab(Game.Tab.EQUIPMENT)) {
            return
        }
        if (Equipment.itemAt(slot).click("Remove")) {
            Condition.wait { Equipment.itemAt(slot) == Equipment.nil() }
        }
    }

    override fun stepName(): String {
        return "Equipping gear"
    }
}