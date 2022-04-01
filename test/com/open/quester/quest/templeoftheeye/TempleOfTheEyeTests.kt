
package com.open.quester.quest.templeoftheeye

import com.open.quester.quest.templeoftheeye.TempleOfTheEyeConstants.VARPBIT_SHIFT_FELIX_INGAME
import com.open.quester.quest.templeoftheeye.TempleOfTheEyeConstants.VARPBIT_SHIFT_TAMARA
import com.open.quester.quest.templeoftheeye.TempleOfTheEyeConstants.VARPBIT_SHIFT_TAMARA_INGAME
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TempleOfTheEyeTests {

    @Test
    fun questState() {
        val value = shiftValue(536976971, VARPBIT_SHIFT_TAMARA, 0x1)
        assertEquals(1, value)
    }

    @Test
    fun talkedToFelixIngame() {
        val value = shiftValue(-1610482076, VARPBIT_SHIFT_TAMARA_INGAME, 0x1)
        val felixValue = shiftValue(-1610482076, VARPBIT_SHIFT_FELIX_INGAME, 0x1)
        assertEquals(0, value)
        assertEquals(1, felixValue)
    }

    @Test
    fun talkedToTamaraIngame() {
        val value = shiftValue(-536740252, VARPBIT_SHIFT_TAMARA_INGAME, 0x1)
        val felixValue = shiftValue(-536740252, VARPBIT_SHIFT_FELIX_INGAME, 0x1)

        assertEquals(1, value)
        assertEquals(1, felixValue)
    }

    private fun shiftValue(value: Int, shift: Int, mask: Int): Int {
        return value ushr shift and mask
    }

}
