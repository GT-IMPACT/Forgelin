@file:Suppress("unused")

package net.shadowfacts.forgelin.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

val Dispatchers.Server: CoroutineDispatcher get() = ServerTaskQueue.dispatcher

val Dispatchers.Client: CoroutineDispatcher get() = ClientTaskQueue.dispatcher
