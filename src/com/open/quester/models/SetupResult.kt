package com.open.quester.models

enum class SetupResult {
    /**
     *  Undetermined yet
     */
    UNKNOWN,

    /**
     *  Remaining items need to be acquired
     */
    INCOMPLETE,

    /**
     *  All items accounted for
     */
    COMPLETE,

    /**
     *  Error and no longer able to proceed
     */
    FAILURE
}