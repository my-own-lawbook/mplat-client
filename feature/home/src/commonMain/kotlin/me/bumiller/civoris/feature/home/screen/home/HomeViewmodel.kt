package me.bumiller.civoris.feature.home.screen.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.bumiller.civoris.common.ui.event.UiEvent
import me.bumiller.civoris.common.ui.viewmodel.CivorisViewModel
import me.bumiller.civoris.sync.SyncManager

/**
 * Viewmodel for the home screen.
 */
internal class HomeViewmodel(
    syncManager: SyncManager
) : CivorisViewModel<UiEvent, HomeEvent>() {

    init {
        val syncJobFlow = syncManager.scheduleSync()

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