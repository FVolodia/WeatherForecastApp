package com.weatherforecastapp.utils

import com.weatherforecastapp.utils.DispatcherHolder.BG
import com.weatherforecastapp.utils.DispatcherHolder.UI
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Hold various implementations of [Dispatchers] to launch coroutines in main and background
 */
object DispatcherHolder {

  val UI = Dispatchers.Main

  val BG = Dispatchers.IO
}

/**
 * Default implementation of [CoroutineExceptionHandler] to print stack stace
 */
fun coolHandler() = CoroutineExceptionHandler { _, throwable ->
  throwable.printStackTrace()
}

/**
 * Create [CoroutineContext] with [CoroutineExceptionHandler]
 *
 * @param job Prefer to use SupervisorJob, because
 *            children of a supervisor job can fail independently of each other
 */
fun mainContext(job: Job): CoroutineContext = UI + job + coolHandler()

fun bgContext(job: Job): CoroutineContext = BG + job + coolHandler()

/**
 * Invoke block in UI context
 */
suspend fun <T> onUI(block: suspend CoroutineScope.() -> T) = withContext(UI, block)

/**
 * Invoke block in BG context
 */
suspend fun <T> onBG(block: suspend CoroutineScope.() -> T) = withContext(BG, block)
