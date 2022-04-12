package com.open.quester.quest.priestinperil.steps.temple

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.count
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.quest.Constants.BANK_VARROCK_EAST
import org.powbot.api.Notifications
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Varpbits
import org.powbot.mobile.script.ScriptManager
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants
import org.powbot.quests.quest.priestinperil.PriestInPerilConstants.ITEM_RUNE_ESSENCE

class BankForEssence(essenceCondition: ItemRequirementCondition) : BaseQuestStep() {
    private val _bankItemName by lazy {
        if (essenceCondition.chosenRequirement != null) {
            essenceCondition.chosenRequirement!!.name
        } else {
            Bank.stream().name(essenceCondition.itemRequirements[0].name, essenceCondition.itemRequirements[1].name)
                .filtered { it.stack > 50 }.first().name()
        }
    }

    override fun shouldExecute(): Boolean {
        if (Bank.opened()) {
            return true
        }
        val essenceCount = Inventory.count(ITEM_RUNE_ESSENCE, PriestInPerilConstants.ITEM_PURE_ESSENCE)
        val essenceRequired = 59 - Varpbits.varpbit(302)
        logger.info("Required $essenceRequired, count $essenceCount")
        return essenceRequired > 0 && essenceCount == 0
    }

    override fun run() {
        if (Bank.open()) {
            if (Bank.depositAllExcept(_bankItemName)) {
                if (Bank.withdraw(_bankItemName, 25) || Inventory.count(_bankItemName) == 25) {
                    Bank.close()
                } else if (Bank.stream().name(_bankItemName).count().toInt() == 0) {
                    Notifications.showNotification("Out of ess $_bankItemName")
                    ScriptManager.stop()
                }
            }
        } else {
            Movement.walkTo(BANK_VARROCK_EAST)
        }
    }

    override fun stepName(): String {
        return "Banking for essence"
    }
}