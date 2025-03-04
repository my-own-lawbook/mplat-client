package me.bumiller.mol.common.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.bumiller.mol.common.ui.event.ViewModelEvent
import me.bumiller.mol.model.state.SimpleState
import me.bumiller.mol.model.sync.SyncJobInfo
import kotlin.reflect.KClass

/**
 * Base class for any view model.
 */
abstract class MolViewModel<UiEvent : me.bumiller.mol.common.ui.event.UiEvent, Event : ViewModelEvent> :
    ViewModel() {

    //
    // Events
    //

    /**
     * Handles the event passed by the ui.
     */
    protected abstract suspend fun handleEvent(event: UiEvent)

    /**
     * Will queue the event to be handled by the view model.
     *
     * @param uiEvent The event from the ui.
     */
    fun onEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            handleEvent(uiEvent)
        }
    }

    private val _events = MutableSharedFlow<Event>()

    /**
     * The events fired by the view model.
     */
    val events = _events.asSharedFlow()

    /**
     * Fired an event into the view model events flow.
     *
     * @param event The event to fire
     */
    protected suspend fun fireEvent(event: Event) {
        _events.emit(event)
    }

    //
    // State configuring for view models
    //

    /**
     * List of all current ui stated registered.
     */
    protected val uiStates = hashMapOf<Pair<KClass<*>, *>, MutableStateFlow<*>>()

    /**
     * Registers a new ui state.
     *
     * @param initialValue The initial value of the newly registered ui state.
     * @param key The key used to uniquely associate the type with the state flow, if multiple states of the same type are present. [Unit] is disallowed.
     * @param Data The type of which to create a new ui state for
     * @return The newly created ui state flow
     */
    protected inline fun <reified Data : Any?> registerUiState(
        initialValue: Data,
        key: Any
    ): MutableStateFlow<Data> {
        val stateFlow = MutableStateFlow(initialValue)

        val dataClass = Data::class
        uiStates[Pair(dataClass, key)] = stateFlow

        return stateFlow
    }

    /**
     * Registers a new ui state.
     *
     * @param initialValue The initial value of the newly registered ui state.
     * @param Data The type of which to create a new ui state for
     * @return The newly created ui state flow
     */
    protected inline fun <reified Data : Any> registerUiState(initialValue: Data): MutableStateFlow<Data> =
        registerUiState(initialValue, Unit)

    /**
     * Retrieves the ui state of the specified type and key.
     *
     * @param key The key used to uniquely identify the state flow, if multiple state flows of the same type exist.
     */
    @Suppress("UNCHECKED_CAST")
    protected inline fun <reified Data : Any> uiState(key: Any): MutableStateFlow<Data> {
        val dataClass = Data::class
        val stateFlow = uiStates[Pair(dataClass, key)]

        return stateFlow as MutableStateFlow<Data>
    }

    /**
     * Retrieves the ui state of the specified type.
     */
    protected inline fun <reified Data : Any> uiState(): MutableStateFlow<Data> =
        uiState<Data>(Unit)


    /**
     * Updates the ui state of specified type with the given block.
     *
     * @param block The lambda creating the new element
     */
    protected inline fun <reified Data : Any> updateUiState(block: (Data) -> Data) {
        val stateFlow = uiState<Data>()

        stateFlow.update(block)
    }

    //
    // Shared state for all view models
    //

    private data object Fetching
    private data object NetworkError
    private data object UnknownError
    private data object SyncInfo

    init {
        registerUiState<Boolean>(false, Fetching)
        registerUiState<Boolean>(false, NetworkError)
        registerUiState<Boolean>(false, UnknownError)
        registerUiState<SyncJobInfo?>(null, SyncInfo)
    }

    private val _isFetching = uiState<Boolean>(Fetching)

    /**
     * Sets the new value of the isFetching state.
     *
     * @param isFetching The new value
     */
    fun setIsFetching(isFetching: Boolean = true) {
        viewModelScope.launch { _isFetching.emit(isFetching) }
    }

    /**
     * Whether the viewmodel was set into a fetching state, i.e. some processing work is being made in the background.
     */
    val isFetching = _isFetching.asStateFlow()

    private val _hasNetworkError = uiState<Boolean>(NetworkError)

    /**
     * Sets the new value of the hasNetworkError state.
     *
     * @param hasNetworkError The new value
     */
    fun setHasNetworkError(hasNetworkError: Boolean = true) {
        viewModelScope.launch { _hasNetworkError.emit(hasNetworkError) }
    }

    /**
     * Whether a request failed because the server could not be reached.
     */
    val hasNetworkError = _hasNetworkError.asStateFlow()

    private val _hasUnknownError = uiState<Boolean>(UnknownError)

    /**
     * Sets the new value of the hasUnknownError state.
     *
     * @param hasUnknownError The new value
     */
    fun setHasUnknownError(hasUnknownError: Boolean = true) {
        viewModelScope.launch { _hasUnknownError.emit(hasUnknownError) }
    }

    /**
     * Whether a request failed because of an unknown error.
     */
    val hasUnknownError = _hasUnknownError.asStateFlow()

    /**
     * The info about the latest executed sync job, or null.
     */
    val syncJobInfo = uiState<SyncJobInfo>(SyncInfo).asStateFlow()

    /**
     * Sets the current sync job info.
     *
     * @param syncJobInfo The new sync job info
     */
    fun setSyncJobInfo(syncJobInfo: SyncJobInfo) {
        uiState<SyncJobInfo>(SyncInfo).update { syncJobInfo }
    }

    /**
     * Sets the [isFetching] value to true while a suspend block is executed.
     *
     * @param block The block to be executed
     * @return The result value of the block
     */
    protected inline fun <T> withFetchState(block: () -> T): T {
        setIsFetching()
        val result = block()
        setIsFetching(false)

        return result
    }

    /**
     * Clears [hasNetworkError] and [hasUnknownError] by setting them to false.
     */
    protected fun clearErrors() {
        setHasNetworkError(false)
        setHasUnknownError(false)
    }

    //
    // Stateflow utilities
    //

    /**
     * Extension function to easily create a stateflow from an initial value.
     *
     * @param initial The initial value of the stateflow
     * @return A stateflow, with the initial value [initial]
     */
    private fun <T> Flow<T>.stateIn(initial: T) =
        stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = initial
        )

    /**
     * Extension function to easily create a stateflow from a flow, whose initial value is a loading [SimpleState].
     *
     * @return A stateflow, with the initial value being an instance of [SimpleState.Loading]
     */
    fun <Data, State : SimpleState<Data>> Flow<State>.loadingStateIn() =
        stateIn(SimpleState.loading())

}