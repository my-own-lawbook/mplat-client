package me.bumiller.mol.feature.home.screen.home

import me.bumiller.mol.common.ui.event.ViewModelEvent

/**
 * Events fired by the home view model.
 */
internal interface HomeEvent : ViewModelEvent {

    /**
     * Fired when the synchronization failed.
     */
    data object SyncFailed : HomeEvent

}