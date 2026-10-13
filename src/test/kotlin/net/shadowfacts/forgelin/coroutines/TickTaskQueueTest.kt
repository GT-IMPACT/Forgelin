package net.shadowfacts.forgelin.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.coroutines.CoroutineContext

class TickTaskQueueTest {
    @Test(timeout = 5000)
    fun yieldingCoroutineWaitsForNextTick() {
        val queue = TickTaskQueue()
        val dispatcher = object : CoroutineDispatcher() {
            override fun dispatch(context: CoroutineContext, block: Runnable) = queue.dispatch(block)
        }
        var steps = 0
        val job = CoroutineScope(dispatcher).launch {
            repeat(3) {
                steps++
                yield()
            }
        }
        repeat(3) {
            queue.process()
            assertEquals(it + 1, steps)
        }
        queue.process()
        assertTrue(job.isCompleted)
    }

    @Test
    fun cancelledCoroutineDoesNotExecuteQueuedBody() {
        val queue = TickTaskQueue()
        val dispatcher = object : CoroutineDispatcher() {
            override fun dispatch(context: CoroutineContext, block: Runnable) = queue.dispatch(block)
        }
        var executed = false
        val parent = Job()
        val job = CoroutineScope(dispatcher + parent).launch { executed = true }
        parent.cancel()
        queue.process()
        assertTrue(job.isCancelled)
        assertEquals(false, executed)
    }

    @Test
    fun tasksRunInSubmissionOrder() {
        val queue = TickTaskQueue()
        val results = mutableListOf<Int>()
        repeat(3) { index -> queue.dispatch(Runnable { results.add(index) }) }
        queue.process()
        assertEquals(listOf(0, 1, 2), results)
        queue.process()
        assertEquals(3, results.size)
    }
}
