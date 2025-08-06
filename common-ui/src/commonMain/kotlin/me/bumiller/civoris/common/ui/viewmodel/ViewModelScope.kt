package me.bumiller.civoris.common.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import me.bumiller.civoris.common.ui.event.UiEvent
import me.bumiller.civoris.common.ui.event.ViewModelEvent
import me.bumiller.civoris.model.sync.SyncJobInfo
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

/**
 * Scope which contains information about the state of the current view model.
 */
data class ViewModelScope(

    /**
     * Whether the view model is doing background work.
     */
    val isFetching: Boolean,

    /**
     * Whether a request failed due to not reaching the server.
     */
    val hasNetworkError: Boolean,

    /**
     * Whether a request failed due to an unknown error.
     */
    val hasUnknownError: Boolean,

    /**
     * Contains the latest sync job info.
     *
     * Null if no sync has yet been started
     */
    val syncJobInfo: SyncJobInfo?

) {

    /**
     * Combines the current scope with one that lives a hierarchy above it.
     */
    fun mergeWithUpper(scope: ViewModelScope): ViewModelScope =
        ViewModelScope(
            isFetching || scope.isFetching,
            hasNetworkError || scope.hasNetworkError,
            hasUnknownError || scope.hasUnknownError,
            syncJobInfo ?: scope.syncJobInfo
        )

}

/**
 * Composition local holding reference to the current [ViewModelScope].
 */
val LocalViewModelScope = staticCompositionLocalOf {
    ViewModelScope(isFetching = false, hasNetworkError = false, hasUnknownError = false, null)
}

/**
 * A composable which is supposed to wrap a composable that acts as a screen, and is controlled by a [CivorisViewModel].
 */
@OptIn(KoinExperimentalAPI::class)
@Composable
inline fun <ScreenEvent : UiEvent, Event : ViewModelEvent, reified ViewModel : CivorisViewModel<ScreenEvent, Event>> ViewModelScope(
    noinline onViewModelEvent: suspend (Event) -> Unit,
    crossinline content: @Composable ViewModelScope.(ViewModel) -> Unit
) {
    val viewModel = koinViewModel<ViewModel>()

    LaunchedEffect(Unit) {
        viewModel.events
            .collectLatest(onViewModelEvent)
    }

    val isFetching by viewModel.isFetching.collectAsStateWithLifecycle()
    val hasNetworkError by viewModel.hasNetworkError.collectAsStateWithLifecycle()
    val hasUnknownError by viewModel.hasUnknownError.collectAsStateWithLifecycle()
    val syncJobInfo by viewModel.syncJobInfo.collectAsStateWithLifecycle()

    val scope = ViewModelScope(isFetching, hasNetworkError, hasUnknownError, syncJobInfo)

    val mergedScope = scope.mergeWithUpper(LocalViewModelScope.current)

    CompositionLocalProvider(
        LocalViewModelScope provides mergedScope
    ) {
        scope.content(viewModel)
    }
}