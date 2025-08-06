package me.bumiller.civoris.feature.auth.screen

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.milliseconds

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

    private lateinit var job: Job

    private lateinit var start: Instant

    /**
     * Starts the scheduling.
     */
    fun start() {
        job = scope.launch {
            while (isActive) {
                task(defaultArg)

                start = Clock.System.now()
                while (Clock.System.now() < start + delayMillis.milliseconds) {
                    var result = trigger.tryReceive().getOrNull()
                    while (result != null) {
                        if (!isActive) break
                        task(result)
                        result = trigger.tryReceive().getOrNull()
                    }
                    delay(100)
                }
            }
        }
    }

    /**
     * Stops the execution by not scheduling any more.
     */
    fun stop() {
        job.cancel()
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