/*
package com.open.quester.quest.xmarksthespot

import com.open.quester.quest.xmarksthespot.XMarksTheSpotConstants.VARPBIT_STATE_MASK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class XMarksTheSpotTests {

    @ParameterizedTest
    @MethodSource("questStates")
    fun questState(varpbit: Int, expectedState: Int) {
        val value = shiftValue(varpbit, 0, VARPBIT_STATE_MASK)
        assertEquals(expectedState, value)
    }

    private fun shiftValue(value: Int, shift: Int, mask: Int): Int {
        return value ushr shift and mask
    }

    companion object {
        @JvmStatic
        fun questStates(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(583008257, 1),
                Arguments.of(583008258, 2),
                Arguments.of(583008259, 3),
                Arguments.of(583008260, 4),
                Arguments.of(583008261, 5),
                Arguments.of(46137350, 6),
                Arguments.of(46137351, 7),
                Arguments.of(48758792, 8),
            )
        }
    }
}*/
