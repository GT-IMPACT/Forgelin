@file:Suppress("unused")

package net.shadowfacts.forgelin.coroutines

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent
import kotlinx.coroutines.CoroutineDispatcher
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.coroutines.CoroutineContext

object ServerTaskQueue {

    internal val dispatcher = object : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            runOnServerThread(block)
        }
    }

    private val taskQueue = ConcurrentLinkedQueue<Runnable>()

    private fun runOnServerThread(task: Runnable) {
        taskQueue.add(task)
    }

    private fun processQueue() {
        while (true) {
            val task = taskQueue.poll() ?: break
            task.run()
        }
    }

    @SubscribeEvent
    fun onServerTick(event: ServerTickEvent) {
        if (event.phase == TickEvent.Phase.END) {
            processQueue()
        }
    }
}
