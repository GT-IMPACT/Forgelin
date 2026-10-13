package net.shadowfacts.forgelin.coroutines

import java.util.concurrent.ConcurrentLinkedQueue

internal class TickTaskQueue {
    private val tasks = ConcurrentLinkedQueue<Runnable>()

    fun dispatch(task: Runnable) {
        tasks.add(task)
    }

    fun process() {
        // Tasks dispatched by a running coroutine (including yield) wait for the next tick.
        // Otherwise a coroutine can keep the game thread inside this loop forever.
        repeat(tasks.size) {
            val task = tasks.poll() ?: return
            task.run()
        }
    }
}
