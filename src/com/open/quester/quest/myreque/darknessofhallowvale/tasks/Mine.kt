package com.open.quester.quest.myreque.darknessofhallowvale.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.count
import com.open.quester.extensions.nearestGameObject
import com.open.quester.extensions.nearestNpc
import com.open.quester.models.QuestInformation
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.VARP_INFORATION
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.VARP_MASK_MINING
import com.open.quester.quest.myreque.darknessofhallowvale.DarknessOfHallowvaleConstants.VARP_SHIFT_MINING
import org.powbot.api.Condition
import org.powbot.api.rt4.*
import org.powbot.api.rt4.walking.local.LocalPathFinder

class Mine(val information: QuestInformation) : BaseQuestStep() {
    private val NAME_ROCK = "Daeyalt rocks"
    private val NAME_ORE = "Daeyalt ore"
    private val NAME_MINE_CART = "Mine cart"
    private var nearestRock = GameObject.Nil

    override fun shouldExecute(): Boolean {
        nearestRock = Objects.nearestGameObject(17962, 17963, 17964)
        return nearestRock != GameObject.Nil
    }

    override fun run() {
        if (Chat.chatting()) {
            Chat.completeChat()
            return
        }

        val remainingOre = remainingOre()
        if (remainingOre == 0) {
            val vamp = Npcs.nearestNpc("Vampyre Juvinate")
            if (!vamp.inViewport()) {
                LocalPathFinder.findWalkablePath(vamp.tile()).traverseUntilReached(4.0)
            }

            if (vamp.inViewport() && vamp.interact("Talk-to")) {
                Condition.wait { Chat.chatting() }
                Chat.completeChat()
            }
        } else if (Inventory.isFull() || Inventory.count(NAME_ORE) == remainingOre) {
            clearUpInventory()
        } else {
            mineOre()
        }
    }

    private fun mineOre() {
        if (!nearestRock.inViewport()) {
            LocalPathFinder.findWalkablePath(nearestRock.tile).traverseUntilReached(4.0)
        }

        if (nearestRock.inViewport() && nearestRock.interact("Mine")) {
            val count = Inventory.count(NAME_ORE)
            Condition.wait({ count < Inventory.count(NAME_ORE) }, 500, 12)
        }
    }

    private fun clearUpInventory() {
        val oreCount = Inventory.count(NAME_ORE)
        if (oreCount > 0) {
            handInOre()
        } else {
            if (Inventory.stream().name(*information.foodName).first().interact("Drop")) {
                Condition.wait { !Inventory.isFull() }
            }
        }
    }

    private fun handInOre() {
        val mineCart = Objects.stream().name(NAME_MINE_CART).nearest().first()
        if (!mineCart.inViewport()) {
            LocalPathFinder.findWalkablePath(mineCart.tile).traverseUntilReached(4.0)
        }
        if (mineCart.inViewport() && mineCart.interact("Inspect")) {
            Condition.wait { Inventory.count(NAME_ORE) == 0 }
        }
    }

    private fun remainingOre(): Int {
        val result = Varpbits.varpbit(VARP_INFORATION, VARP_SHIFT_MINING, VARP_MASK_MINING)
        return 15 - result
    }

    override fun stepName(): String {
        return "Mining."
    }
}