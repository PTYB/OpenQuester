package com.open.quester.common.base

import org.powbot.api.event.InventoryChangeEvent
import org.powbot.api.event.MessageEvent
import org.powbot.mobile.drawing.Rendering
import java.util.logging.Logger

abstract class BaseQuestStep {
    protected var logger: Logger = Logger.getLogger(this.javaClass.simpleName)

    init {
        init()
    }

    /**
     * @return Returns whether a step should execute or not
     */
    abstract fun shouldExecute(): Boolean

    /**
     * Executes the step if possible
     *
     * @return true when the task was executed
     */
    fun execute(): Boolean {
        if (shouldExecute()) {
            run()
            return true
        }
        return false
    }

    /**
     * Main method to run for the script
     *
     * @return result status of running this step.
     */
    abstract fun run()

    /**
     * Do things required to be done on initialization
     */
    protected fun init() {
    }

    /**
     * @return Text used to describe step you wish to display on the paint.
     */
    abstract fun stepName(): String

    open fun draw(g: Rendering) {

    }

    open fun messageRecieved(me: MessageEvent) {

    }

    open fun inventoryChanged(me: InventoryChangeEvent) {

    }
}