package com.open.quester.quest.demonslayer.tasks

import com.open.quester.Varpbits.*
import com.open.quester.common.base.BaseQuestStep
import com.open.quester.extensions.Conditions
import com.open.quester.extensions.nearestNpc
import com.open.quester.helpers.CombatHelper
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.Input
import org.powbot.api.Random
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import com.open.quester.quest.demonslayer.DemonSlayerConstants

class KillDelrith(private val information: QuestInformation) : BaseQuestStep() {

    var incantation = arrayOf<String>()

    override fun shouldExecute(): Boolean {
        return Equipment.itemAt(Equipment.Slot.MAIN_HAND).name() == DemonSlayerConstants.ITEM_SILVERLIGHT
    }

    override fun run() {
        setupIncantation()
        val delrith = Npcs.nearestNpc(DemonSlayerConstants.NAME_DELRITH, DemonSlayerConstants.NAME_WEAKENED_DELRITH)
        if (delrith == Npc.Nil) {
            if (Chat.chatting()) {
                Chat.completeChat()
            } else {
                Movement.builder(Tile(3226, 3368, 0))
                    .setWalkUntil { Npcs.nearestNpc(DemonSlayerConstants.NAME_DELRITH) != Npc.Nil || Chat.chatting() }
                    .move()
            }
        } else {
            if (CombatHelper.shouldEat(*information.foodName)) {
                CombatHelper.eatFood(*information.foodName)
            }
            if (delrith.name == DemonSlayerConstants.NAME_WEAKENED_DELRITH) {
                if (Chat.chatting()) {
                    for (option in incantation) {
                        if (Chat.canContinue()) {
                            Chat.completeChat()
                        }
                        Condition.wait { Chat.optionBarComponent().visible() }
                        val chatOption = Chat.optionBarComponent().components().firstOrNull { it.text() == option }
                        if (chatOption == null || !chatOption.click()) {
                            Input.press(Players.local().tile().matrix().nextPoint())
                            return
                        }
                        Condition.sleep(Random.nextInt(1000, 1500))
                        Condition.wait {
                            Chat.optionBarComponent().components()
                                .count { it.text().contains("Please wait", true) } == 0
                        }
                    }
                } else {
                    if (delrith.interact("Banish")) {
                        Condition.wait(Conditions.waitUntilChatting())
                    }
                }
            } else {

                if (Chat.chatting()) {
                    Chat.completeChat()
                } else {
                    killDelrith(delrith)
                }
            }
        }
    }

    private fun killDelrith(delrith: Npc) {
        // Fucking wizards
        val damNpcAttackingMe = Npcs.stream().interactingWithMe().first()
        if (damNpcAttackingMe != Npc.Nil) {
            if (Players.local().interacting() != damNpcAttackingMe) {
                attackNpc(damNpcAttackingMe)
            }
        } else {
            attackNpc(delrith)
        }
    }

    private fun attackNpc(npc: Npc) {
        if (!npc.inViewport()) {
            Camera.turnTo(npc)
        }
        if (npc.healthPercent() == 0) {
            Condition.wait(Conditions.waitUntilChatting())
        } else if (npc.interact("Attack")) {
            Condition.wait({
                CombatHelper.shouldEat(*information.foodName) || Chat.chatting()
            }, 300, 10)
        }
    }

    private fun setupIncantation() {
        if (incantation.isNotEmpty()) {
            return
        }

        incantation = arrayOf(
            DemonSlayerConstants.CONVERSATION_KILL_DELRITH[Varpbits.varpbit(
                DEMON_SLAYER.questVarbit,
                5,
                0x7
            )],
            DemonSlayerConstants.CONVERSATION_KILL_DELRITH[Varpbits.varpbit(
                DEMON_SLAYER.questVarbit,
                8,
                0x7
            )],
            DemonSlayerConstants.CONVERSATION_KILL_DELRITH[Varpbits.varpbit(
                DEMON_SLAYER.questVarbit,
                11,
                0x7
            )],
            DemonSlayerConstants.CONVERSATION_KILL_DELRITH[Varpbits.varpbit(
                DEMON_SLAYER.questVarbit,
                14,
                0x7
            )],
            DemonSlayerConstants.CONVERSATION_KILL_DELRITH[Varpbits.varpbit(
                DEMON_SLAYER.questVarbit,
                17,
                0x7
            )],
        )
        logger.info("Incantation is: ${incantation.joinToString(separator = ",")}")
    }

    override fun stepName(): String {
        return "Killing Delrith, hopefully"
    }
}