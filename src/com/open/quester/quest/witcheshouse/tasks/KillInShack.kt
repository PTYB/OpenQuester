package com.open.quester.quest.witcheshouse.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.Conditions
import com.open.quester.helpers.CombatHelper
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.api.rt4.walking.local.Utils.getWalkableNeighbor
import org.powbot.mobile.drawing.Rendering
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.AREA_SHACK
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.ITEM_BALL
import org.powbot.quests.quest.witcheshouse.WitchesHouseConstants.TILE_SOUTH_SAFESPOT

class KillInShack(val information: QuestInformation) : BaseQuestStep() {
    private var drawTile: Tile? = null

    override fun shouldExecute(): Boolean {
        return AREA_SHACK.contains(Players.local()) && Inventory.stream().name(ITEM_BALL).count().toInt() == 0
    }

    override fun run() {
        val npc = getNpc()

        if (npc === Npc.Nil) {
            takeBall()
        } else {
            if (npc.combatLevel > 30) {
                killUsingSouthSafespot(npc)
            } else {
                attackOrEat(npc)
                // Work hassel than its worth  killUsingNorthSafespot(npc)
            }
        }
    }

    private fun takeBall() {
        val ball = GroundItems.stream().name(ITEM_BALL).nearest().first()
        drawTile = ball.tile
        if (ball.inViewport() && ball.interact("Take")) {
            Condition.wait {
                Conditions.waitUntilItemEntersInventory(ITEM_BALL, 0).invoke() ||
                        Conditions.waitUntilNpcAppears("Witch's experiment").call()
            }
        } else {
            Movement.builder(ball.tile.getWalkableNeighbor(true))
                .setWalkUntil { GroundItems.stream().name(ITEM_BALL).nearest().first().inViewport() }
                .move()
        }
    }

    private fun killUsingSouthSafespot(npc: Npc) {
        val player = Players.local()
        if (player.tile() == TILE_SOUTH_SAFESPOT) {
            if (Chat.canContinue()) {
                Chat.clickContinue()
            } else {
                attackOrEat(npc)
            }
        } else {
            Movement.builder(TILE_SOUTH_SAFESPOT)
                .setRunMin(5)
                .setRunMax(10)
                .setWalkUntil {
                    if (CombatHelper.shouldEat(*information.foodName)) {
                        CombatHelper.eatFood(*information.foodName)
                    }
                    Players.local().tile() === TILE_SOUTH_SAFESPOT
                }
                .move()
            drawTile = TILE_SOUTH_SAFESPOT
        }
    }

    private fun attackOrEat(npc: Npc) {
        if (Players.local().interacting() == npc) {
            if (CombatHelper.shouldEat(*information.foodName)) {
                CombatHelper.eatFood(*information.foodName)
            }
        } else {
            attackNpc(npc)
        }
    }

    private fun attackNpc(npc: Npc) {
        if (npc.healthPercent() > 0 && npc.interact("Attack")) {
            Condition.wait({ CombatHelper.isNpcAttackingMe(getNpc()) }, 300, 10)
        }
    }

    private fun getNpc(): Npc {
        return Npcs.stream().filtered { it.name.contains("Witch's experiment") }.first()
    }

    override fun stepName(): String {
        return "Killings NPCS"
    }

    override fun draw(g: Rendering) {
        val tile = drawTile
        if (tile == null || tile == Tile.Nil) {
            return
        }
        tile.matrix().drawOnScreen("")
    }
}