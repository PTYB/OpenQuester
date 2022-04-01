/*
package com.open.quester.quest.daddyshome

import com.open.quester.quest.daddyshome.DaddysHomeConstants.FAR_STOOL_SHIFT
import com.open.quester.quest.daddyshome.DaddysHomeConstants.HOME_ITEM_MASK
import com.open.quester.quest.daddyshome.DaddysHomeConstants.SHIFT_QUEST_INDEX
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class DaddysHomeTests {

    @ParameterizedTest
    @MethodSource("questStates")
    fun questState(varpbit: Int, expectedState: Int) {
        val value = varpbit ushr SHIFT_QUEST_INDEX
        assertEquals(expectedState, value)
    }

    @Test
    fun testMaskRemoved() {
        val value = shiftValue(1922368, FAR_STOOL_SHIFT, HOME_ITEM_MASK)
        assertEquals(2, value)
    }

    private fun shiftValue(value: Int, shift: Int, mask: Int): Int {
        return value ushr shift and mask
    }

    companion object {
        @JvmStatic
        fun questStates(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(524288, 1),
                Arguments.of(1223328, 2),
                Arguments.of(1225408, 2),
                Arguments.of(1398080, 2),
                Arguments.of(1922368, 3),
                Arguments.of(2446656, 4),
                Arguments.of(2970944, 5)
            )
        }
    }
}*/
