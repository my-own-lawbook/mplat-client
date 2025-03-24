package me.bumiller.mol.feature.auth.screen

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Class that manages executing a task in fixed time intervals.
 */
internal class TaskScheduler(

    /**
     * Millis delay between executions.
     */
    private val delayMillis: Long,

    /**
     * The scope to launch the task in.
     */
    private val scope: CoroutineScope,

    /**
     * The task to execute.
     */
    private val task: suspend () -> Unit
) {

    private var trigger = Channel<Unit>(Channel.UNLIMITED)

    private var isActive = true

    private lateinit var job: Job

    /**
     * Starts the scheduling.
     */
    fun start() {
        job = scope.launch {
            while (isActive) {
                task()
                while (trigger.tryReceive().isSuccess) {
                    if (!isActive) break
                    task()
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
     */
    fun schedule() {
        trigger.trySend(Unit)
    }

}