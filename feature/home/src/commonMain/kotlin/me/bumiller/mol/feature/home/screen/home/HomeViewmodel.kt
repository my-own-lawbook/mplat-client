package me.bumiller.mol.feature.home.screen.home

import me.bumiller.mol.common.ui.event.UiEvent
import me.bumiller.mol.common.ui.event.ViewModelEvent
import me.bumiller.mol.common.ui.viewmodel.MolViewModel

/**
 * Viewmodel for the home screen.
 */
internal class HomeViewmodel : MolViewModel<UiEvent, ViewModelEvent>() {

    override suspend fun handleEvent(event: UiEvent): Nothing =
        throw Error("No ui event should be fired.")

}