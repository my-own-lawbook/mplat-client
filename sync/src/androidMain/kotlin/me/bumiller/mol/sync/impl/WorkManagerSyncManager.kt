package me.bumiller.mol.sync.impl

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.bumiller.mol.model.sync.SyncJobInfo
import me.bumiller.mol.model.sync.SyncResult
import me.bumiller.mol.sync.SyncManager
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

@OptIn(ExperimentalUuidApi::class)
internal class WorkManagerSyncManager(
    context: Context
) : SyncManager {

    private val workManager by lazy {
        WorkManager.getInstance(context)
    }

    override fun scheduleSync(identifier: Uuid): Flow<SyncJobInfo> {
        val workRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setId(identifier.toJavaUuid())
            .build()

        workManager.enqueue(workRequest)

        return workManager.getWorkInfoByIdFlow(identifier.toJavaUuid())
            .map { workInfo ->
                when (workInfo?.state) {
                    androidx.work.WorkInfo.State.ENQUEUED,
                    androidx.work.WorkInfo.State.BLOCKED -> SyncJobInfo.Scheduled

                    androidx.work.WorkInfo.State.RUNNING -> SyncJobInfo.Running
                    androidx.work.WorkInfo.State.SUCCEEDED -> mapOutputData(workInfo.outputData)
                    androidx.work.WorkInfo.State.FAILED -> mapOutputData(workInfo.outputData)
                    androidx.work.WorkInfo.State.CANCELLED -> SyncJobInfo.Cancelled
                    null -> throw IllegalStateException("Did not find a scheduled or for a uuid.")
                }
            }
    }

    private fun mapOutputData(data: Data): SyncJobInfo =
        data.getString(SyncWorker.KEY_OUTPUT_DATA)?.let {
            SyncJobInfo.Finished(SyncResult.valueOf(it))
        } ?: throw IllegalStateException("Worker finished successfully with no data")

    override fun stopSync(identifier: Uuid) {
        workManager.cancelWorkById(identifier.toJavaUuid())
    }

    override fun isSyncJobActive(): Flow<Boolean> = workManager
        .getWorkInfosByTagFlow(SYNC_TAG)
        .map { infos ->
            infos.any { it.state == WorkInfo.State.RUNNING }
        }

    companion object {

        private const val SYNC_TAG = "me.bumiller.mol.sync.work_tag"

    }

}