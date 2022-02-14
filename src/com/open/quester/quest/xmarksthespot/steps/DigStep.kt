package com.open.quester.quest.xmarksthespot.steps

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.quest.Constants.ITEM_SPADE
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.*

class DigStep(val tile: Tile, val stepText: String) : BaseQuestStep() {

    private var spade = Item.Nil

    override fun shouldExecute(): Boolean {
        spade = Inventory.stream().name(ITEM_SPADE).first()
        return spade != Item.Nil
    }

    override fun run() {
        if (Players.local().tile() == tile) {
            digForItem()
        } else {
            Movement.builder(tile)
                .setRunMax(20)
                .setRunMax(60)
                .move()
        }
    }

    private fun digForItem() {
        val currentVarp = getCurrentQuestVarp()
        if (spade.interact("Dig")) {
            Condition.wait { currentVarp != getCurrentQuestVarp() }
        }
    }

    private fun getCurrentQuestVarp(): Int {
        return Varpbits.varpbit(com.open.quester.Varpbits.X_MARKS_THE_SPOT.questVarbit)
    }

    override fun stepName(): String {
        return stepText
    }
}