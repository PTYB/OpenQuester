package com.open.quester.quest.gertrudescat.tasks

import com.open.quester.common.base.WalkToInteractiveStep
import com.open.quester.models.QuestInformation
import com.open.quester.quest.gertrudescat.GertrudesCatConstants.ITEM_KITTEN
import com.open.quester.quest.gertrudescat.GertrudesCatConstants.TILE_CENTER_LUMBERMILL
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.event.MessageEvent
import org.powbot.api.rt4.*
import java.util.concurrent.CountDownLatch

class GetKitten(questInformation: QuestInformation) : WalkToInteractiveStep<Npc>(
    TILE_CENTER_LUMBERMILL,
    arrayOf(), false, questInformation
) {

    private val ignoreCrates = mutableListOf<Tile>()
    private var check: TileCheck? = null

    override fun shouldExecute(): Boolean {
        return Inventory.stream().name(ITEM_KITTEN).isEmpty()
    }

    private fun getCatNpc(): Npc {
        return Npcs.stream().name("Crate").filtered {
            !ignoreCrates.any { ic -> it.x() == ic.x && it.y() == ic.y }
        }.nearest().first()
    }

    override fun messageRecieved(me: MessageEvent) {
        if (me.sender.isNotEmpty()) {
            return
        }

        val currentCheck = check ?: return

        if (me.message.contains("You find nothing")) {
            ignoreCrates.add(currentCheck.tile)
            currentCheck.latch.countDown()
        }
    }

    override fun stepName(): String {
        return "Finding cat"
    }

    override fun getInteractive(): Npc {
        return getCatNpc()
    }

    override fun interact(npc: Npc): Boolean {
        var checkIfFound = false
        if (npc.interact("Search")) {
            check = TileCheck(CountDownLatch(1), npc.tile())
            val latch = check!!.latch
            Condition.wait { Players.local().inMotion() || latch.count == 0L }
            Condition.wait { !Players.local().inMotion() || latch.count == 0L }

            checkIfFound = Condition.wait({ latch.count == 0L || Chat.chatting() }, 1000, 8)
            logger.info("Found cat result $checkIfFound")
            check = null
        }
        return checkIfFound
    }

    override fun getInteractiveTile(interactive: Npc): Tile {
        return interactive.tile()
    }
}

data class TileCheck(val latch: CountDownLatch, val tile: Tile)