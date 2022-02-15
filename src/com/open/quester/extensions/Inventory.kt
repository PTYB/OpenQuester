package com.open.quester.extensions

import org.powbot.api.rt4.Inventory

fun Inventory.count(vararg name: String): Int {
    return stream().name(*name).count(true).toInt()
}