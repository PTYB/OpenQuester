package com.open.quester.quest.theknightssword.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.count
import com.open.quester.helpers.CombatHelper
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.AREA_ICE_DUNGEON
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.ITEM_BLURITE_ORE
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.ITEM_BLURITE_SWORD
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.NAME_BLURITE_ROCK
import com.open.quester.quest.theknightssword.TheKnightsSwordConstants.TILE_BLURITE_TILE
import org.powbot.api.Condition
import org.powbot.api.rt4.*

class MineTheOre(vararg food: String) : BaseQuestStep() {
    private val food = food.toList().toTypedArray()

    override fun shouldExecute(): Boolean {
        return Inventory.count(ITEM_BLURITE_ORE) == 0 &&
                Inventory.count(ITEM_BLURITE_SWORD) == 0
    }

    override fun run() {
        if (CombatHelper.shouldEat(*food)) {
            CombatHelper.eatFood(*food)
        }
        val ore = getOre()
        if (ore.inViewport()) {
            if (ore.interact("Mine")) {
                Condition.wait { Inventory.stream().name(NAME_BLURITE_ROCK).count() > 0 || CombatHelper.shouldEat(*food) }
            }
        } else {
            Movement.builder(TILE_BLURITE_TILE.derive(0, 1))
                .setRunMin(1)
                .setRunMax(10)
                .setWalkUntil {
                    if (CombatHelper.shouldEat(*food)) {
                        CombatHelper.eatFood(*food)
                    }
                    getOre().inViewport()
                }
                .move()

        }
    }

    private fun getOre() : GameObject {
        return Objects.stream().type(GameObject.Type.INTERACTIVE).name(NAME_BLURITE_ROCK).within(AREA_ICE_DUNGEON).nearest().first()
    }

    override fun stepName(): String {
        return "Mining blurite ore"
    }
}