package com.open.quester.quest.treegnomevillage.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.Conditions
import com.open.quester.helpers.CombatHelper
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.rt4.*
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.ITEM_ORBS_OF_PROTECTION
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.NAME_KHAZARD_WARLORD
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.TILE_FIRST_LURE_SPOT
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.TILE_KHAZARD_WARLORD
import org.powbot.quests.quest.treegnomevillage.TreeGnomeVillageConstants.TILE_LURE_SAFESPOT

class GetOrbs(val information: QuestInformation) : BaseQuestStep() {
    override fun shouldExecute(): Boolean {
        return true
    }

    override fun run() {
        if (CombatHelper.shouldEat(*information.foodName)) {
            CombatHelper.eatFood(*information.foodName)
        }

        if (Chat.chatting()) {
            Chat.completeChat()
            return
        }

        val npc = getInteractingNpcs
        if (npc == Npc.Nil) {
            if (getGroundItem != GroundItem.Nil) {
                lootOrb()
            } else {
                talkToKhazardWarlord()
            }
        } else {
            lureNpc(npc)
        }
    }

    private fun talkToKhazardWarlord() {
        val npc = Npcs.stream().name(NAME_KHAZARD_WARLORD).first()

        if (npc == Npc.Nil || !npc.inViewport()) {
            Movement.builder(TILE_KHAZARD_WARLORD)
                .setRunMin(5)
                .setRunMax(20)
                .setWalkUntil {
                    if (CombatHelper.shouldEat(*information.foodName)) {
                        CombatHelper.eatFood(*information.foodName)
                    }
                    (TILE_KHAZARD_WARLORD.matrix().inViewport() && TILE_KHAZARD_WARLORD.distanceTo(Players.local()) < 6)
                            || getInteractingNpcs != Npc.Nil
                }
                .move()
        } else {
            if (npc.interact("Talk-to")) {
                Condition.wait(Conditions.waitUntilNotMoving())
                Condition.wait(Conditions.waitUntilChatting())
            }
        }
    }

    private fun lootOrb() {
        val groundItem = getGroundItem

        if (!groundItem.inViewport()) {
            Movement.builder(groundItem.tile)
                .setRunMin(10)
                .setRunMax(40)
                .setWalkUntil { groundItem.tile.matrix().inViewport() }
                .move()
        }

        if (groundItem.inViewport() && groundItem.interact("Take")) {
            Condition.wait(Conditions.waitUntilItemEntersInventory(ITEM_ORBS_OF_PROTECTION, 0))
        }
    }


    private fun lureNpc(npc: Npc) {
        val playerTile = Players.local().tile()
        if (playerTile.distanceTo(TILE_FIRST_LURE_SPOT) < 1 && playerTile.x >= 2446) {
            logger.info("Safespot luring")
            if (getInteractingNpcs.tile().distanceTo(playerTile) < 2) {
                Movement.step(TILE_LURE_SAFESPOT, 0)
            }
        } else if (playerTile == TILE_LURE_SAFESPOT) {
            val interactingNpc = Players.local().interacting()
            if (interactingNpc == Actor.Nil) {
                // Possible level ups
                if (Chat.chatting()) {
                    Chat.completeChat()
                }
                if (npc.interact("Attack")) {
                    Condition.wait { Players.local().interacting() != Actor.Nil }
                }
            } else {
                if (interactingNpc.healthPercent() == 0) {
                    val result = Condition.wait { getGroundItem != GroundItem.Nil }
                    if (result) {
                        lootOrb()
                    }
                }
            }
        } else {
            Movement.step(TILE_FIRST_LURE_SPOT, 0)
        }

    }

    override fun stepName(): String {
        return "Getting the orb"
    }


    private val getGroundItem: GroundItem
        get() = GroundItems.stream().name(ITEM_ORBS_OF_PROTECTION).first()

    private val getInteractingNpcs: Npc
        get() = Npcs.stream().name(NAME_KHAZARD_WARLORD).action("Attack").first()

}