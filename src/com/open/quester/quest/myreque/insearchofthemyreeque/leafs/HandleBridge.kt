package com.open.quester.quest.myreque.insearchofthemyreeque.leafs

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.quest.myreque.insearchofthemyreeque.InSearchOfTheMyrequeConstants.AREA_BRIDGE
import org.powbot.api.Condition
import org.powbot.api.rt4.GameObject
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players

class HandleBridge(val north: Boolean) : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return AREA_BRIDGE.contains(Players.local())
    }

    override fun run() {
        val itemsToRepair = Objects.stream().name("Broken rope bridge").nearest().first()

        if (itemsToRepair != GameObject.Nil) {
            if (itemsToRepair.interact("Repair")) {
                Condition.wait { !itemsToRepair.valid() }
            }
        } else {
            val tree = if (north) {
                Objects.stream().name("Tree").maxByOrNull { it.tile.y }!!
            } else {
                Objects.stream().name("Tree").minByOrNull { it.tile.y }!!
            }
            if (tree.tile.distance() > 3) {
                val crossObject = Objects.stream().action("Cross").nearest().first()
                if (crossObject != Objects.nil() && crossObject.interact("Cross")) {
                    Condition.wait { tree.tile.distance() < 2 }
                }
            }

            if (tree.interact("Climb")) {
                Condition.wait { !AREA_BRIDGE.contains(Players.local()) }
            }
        }
    }

    override fun stepName(): String {
        return "Handling bridge"
    }
}