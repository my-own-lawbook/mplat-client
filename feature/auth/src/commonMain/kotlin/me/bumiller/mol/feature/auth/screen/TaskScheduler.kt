package me.bumiller.mol.feature.auth.screen

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Class that manages executing a task in fixed time intervals.
 */
internal class TaskScheduler<Data>(

    /**
     * Millis delay between executions.
     */
    private val delayMillis: Long,

    /**
     * The scope to launch the task in.
     */
    private val scope: CoroutineScope,

    /**
     * The default argument given to the execution.
     */
    private val defaultArg: Data,

    /**
     * The task to execute.
     */
    private val task: suspend (Data) -> Unit
) {

    private var trigger = Channel<Data>(Channel.UNLIMITED)

    private var isActive = true

    private lateinit var job: Job

    /**
     * Starts the scheduling.
     */
    fun start() {
        job = scope.launch {
            while (isActive) {
                task(defaultArg)

                var result = trigger.tryReceive().getOrNull()
                while (result != null) {
                    if (!isActive) break
                    task(result)
                    result = trigger.tryReceive().getOrNull()
                }

                delay(delayMillis)
            }
        }
    }

    /**
     * Stops the execution by not scheduling any more and suspends until the currently running execution has stopped.
     */
    suspend fun stop() {
        isActive = false
        job.join()
    }

    /**
     * Schedules a new task execution.
     *
     * @param data The data passed to the task
     */
    fun schedule(data: Data) {
        trigger.trySend(data)
    }

}