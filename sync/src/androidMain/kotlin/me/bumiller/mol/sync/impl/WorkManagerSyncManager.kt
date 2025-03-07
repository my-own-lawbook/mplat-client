package me.bumiller.mol.sync.impl

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.fasterxml.uuid.Generators
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import me.bumiller.mol.model.sync.SyncJobInfo
import me.bumiller.mol.model.sync.SyncResult
import me.bumiller.mol.sync.SyncManager

internal class WorkManagerSyncManager(
    context: Context
) : SyncManager {

    private val workManager by lazy {
        WorkManager.getInstance(context)
    }

    override fun scheduleSync(): Flow<SyncJobInfo> {
        val workRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .addTag(SYNC_TAG)
            .setId(Generators.timeBasedGenerator().generate())
            .build()

        workManager.enqueue(workRequest)

        return workManager.getWorkInfoByIdFlow(workRequest.id)
            .map { workInfo ->
                workInfo?.toSyncJobInfo()
                    ?: throw Error("Did not find the just recently started worker")
            }
    }

    private fun WorkInfo.toSyncJobInfo() = when (state) {
        WorkInfo.State.ENQUEUED,
        WorkInfo.State.BLOCKED -> SyncJobInfo.Scheduled

        WorkInfo.State.RUNNING -> SyncJobInfo.Running
        WorkInfo.State.SUCCEEDED -> mapOutputData(outputData)
        WorkInfo.State.FAILED -> mapOutputData(outputData)
        WorkInfo.State.CANCELLED -> SyncJobInfo.Cancelled
    }

    private fun mapOutputData(data: Data): SyncJobInfo =
        data.getString(SyncWorker.KEY_OUTPUT_DATA)?.let {
            SyncJobInfo.Finished(SyncResult.valueOf(it))
        } ?: throw IllegalStateException("Worker finished successfully with no data")

    private val workerFailedFlow = workManager
        .getWorkInfosByTagFlow(SYNC_TAG)
        .map { infos ->
            infos
                .maxByOrNull { it.id.timestamp() }
        }
        .filterNotNull()
        .map { info ->
            val jobInfo = info.toSyncJobInfo()

            jobInfo is SyncJobInfo.Finished && jobInfo.result.isFailed
        }

    override fun workerFailed(): Flow<Boolean> = workerFailedFlow

    override fun isSyncJobActive(): Flow<Boolean> = workManager
        .getWorkInfosByTagFlow(SYNC_TAG)
        .map { infos ->
            infos.any { it.state == WorkInfo.State.RUNNING }
        }

    companion object {

        private const val SYNC_TAG = "me.bumiller.mol.sync.work_tag"

    }

}