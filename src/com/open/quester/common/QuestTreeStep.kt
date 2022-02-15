package com.open.quester.common

import com.open.quester.common.base.BaseQuest
import com.open.quester.common.base.BaseQuestStep

/**
 * Root element of a Quest tree step
 * @param baseQuest Quest reference incase you need to do any special stuff.
 * @param stepName Text used to describe step you wish to display on the paint.
 */
abstract class QuestTree<Q : BaseQuest>(val baseQuest: Q, val stepName: String) : BaseQuestStep() {

    /**
     * {@inheritDoc}
     */
    override fun shouldExecute(): Boolean {
        return true
    }

    /**
     *  Gets the leaf element of the tree, duh.
     */
    abstract fun getLeaf(): QuestTree<Q>

    /**
     * {@inheritDoc}
     */
    override fun stepName(): String {
        return stepName
    }
}

/**
 *  A simple branch where it returns an element based on validation
 * @param baseQuest Quest reference incase you need to do any special stuff.
 * @param stepName Text used to describe step you wish to display on the paint.
 */
abstract class QuestBranchStep<Q : BaseQuest>(baseQuest: Q, stepName: String) : QuestTree<Q>(baseQuest, stepName) {

    /**
     *  The element which will be called when this trees validation is true
     */
    abstract val successElement: QuestTree<Q>

    /**
     *  The element which will be called when this trees validation is false
     */
    abstract val failedElement: QuestTree<Q>

    /**
     * {@inheritDoc}
     */
    abstract fun validate(): Boolean

    /**
     * {@inheritDoc}
     */
    override fun run() {}

    /**
     * {@inheritDoc}
     */
    override fun getLeaf(): QuestTree<Q> {
        return if (validate()) {
            logger.info("$stepName: Success, running ${successElement.stepName}")
            successElement.getLeaf()
        } else {
            logger.info("$stepName: Failure, running ${failedElement.stepName}")
            failedElement.getLeaf()
        }
    }
}

/**
 *  A simple leaf which executes a piece of code.
 * @param baseQuest Quest reference incase you need to do any special stuff.
 * @param stepName Text used to describe step you wish to display on the paint.
 */
abstract class QuestLeafStep<Q : BaseQuest>(baseQuest: Q, stepName: String) : QuestTree<Q>(baseQuest, stepName) {

    /**
     * {@inheritDoc}
     */
    override fun getLeaf(): QuestTree<Q> {
        return this
    }
}
