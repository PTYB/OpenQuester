package com.open.quester.quest.myreque.inaidofthemyreque.leafs

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.nearestNpc
import com.open.quester.helpers.CombatHelper
import com.open.quester.models.QuestInformation
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.AREA_SECRET_BASE
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_IVAN
import com.open.quester.quest.myreque.inaidofthemyreque.InAidOfTheMyrequeConstants.NAME_JUVINATE
import org.powbot.api.Condition
import org.powbot.api.rt4.Npc
import org.powbot.api.rt4.Npcs
import org.powbot.api.rt4.Players

class KillVampyres(val information: QuestInformation) : BaseQuestStep() {

    override fun shouldExecute(): Boolean {
        return !AREA_SECRET_BASE.contains(Players.local()) && Npcs.nearestNpc(NAME_IVAN) != Npc.Nil
    }

    override fun run() {

        if (CombatHelper.shouldEat(*information.foodName)) {
            CombatHelper.eatFood(*information.foodName)
        }
        val targetNpcs = getNpcsAttackingIvan()
        val npcIAmAttack = Players.local().interacting()
        if (targetNpcs.isEmpty()) {
            logger.info("No npcs attacking Ivan :)")
            if (npcIAmAttack.name.isEmpty()) {
                logger.info("Attacking NPC")
                val nearestNpc = Npcs.nearestNpc(NAME_JUVINATE)
                if (nearestNpc.interact("Attack")) {
                    Condition.wait { Players.local().interacting().name.isNotEmpty() }
                }
            } else {
                logger.info("Already attacking NPC")
            }
        } else {
            if (npcIAmAttack.name.isEmpty()) {
                logger.info("Not attacking")
                if (targetNpcs.first().interact("Attack")) {
                    Condition.wait { Players.local().interacting().name.isNotEmpty() }
                }
            } else if (npcIAmAttack.interacting().name != NAME_IVAN){
                logger.info("Attacking NPC not attacking Ivan")
                if (targetNpcs.first().interact("Attack")) {
                    Condition.wait { Players.local().interacting().name.isNotEmpty() }
                }
            } else {
                logger.info("Attacking NPC attacking Ivan")
            }

        }

    }


    private fun getNpcsAttackingIvan(): List<Npc> {
        return Npcs.stream().filtered { x -> x.interacting().name() == NAME_IVAN }.toList()
    }

    override fun stepName(): String {
        return "Saving private Ivan"
    }
}