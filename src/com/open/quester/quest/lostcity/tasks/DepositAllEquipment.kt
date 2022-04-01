package com.open.quester.quest.lostcity.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.count
import com.open.quester.models.QuestInformation
import org.powbot.api.Notifications
import org.powbot.api.rt4.*
import org.powbot.mobile.script.ScriptManager
import com.open.quester.quest.lostcity.LostCityConstants.AREA_ENTRANA
import com.open.quester.quest.lostcity.LostCityConstants.AREA_LOST_CITY_DUNGEON
import org.powbot.api.requirement.RunePowerRequirement

class DepositAllEquipment(val information: QuestInformation) : BaseQuestStep() {

    private var hasEquipment = false

    private var itemsToKeep = lazy {
        getList()
    }

    private fun getList(): Array<String> {
        val items = mutableListOf("Knife")
        information.spell!!.requirements.forEach {
            if (it is RunePowerRequirement) {
                val runeName = it.power.getFirstRune().name.lowercase() + " rune"
                items.add(runeName)
            }
        }
        if (!information.foodName.isNullOrEmpty()) {
            items.addAll(information.foodName)
        }
        return items.toTypedArray()
    }

    override fun shouldExecute(): Boolean {
        val playerLocation = Players.local()
        if (AREA_ENTRANA.contains(playerLocation) || AREA_LOST_CITY_DUNGEON.contains(Players.local())) {
            return false
        }

        hasEquipment = Equipment.stream().any { it != Item.Nil }
        if (hasEquipment) {
            logger.info("Has equipment")
            return true
        }
        val hasBadItems =
            Inventory.stream().any {
                !information.foodName.contains(it.name()) && !it.name().contains(" rune", true) &&
                        it.name() != "Knife"
            }
        if (hasBadItems) {
            logger.info("Has bad item")
            return true
        }

        if (Inventory.count("Knife") < 1) {
            logger.info("Missing knfe")
            return true
        }

        if (Inventory.count(*information.foodName) < 20) {
            return true
        }

        if (information.spell == null) {
            Notifications.showNotification("No spell detected")
            ScriptManager.stop()
            return false
        }

        information.spell!!.requirements.forEach {
            if (it is RunePowerRequirement) {
                val runeName = it.power.getFirstRune().name.lowercase() + " rune"
                val count = Inventory.count(runeName)
                if (count < it.amount * 100) {
                    logger.info("Missing rune ${runeName}")
                    return true
                }
            }
        }

        return false
    }

    override fun run() {
        if (!Bank.opened()) {
            Movement.moveToBank(false)
            Bank.open()
        }

        if (Bank.opened()) {
            if (hasEquipment) {
                logger.info("Depositing equipment")
                Bank.depositEquipment()
            }
            if (information.spell == null) {
                Notifications.showNotification("No spell detected, remember to start script with spell or wand")
            }
            if (Bank.depositAllExcept(*itemsToKeep.value)) {
                if (information.foodName.isNotEmpty()) {
                    val foodCount = Inventory.count(*information.foodName)
                    if (foodCount < 20) {
                        logger.info("Withdrawing food $foodCount")
                        Bank.withdraw(information.foodName.first(), 20 - foodCount)
                    }
                }
                information.spell!!.requirements.forEach {
                    if (it is RunePowerRequirement) {
                        val runeName = it.power.getFirstRune().name.lowercase() + " rune"
                        val count = Inventory.count(runeName)
                        if (count < it.amount * 100) {
                            Bank.withdraw(runeName, it.amount * 100 - count)
                        }
                    }
                }

                if (Inventory.count("Knife") < 1) {
                    logger.info("Withdrawing knife")
                    Bank.withdraw("Knife", 1)
                }
            }

        }
    }

    override fun stepName(): String {
        return "Banking excess items"
    }
}