package com.open.quester.quest.sheepshearer.leafs

import com.open.quester.common.QuestLeafStep
import com.open.quester.extensions.Conditions
import com.open.quester.extensions.count
import com.open.quester.helpers.InterfacesHelper
import com.open.quester.quest.sheepshearer.SheepShearer
import com.open.quester.quest.sheepshearer.SheepShearerConstants.ACTION_SPIN
import com.open.quester.quest.sheepshearer.SheepShearerConstants.ITEM_BALL_OF_WOOL
import com.open.quester.quest.sheepshearer.SheepShearerConstants.ITEM_WOOL
import com.open.quester.quest.sheepshearer.SheepShearerConstants.NAME_SPINNING_WHEEL
import com.open.quester.quest.sheepshearer.SheepShearerConstants.TILE_SPINNING_WHEEL
import org.powbot.api.Condition
import org.powbot.api.rt4.*

class SpinBallsOfWool(val quest: SheepShearer) : QuestLeafStep<SheepShearer>(quest, "Spinning balls of wool") {

    override fun run() {
        if (InterfacesHelper.combineInterfaceOpen() &&
            InterfacesHelper.createItemInterface(ITEM_BALL_OF_WOOL).interact("")
        ) {
            waitForWoolToSpin()
        } else {
            val wheel = Objects.stream().type(GameObject.Type.INTERACTIVE).name(NAME_SPINNING_WHEEL).nearest().first()

            if (wheel.valid() && wheel.reachable() && wheel.interact(ACTION_SPIN)) {
                Condition.wait { InterfacesHelper.combineInterfaceOpen() }
            } else {
                Movement.builder(TILE_SPINNING_WHEEL)
                    .setRunMin(10)
                    .setRunMax(30)
                    .move()
            }
        }
    }

    private fun waitForWoolToSpin(): Boolean {
        val woolCount = Inventory.count(ITEM_WOOL)
        for (i in 0..woolCount) {
            val ballMade = Condition.wait(Conditions.expGained(Constants.SKILLS_CRAFTING))
            if (!ballMade) {
                return false
            }
        }
        return true
    }
}