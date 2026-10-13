@file:Suppress("unused")

package net.shadowfacts.forgelin.coroutines

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

object ServerTaskQueue {

    internal val dispatcher = object : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            runOnServerThread(block)
        }
    }

    private val taskQueue = TickTaskQueue()

    private fun runOnServerThread(task: Runnable) {
        taskQueue.dispatch(task)
    }

    private fun processQueue() {
        taskQueue.process()
    }

    @SubscribeEvent
    fun onServerTick(event: ServerTickEvent) {
        if (event.phase == TickEvent.Phase.END) {
            processQueue()
        }
    }
}
