package com.open.quester.extensions

import org.powbot.api.rt4.GameObject
import org.powbot.api.rt4.Objects

fun Objects.nearestGameObject(vararg name: String): GameObject {
    return stream().name(*name).nearest().first()
}

fun Objects.nearestGameObject(name: String, type: GameObject.Type): GameObject {
    return stream().type(type).name(name).nearest().first()
}

fun Objects.nearestGameObject(vararg ids: Int): GameObject {
    return stream().id(*ids).nearest().first()
}