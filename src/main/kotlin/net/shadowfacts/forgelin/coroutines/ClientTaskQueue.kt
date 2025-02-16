@file:Suppress("unused")

package net.shadowfacts.forgelin.coroutines

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent
import kotlinx.coroutines.CoroutineDispatcher
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.coroutines.CoroutineContext

object ClientTaskQueue {

    internal val dispatcher = object : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            runOnClientThread(block)
        }
    }

    private val taskQueue = ConcurrentLinkedQueue<Runnable>()

    private fun runOnClientThread(task: Runnable) {
        taskQueue.add(task)
    }

    private fun processQueue() {
        while (true) {
            val task = taskQueue.poll() ?: break
            task.run()
        }
    }

    @SubscribeEvent
    fun onClientTick(event: ClientTickEvent) {
        if (event.phase == TickEvent.Phase.END) {
            processQueue()
        }
    }
}
