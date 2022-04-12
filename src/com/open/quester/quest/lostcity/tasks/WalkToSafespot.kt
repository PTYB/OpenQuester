package com.open.quester.quest.lostcity.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.nearestNpc
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Npc
import org.powbot.api.rt4.Npcs
import org.powbot.api.rt4.Players
import com.open.quester.quest.lostcity.LostCityConstants
import com.open.quester.quest.lostcity.LostCityConstants.TILE_SAFESPOT
import org.powbot.api.Condition

class WalkToSafespot : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        val playerTile = Players.local().tile()
        return  playerTile != TILE_SAFESPOT &&
                LostCityConstants.AREA_END_ROOM.contains(playerTile) && Npcs.nearestNpc("Tree spirit") != Npc.Nil
    }

    override fun run() {
        if (Players.local().tile() != TILE_SAFESPOT) {
            if (TILE_SAFESPOT.matrix().inViewport()) {
                TILE_SAFESPOT.matrix().interact("Walk here")
            } else {
                Movement.step(TILE_SAFESPOT, 0)
            }
        }
        if(Condition.wait { Players.local().inMotion() }) {
            Condition.wait { !Players.local().inMotion() }
        }
    }

    override fun stepName(): String {
        return "Walking to safespot"
    }
}