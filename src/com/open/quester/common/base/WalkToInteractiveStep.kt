package com.open.quester.common.base

import com.open.quester.common.CommonMethods
import com.open.quester.helpers.CombatHelper
import com.open.quester.models.QuestInformation
import org.powbot.api.Condition
import org.powbot.api.Point
import org.powbot.api.Tile
import org.powbot.api.rt4.*
import org.powbot.api.rt4.walking.WebWalkingResult
import java.util.logging.Level

/**
 *  @param noInteractableTile Used as a destination tile when no interactive is located nearby
 *  @param conversation Used to finish dialogues, will auto detect click here to continue. Null means ignore conversations
 *  @param forceWeb Forces web where possible
 */
abstract class WalkToInteractiveStep<T : Interactive>(
    protected open val noInteractableTile: Tile,
    var conversation: Array<String>? = arrayOf(),
    val forceWeb: Boolean = false,
    protected val questInformation: QuestInformation
) : BaseQuestStep() {

    /**
     *  This is used to determine reachability, can be helpful to for things such as checking if you want to talk to a
     *  certain NPC through a wall or another object like that.
     */
    var pointVariance = Point(0, 0)
    protected var extraFood: Array<String> = arrayOf()

    /**
     * {@inheritDoc}
     */
    abstract override fun shouldExecute(): Boolean

    /**
     * {@inheritDoc}
     */
    override fun run() {
        if (Bank.opened()) {
            logger.info("Closing bank since its open")
            Bank.close()
        } else if (Store.opened()) {
            logger.info("Closing store since its open")
            Store.close()
        }

        if ((conversation != null && Chat.chatting() && !Chat.pendingInput()) || CommonMethods.isInCutscene()) {
            logger.info("Completing chat")
            completeChat()
        } else {
            val interactive = getInteractive()
            if (interactive.valid() && interactive.inViewport(true)) {
                val interactiveTile = getInteractiveTile(interactive).derive(pointVariance.x, pointVariance.y)
                if (interactive.inViewport() && interactiveTile.reachable()) {
                    interact(interactive)
                } else {
                    logger.log(Level.INFO, "Walking to interactive tile $interactiveTile")
                    walkToTile(interactiveTile)
                }
            } else {
                logger.log(Level.INFO, "No interactive detected.")
                walkToTile(noInteractableTile)
            }
        }
    }

    /**
     *  Completes the chat for the cutscene, will press current tile if prompted for dialogue for which it has no answer.
     */
    private fun completeChat() {
        val component = Chat.optionBarComponent()
        if (component.visible() && component.components()
                .none {
                    logger.info("Text ${it.text()}")
                    conversation!!.contains(it.text()) }
        ) {
            if (Players.local().tile().matrix().interact("Walk here")) {
                Condition.wait { !Chat.chatting() }
            }
        } else {
            logger.info("Completing conversation/cutscene, options ${conversation?.joinToString(",")}")
            Chat.completeChat(*conversation!!)
            if (CommonMethods.isInCutscene()) {
                Condition.wait({ Chat.chatting() || !CommonMethods.isInCutscene() }, 150, 25)
            }
        }
    }

    /**
     *  Gets the interactive instance
     */
    protected abstract fun getInteractive(): T

    /**
     *  Interacts and validates the condition to verify interaction was successful
     *  @param interactive Instance retrieved from getInteractive()
     *  @see  #getInteractive()
     */
    protected abstract fun interact(interactive: T): Boolean

    /**
     *  Gets the interactive objects tile.
     */
    protected abstract fun getInteractiveTile(interactive: T): Tile

    protected open fun walkToTile(tile: Tile): WebWalkingResult {
        logger.info("Walking to objective")
        return Movement.builder(tile)
            .setRunMin(20)
            .setRunMax(50)
            .setWalkUntil {
                if (CombatHelper.shouldEat(*questInformation.foodName, *extraFood)) {
                    CombatHelper.eatFood(*questInformation.foodName, *extraFood)
                }

                val interactive = getInteractive()
                interactive.valid() &&
                        interactive.inViewport(true) &&
                        getInteractiveTile(interactive).derive(pointVariance.x, pointVariance.y).reachable()
            }
            .setForceWeb(forceWeb)
            .move()
    }
}