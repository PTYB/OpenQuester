package com.open.quester.helpers

import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.rt4.Combat
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Item

object CombatHelper {
    private var lowerAmount = 40
    private var higherAmount = 50
    private var hpToEatAt = Random.nextInt(lowerAmount, higherAmount)

    fun updateHealthToEatAt(lower: Int, high: Int) {
        lowerAmount = lower
        higherAmount = high
        hpToEatAt = Random.nextInt(lowerAmount, higherAmount)
    }

    fun shouldEat(vararg foodName: String?): Boolean {
        if (foodName.isEmpty()) {
            return false
        }

        return Combat.healthPercent() <= hpToEatAt
    }

    fun eatFood(vararg foodName: String?): Boolean {
        val orderById = foodName.withIndex().associate { it.value to it.index }
        val foodItem = Inventory.stream().name(*foodName).sortedBy { orderById[it.name()] }.firstOrNull()

        if (foodItem == null || foodItem == Item.Nil) {
            return false
        }
        val hp = Combat.health()
        if (foodItem.interact(getEatAction(foodItem.name())) &&
            Condition.wait { Combat.health() > hp }) {
            hpToEatAt = Random.nextInt(lowerAmount, higherAmount)
            if (foodItem.name() == "Jug of wine") {
                dropJug()
            }
            return true
        }
        return false
    }

    private fun dropJug() {
        var jug: Item = Item.Nil

        Condition.wait({
            jug = Inventory.stream().name("Jug").first()
            jug != Item.Nil
        }, 100, 15)
        if (jug != Item.Nil) {
            Inventory.drop(jug, false)
        }
    }

    fun getEatAction(foodName: String?): String {
        if (foodName == null) {
            return ""
        }

        return if (foodName == "Jug of wine") {
            "Drink"
        } else {
            "Eat"
        }
    }
}