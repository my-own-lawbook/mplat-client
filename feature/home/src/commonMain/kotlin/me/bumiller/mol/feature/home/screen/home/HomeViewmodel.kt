package me.bumiller.mol.feature.home.screen.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.bumiller.mol.common.ui.event.UiEvent
import me.bumiller.mol.common.ui.viewmodel.MolViewModel
import me.bumiller.mol.sync.SyncManager
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Viewmodel for the home screen.
 */
@OptIn(ExperimentalUuidApi::class)
internal class HomeViewmodel(
    syncManager: SyncManager
) : MolViewModel<UiEvent, HomeEvent>() {

    init {
        val syncJobFlow = syncManager.scheduleSync(Uuid.random())

        viewModelScope.launch {
            syncJobFlow.collect { jobInfo ->
                setSyncJobInfo(jobInfo)
            }
        }

        viewModelScope.launch {
            syncManager.workerFailed().collect { failed ->
                if (failed) {
                    fireEvent(HomeEvent.SyncFailed)
                }
            }
        }
    }

    override suspend fun handleEvent(event: UiEvent): Nothing =
        throw Error("No ui event should be fired.")

}