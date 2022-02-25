package com.open.quester.common.base

import com.open.quester.extensions.Conditions
import com.open.quester.helpers.AutoCastSpell
import com.open.quester.helpers.MagicHelpers
import com.open.quester.helpers.MagicHelpers.isAutoCastOpen
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.Random
import org.powbot.api.Tile
import org.powbot.api.rt4.*

class CombatStep(
    val npcTile: Tile,
    val information: QuestInformation,
    val npcsToAttack: String,
    val stepText: String,
    val shouldExecute: () -> Boolean = { true },
) : BaseQuestStep() {

    private var healthToEat = Random.nextInt(40, 53)
    private var setup = false
    private val ACTION_ATTACK = "Attack"
    private val ACTION_EAT = "Eat"

    override fun shouldExecute(): Boolean {
        return shouldExecute.invoke()
    }

    override fun run() {
        if (Bank.opened()) {
            logger.info("Closing bank")
            Bank.close()
            return
        }

        if (!setup) {
            if (!setupWeapon()) {
                return
            }
            setup = true
        }
        attackNpc()
    }

    override fun stepName(): String {
        return stepText
    }

    protected fun attackNpc(): Boolean {
        val npcAttackingMe = getNpcsAttackingMe()
        if (attacking() && npcAttackingMe == Npc.Nil) {
            return Condition.wait { getNpcsAttackingMe() != Npc.Nil }
        } else if (attacking() && npcAttackingMe != Npc.Nil) {
            inCombat(npcAttackingMe)
            return true
        } else {
            val nearbyNpc = Npcs.stream().name(npcsToAttack).nearest().first()
            if (nearbyNpc == Npc.Nil) {
                Movement.builder(npcTile)
                    .setRunMax(10)
                    .setRunMax(30)
                    .setWalkUntil {
                        val npc = Npcs.stream().name(npcsToAttack).nearest().first()
                        npc != Npc.Nil && npc.inViewport() && npc.reachable()
                    }
                    .move()
            } else if (!nearbyNpc.inViewport(true)) {
                Movement.builder(npcTile)
                    .setRunMax(10)
                    .setRunMax(30)
                    .setWalkUntil {
                        npcTile.reachable()
                    }
                    .move()
            } else {
                if (nearbyNpc.interact(ACTION_ATTACK)) {
                    Condition.wait { attacking() }
                }
            }
        }
        return false
    }

    private fun inCombat(attackingNpc: Npc) {
        val healthPercent: Int = Random.nextInt(40, 53)

        if (!attacking() && attackingNpc.inViewport() && attackingNpc.healthPercent() > 0) {
            attackingNpc.interact(ACTION_ATTACK)
        }

        if (!information.foodName.isNullOrEmpty() && Players.local()
                .healthPercent() < healthToEat
        ) {
            eatFood(attackingNpc)
        }
    }

    private fun eatFood(attackingNpc: Npc) {
        val food: Item = Inventory.stream().name(*information.foodName).first()
        if (food.valid()) {
            food.interact(ACTION_EAT)
            Condition.wait { !food.valid() }
            if (attackingNpc.interact(ACTION_ATTACK)) {
                Condition.wait({ attacking() }, 100, 13)
            }
        }
    }

    protected fun attacking(): Boolean {
        return Players.local().interacting().combatLevel > 0
    }

    private fun getNpcsAttackingMe(): Npc {
        val player = Players.local()
        return if (!player.healthBarVisible()) {
            Npc.Nil
        } else Npcs.stream().filtered { x -> x.interacting().name() == player.name() }.first()
    }

    private fun setupWeapon(): Boolean {
        if (information.weaponName == Item.Nil) {
            return true
        }

        val currentWeapon = Equipment.itemAt(Equipment.Slot.MAIN_HAND)
        if (currentWeapon.name() != information.weaponName!!.name()) {
            val weapon = Inventory.stream().name(information.weaponName.name()).first()
            logger.info("CombatStep: Wielding weapon ${information.weaponName}")
            if (weapon.interact("Wield")) {
                Condition.wait(Conditions.waitUntilItemLeavesInventory(information.weaponName.name(), 1))
            } else {
                return false
            }
        }

        if (information.spell != null && !MagicHelpers.isAutoCasting()) {

            Game.tab(Game.Tab.ATTACK)
            logger.info("Setting spell ${information.spell!!.name}")
            if (isAutoCastOpen() || MagicHelpers.openAutocastTab()) {
                val spell = AutoCastSpell.values().first { it.spell == information.spell }
                return MagicHelpers.setAutoCast(spell)
            } else {
                return false
            }
        }

        return true
    }
}