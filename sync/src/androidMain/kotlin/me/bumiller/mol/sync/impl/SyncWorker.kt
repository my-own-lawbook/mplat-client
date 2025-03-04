package me.bumiller.mol.sync.impl

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import me.bumiller.mol.sync.SyncAdapter
import me.bumiller.mol.sync.model.SyncResult

internal class SyncWorker(
    context: Context,
    params: WorkerParameters,
    private val syncAdapter: SyncAdapter
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val result = syncAdapter.performSync()

        val data = Data.Builder()
            .putString(KEY_OUTPUT_DATA, result.name)
            .build()

        return when (result) {
            SyncResult.Success -> Result.success(data)
            else -> Result.failure(data)
        }
    }

    companion object {

        internal const val KEY_OUTPUT_DATA = "me.bumiller.mol.sync.impl.SyncWorker.key_output_data"

    }

}