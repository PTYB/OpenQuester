package com.open.quester.common

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.SetupResult
import com.open.quester.tasks.SetupTask
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.mobile.script.ScriptManager
import java.util.concurrent.Callable
import kotlin.streams.toList

class BankStep(
    val conditions: List<ItemRequirementCondition>,
    val bankTile: Tile,
    val shouldExecute: Callable<Boolean>? = null
) : BaseQuestStep() {

    private var setup = false
    private val setupTask: SetupTask by lazy {
        SetupTask(conditions, listOf())
    }
    private var itemsToKeep = arrayOf<String>()

    var hasBeenSetup = false

    init {
        setup = conditions.all { it.chosenRequirement != null }
    }

    override fun shouldExecute(): Boolean {
        return !setup && (shouldExecute == null || shouldExecute.call())
    }

    override fun run() {
        if (GrandExchange.opened() && !GrandExchange.close()) {
            return
        }

        if (!setup && !setupConditions()) {
            return
        }

        if (hasRequirements()) {
            hasBeenSetup = true
            return
        }

        if (Bank.opened()) {
            setupInventory()
        } else if (bankTile.distanceTo(Players.local()) < 3) {
            Bank.open()
        } else {
            Movement.builder(bankTile)
                .setRunMax(10)
                .setRunMax(60)
                .setWalkUntil {
                    // TODO Check if needs to eat here when doing combat quests.
                    bankTile.distanceTo(Players.local()) < 3
                }
                .move()
        }
    }

    private fun setupInventory() {
        if (Bank.depositAllExcept(*itemsToKeep)) {
            val requirements = conditions.map { it.chosenRequirement!! }.toList()
            requirements.forEach { r ->
                val chosenItem = Inventory.stream().name(r.name).count(true)
                if (chosenItem >= r.countRequired) {
                    return@forEach
                }

                Bank.withdraw(r.name, r.countRequired - chosenItem.toInt())
            }
        }
    }

    /**
     *   Checks if it has all the requirements already
     */
    private fun hasRequirements() : Boolean {
        return setup && conditions.all {
            if (it.chosenRequirement == null) {
                return false
            }

            return Inventory.stream().name(it.chosenRequirement!!.name).count(true) >=
                    it.chosenRequirement!!.countRequired
        }
    }

    /**
     *  Sets up the bank conditions so we know what to withdraw for the specific conditions,
     *  @return true if we successfully setup
     */
    private fun setupConditions(): Boolean {
        return when (setupTask.complete()) {
            SetupResult.FAILURE, SetupResult.INCOMPLETE -> {
                ScriptManager.stop()
                logger.info("Unable to resume with the given items")
                false
            }
            SetupResult.UNKNOWN -> false
            SetupResult.COMPLETE -> {
                setup = true
                itemsToKeep = conditions.stream().map { it.chosenRequirement!!.name }.toList().toTypedArray()
                true
            }
        }
    }

    override fun stepName(): String {
        return "Banking for items"
    }

}