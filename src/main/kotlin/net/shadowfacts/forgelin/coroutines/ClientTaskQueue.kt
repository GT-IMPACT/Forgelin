@file:Suppress("unused")

package net.shadowfacts.forgelin.coroutines

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

object ClientTaskQueue {

    internal val dispatcher = object : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            runOnClientThread(block)
        }
    }

    private val taskQueue = TickTaskQueue()

    private fun runOnClientThread(task: Runnable) {
        taskQueue.dispatch(task)
    }

    private fun processQueue() {
        taskQueue.process()
    }

    @SubscribeEvent
    fun onClientTick(event: ClientTickEvent) {
        if (event.phase == TickEvent.Phase.END) {
            processQueue()
        }
    }
}
