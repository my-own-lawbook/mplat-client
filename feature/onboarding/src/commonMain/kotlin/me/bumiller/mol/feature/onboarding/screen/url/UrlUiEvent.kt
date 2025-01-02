package me.bumiller.mol.feature.onboarding.screen.url

import me.bumiller.mol.common.ui.event.UiEvent
import me.bumiller.mol.common.ui.event.ViewModelEvent

/**
 * Events produced by the [UrlScreen].
 */
sealed interface UrlUiEvent : UiEvent {

    /**
     * Fired when the user changed the url.
     *
     * @param input The new url
     */
    data class ChangeUrl(val input: String) : UrlUiEvent

    /**
     * Fired when the user confirmed the url.
     */
    data object Confirm : UrlUiEvent

}

/**
 * Events produced by the [UrlViewModel]
 */
sealed interface UrlEvent : ViewModelEvent {

    /**
     * The next screen can be travelled to.
     */
    data object Continue : UrlEvent

}