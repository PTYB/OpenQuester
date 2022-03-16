package com.open.quester.extensions

import org.powbot.api.rt4.*
import java.util.concurrent.Callable

class Conditions {

    companion object {
        fun waitUntilItemEntersInventory(
            itemName: String,
            itemCount: Int,
        ): () -> Boolean {
            return {
                val newCount: Int = Inventory.stream().name(itemName)
                    .toList().sumOf { obj: Item -> obj.stack }
                newCount > itemCount
            }
        }

        fun waitUntilItemEntersInventory(
            itemId: Int,
            itemCount: Int,
        ): () -> Boolean {
            return {
                val newCount: Int = Inventory.stream().id(itemId)
                    .toList().sumOf { obj: Item -> obj.stack }
                newCount > itemCount
            }
        }

        fun waitUntilItemLeavesInventory(
            itemName: String,
            itemCount: Int,
        ): () -> Boolean {
            return {
                val newCount: Int = Inventory.stream().name(itemName)
                    .toList().sumOf { obj: Item -> obj.stack }
                newCount < itemCount
            }
        }

        fun waitUntilAnyItemEntersInventory(): Callable<Boolean> {
            val originalCount = Inventory.emptySlotCount()
            return Callable<Boolean> {
                originalCount > Inventory.emptySlotCount()
            }
        }

        fun waitUntilNotMoving(): Callable<Boolean> {
            return Callable<Boolean> { !Players.local().inMotion() }
        }

        fun waitUntilChatting(): Callable<Boolean> {
            return Callable<Boolean> { Chat.chatting() }
        }

        fun waitUntilNotChatting(): Callable<Boolean> {
            return Callable<Boolean> { !Chat.chatting() }
        }

        fun waitUntilNonIdleAnimation(): Callable<Boolean> {
            return Callable<Boolean> { Players.local().animation() != -1 }
        }

        fun waitUntilIdleAnimation(): Callable<Boolean> {
            return Callable<Boolean> { Players.local().animation() == -1 }
        }

        fun expGained(skill: Int): Callable<Boolean> {
            val startExp = Skills.experience(skill)
            return Callable<Boolean> { Skills.experience(skill) > startExp }
        }

        fun waitUntilComponentAppears(widget: Int, component: Int): Callable<Boolean> {
            return Callable<Boolean> { Widgets.component(widget, component).visible() }
        }

        fun waitUntilComponentAppears(widget: Int, component: Int, subComponent: Int): Callable<Boolean> {
            return Callable<Boolean> { Widgets.component(widget, component, subComponent).visible() }
        }

        fun waitUntilNpcAppears(id: Int): Callable<Boolean> {
            return Callable<Boolean> { Npcs.stream().id(id).first() != Npc.Nil }
        }

        fun waitUntilNpcAppears(name: String): Callable<Boolean> {
            return Callable<Boolean> { Npcs.stream().name(name).first() != Npc.Nil }
        }

        fun healthDecreases(): Callable<Boolean> {
            val currentHp = Combat.health()
            return Callable { Combat.health() < currentHp }
        }
    }
}