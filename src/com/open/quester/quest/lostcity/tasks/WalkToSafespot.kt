package com.open.quester.quest.lostcity.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.nearestNpc
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Npc
import org.powbot.api.rt4.Npcs
import org.powbot.api.rt4.Players
import com.open.quester.quest.lostcity.LostCityConstants

class WalkToSafespot : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        val playerTile = Players.local().tile()
        return  playerTile != LostCityConstants.TILE_SAFESPOT &&
                LostCityConstants.AREA_END_ROOM.contains(playerTile) && Npcs.nearestNpc("Tree spirit") != Npc.Nil
    }

    override fun run() {
        Movement.step(LostCityConstants.TILE_SAFESPOT, 0)
    }

    override fun stepName(): String {
        return "Walking to safespot"
    }
}