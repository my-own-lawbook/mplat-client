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
import me.bumiller.mol.common.ui.event.UiEvent
import me.bumiller.mol.common.ui.event.ViewModelEvent
import me.bumiller.mol.model.state.SimpleState
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
    abstract suspend fun handleEvent(event: UiEvent)

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
    protected suspend fun fireEvent(event: Event) = _events.emit(event)

    //
    // State configuring for view models
    //

    protected val uiStates = hashMapOf<Pair<KClass<*>, *>, MutableStateFlow<*>>()

    /**
     * Registers a new ui state.
     *
     * @param initialValue The initial value of the newly registered ui state.
     * @param key The key used to uniquely associate the type with the state flow, if multiple states of the same type are present. [Unit] is disallowed.
     * @param Data The type of which to create a new ui state for
     * @return The newly created ui state flow
     */
    protected inline fun <reified Data : Any> registerUiState(
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
    @Suppress("UNCHECKED_CAST")
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

    private val _isFetching = MutableStateFlow(false)

    /**
     * Whether the viewmodel was set into a fetching state, i.e. some processing work is being made in the background.
     */
    val isFetching = _isFetching.asStateFlow()

    /**
     * Emits a new value to the [isFetching] flow.
     *
     * @param isFetching The to be emitted value
     */
    protected fun setIsFetching(isFetching: Boolean) {
        viewModelScope.launch {
            _isFetching.emit(isFetching)
        }
    }

    /**
     * Sets the [isFetching] value to true while a suspend block is executed.
     *
     * @param block The block to be executed
     * @return The result value of the block
     */
    protected suspend fun <T> withFetchState(block: suspend () -> T): T {
        setIsFetching(true)
        val result = block()
        setIsFetching(false)

        return result
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
    fun <T> Flow<T>.stateIn(initial: T) =
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