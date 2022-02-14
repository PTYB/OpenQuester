package com.open.quester.tasks

import com.open.quester.models.ItemRequirementCondition
import com.open.quester.models.QuestRunnerState
import com.open.quester.models.SetupResult
import org.powbot.api.Area
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.mobile.script.ScriptManager
import java.util.logging.Logger

class GrandExchangeTask(private val itemsRequired: List<ItemRequirementCondition>) {
    private val logger: Logger = Logger.getLogger(this.javaClass.simpleName)
    private val grandExchangeArea = Area(Tile(3159, 3484), Tile(3170, 3494))

    val prices = mutableMapOf<String, Int>()
    var bestItemsSelected = false

    init {
        itemsRequired.forEach { ir ->
            ir.itemRequirements.forEach { r ->
                if (r.isUntradable) {
                    return@forEach
                }
                val price = GrandExchangeItem.fromName(r.name).high
                prices[r.name] = price
            }
        }
    }

    fun complete() : SetupResult {
        if (!grandExchangeArea.contains(Players.local())) {
            walkToGrandExchange()
            return SetupResult.UNKNOWN
        }

        // TODO Check amount
        val hasCoins = Inventory.stream().name("Coins").count() > 0
        if (!hasCoins) {
            // TODO Failure when no GP
            withdrawCoins()
            return SetupResult.UNKNOWN
        }

        if (Bank.opened()) {
            Bank.close()
        }

        if (!GrandExchange.opened()) {
            logger.info("Opening grand exchange")
            GrandExchange.open()
        } else if (!bestItemsSelected) {
            calculateCheapestItems()
        } else {

            val isComplete = itemsRequired.all { it.chosenRequirement!!.hasRequirement }
            if (isComplete) {
                logger.info("Completed buying from grand exchange")
                return SetupResult.COMPLETE
            } else {
                buyItems()
            }
        }
        return SetupResult.UNKNOWN
    }

    private fun buyItems() {
        val slots = GrandExchange.allSlots()

        val offersToPlace = itemsRequired
            .filter {
                val requirement = it.chosenRequirement!!
                !requirement.isUntradable &&
                        slots.none { s -> s.itemName() == it.chosenRequirement!!.name }
            }
            .toList()

        if (offersToPlace.isEmpty()) {
            collectOffers(slots)
        } else {
            logger.info("Remaining orders to buy ${offersToPlace.size}")
            val requirement = offersToPlace.first().chosenRequirement!!
            logger.info("Placing offer ${requirement.name}")
            val geItem = GrandExchangeItem.fromName(requirement.name)
            GrandExchange.createOffer(geItem, requirement.countRequired, geItem.high, true)
        }
    }

    private fun collectOffers(slots: ArrayList<GeSlot>) {
        val completedOffers = slots.filter { it.isFinished() }.toList()

        if (completedOffers.isEmpty()) {
            logger.info("Giving some time for offers to purchase")
            Condition.sleep(Random.nextInt(4000, 9000))
            return
        } else {
            completedOffers.forEach { co ->
                logger.info("--- Checking ${co.itemName()} ---")
                val offerCondition = itemsRequired.firstOrNull { ro ->
                    co.itemName() == ro.chosenRequirement!!.name
                }
                if (offerCondition == null) {
                    logger.info("None found for ${co.itemName()}")
                }else if (GrandExchange.collectOffer(co)) {
                    offerCondition.chosenRequirement!!.hasRequirement = true
                    logger.info("Completed offer ${co.itemName()}")
                } else {
                    logger.info("Failed to collect offer ${co.itemName()}")
            }
        }
    }
}

private fun calculateCheapestItems() {
    itemsRequired.forEach { ir ->
        if (ir.chosenRequirement != null) {
            return@forEach
        }
        val bestOne = ir.itemRequirements.minByOrNull { r -> prices[r.name] ?: Int.MAX_VALUE }

        if (bestOne == null) {
            ScriptManager.stop()
            return
        }
        ir.chosenRequirement = bestOne
        logger.info("Set best requirement to ${ir.chosenRequirement!!.name}")
    }
    bestItemsSelected = true
}


private fun withdrawCoins(): Boolean {
    if (!Bank.opened() && !Bank.open()) {
        return false
    }

    val bankStack = Bank.stream().name("Coins").first()
    if (bankStack == Item.Nil) {
        logger.info("No coins, sadness :(")
        ScriptManager.stop()
        return false
    }

    // TODO Use calculated price from high.
    if (Bank.withdraw("Coins", 0) && Condition.wait { Inventory.stream().name("Coins").count() > 0 }) {
        logger.info("Withdrawed coins")
        Bank.close()
        return true
    }
    return false
}

private fun walkToGrandExchange() {
    Movement.builder(Tile(3168, 3487, 0))// TODO Randomize more
        .setWalkUntil {
            grandExchangeArea.contains(Players.local()) &&
                    Npcs.stream().action("Exchange").viewable().first() != Npc.Nil
        }
        .move()
}
}