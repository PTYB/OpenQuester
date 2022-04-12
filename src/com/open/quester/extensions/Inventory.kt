package com.open.quester.extensions

import org.powbot.api.rt4.Inventory

fun Inventory.count(vararg name: String): Int {
    return stream().name(*name).count(true).toInt()
}

fun Inventory.count(vararg id: Int): Int {
    return stream().id(*id).count(true).toInt()
}