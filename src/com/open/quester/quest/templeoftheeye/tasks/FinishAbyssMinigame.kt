package com.open.quester.quest.templeoftheeye.tasks

import com.open.quester.common.base.BaseQuestStep
import com.open.quester.helpers.MessageListener
import com.open.quester.helpers.SystemMessageManager.addMessageToListen
import com.open.quester.quest.templeoftheeye.TempleOfTheEyeConstants.ALL_ENERGY
import com.open.quester.quest.templeoftheeye.TempleOfTheEyeConstants.MESSAGE_FAILURE
import com.open.quester.quest.templeoftheeye.TempleOfTheEyeConstants.MESSAGE_SUCCESS
import org.powbot.api.Condition
import org.powbot.api.rt4.Camera
import org.powbot.api.rt4.GameObject
import org.powbot.api.rt4.GameObject.*
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players

class FinishAbyssMinigame : BaseQuestStep() {

    private var listToInteract = ALL_ENERGY.toMutableList()
    private var order = mutableListOf<String>()

    override fun shouldExecute(): Boolean {
        return true
    }

    override fun run() {
        val allEnergies = Objects.stream(20, Type.INTERACTIVE).name(*ALL_ENERGY).action("Touch").toList()
        val currentPos = 6 - allEnergies.size
        logger.info("Known list ${order.joinToString(",")}")
        logger.info("Interact list ${listToInteract.joinToString(",")}")

        if (order.size > currentPos) {
            interactWithEnergy(currentPos, allEnergies, false)
        } else {
            interactWithUnknown(allEnergies)
        }
    }

    private fun interactWithUnknown(energies: List<GameObject>) {
        val playerLoc = Players.local()
        val targetEnergy = energies.filter { listToInteract.contains(it.name) }
            .minByOrNull { it.tile.distanceTo(playerLoc) } ?: return
        interactWithEnergy(targetEnergy, true)
    }

    private fun interactWithEnergy(pos: Int, energies: List<GameObject>, addOnSuccess: Boolean) {
        val energy = energies.firstOrNull { it.name == order[pos] } ?: return

        if (!energy.inViewport()) {
            Camera.turnTo(energy)
        }

        if (energy.inViewport()) {
            interactWithEnergy(energy, addOnSuccess)
        }
    }

    private fun interactWithEnergy(energy: GameObject, addOnSuccess: Boolean) {
        val success = MessageListener(1, arrayOf(MESSAGE_SUCCESS), 10000)
        val failure = MessageListener(1, arrayOf(MESSAGE_FAILURE), 10000)
        addMessageToListen(success)
        addMessageToListen(failure)
        if (energy.interact("Touch")) {
            val result = Condition.wait({ success.count == 0 || failure.count == 0 }, 1000, 11)
            if (!result) {
                logger.info("Ahh fuck failed to interact please dont mess up. ")
                return
            }
            if (success.count == 0) {
                if (addOnSuccess) {
                    logger.info("Adding energy ${energy.name}")
                    order.add(energy.name)
                    resetEnergyList()
                }
            } else if (failure.count == 0) {
                logger.info("Failed ${energy.name}, removing from list to process")
                listToInteract.remove(energy.name)
            }
        }
    }

    private fun resetEnergyList() {
        listToInteract = ALL_ENERGY.filter { !order.contains(it) }.toMutableList()
    }

    override fun stepName(): String {
        return "Completing puzzle"
    }
}