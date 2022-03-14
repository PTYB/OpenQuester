package com.open.quester.helpers

import org.powbot.api.Condition
import org.powbot.api.Input
import org.powbot.api.MenuCommand
import org.powbot.api.Random
import org.powbot.api.rt4.*
import java.util.concurrent.Callable

object InteractionsHelper {

    fun useItemOn(firstItem: Item, secondItem: Item): Boolean {
        if (!Inventory.opened()) {
            Inventory.open()
        }

        var selectedItem = Inventory.selectedItem()
        if (selectedItem != firstItem) {
            if (!deselectItem()) {
                return false
            }
        }

        if (!firstItem.interact("Use")) {
            return false
        } else {
            Condition.sleep(Random.nextInt(250, 450))
        }
        return secondItem.click()
    }

    fun useItemOnInteractive(
        item: Item,
        interactive: Interactive
    ): Boolean {
        return if (item.interact("Use") && Inventory.selectedItem().name() == item.name()) {
            return interactive.interact("Use")
        } else {
            false
        }
    }

    fun deselectItem(): Boolean {
        val selectedItem = Inventory.selectedItem()
        if (selectedItem == Item.Nil) {
            return true
        }

        if (Game.singleTapEnabled()) {
            selectedItem.click()
        } else if (!Menu.opened()) {

            val height: Int = Game.dimensions().height
            val width: Int = Game.dimensions().width

            Input.tap(Random.nextInt(0, width), Random.nextInt(0, height))
            Condition.wait({ Menu.opened() }, 100, 15)
        }

        if (Menu.opened()) {
            val menuClick = Menu.click { menuCommand: MenuCommand ->
                menuCommand.action.contains(
                    "Cancel"
                )
            }
            if (menuClick) {
                return Condition.wait({ Inventory.selectedItem() == Item.Nil }, 100, 15)
            }
        }
        return false
    }

}