package me.bumiller.civoris.feature.onboarding.screen.design

import me.bumiller.civoris.common.ui.event.UiEvent
import me.bumiller.civoris.common.ui.event.ViewModelEvent
import me.bumiller.civoris.model.settings.ColorMode
import me.bumiller.civoris.model.settings.ColorScheme
import me.bumiller.civoris.model.settings.ColorSchemeContrastLevel

/**
 * Events fired by the ui of the design screen.
 */
sealed interface DesignUiEvent : UiEvent {

    /**
     * User changed the color mode value.
     *
     * @param input The new color mode value
     */
    data class ChangeMode(val input: ColorMode) : DesignUiEvent

    /**
     * User changed the color scheme value.
     *
     * @param input The new color scheme value
     */
    data class ChangeScheme(val input: ColorScheme) : DesignUiEvent

    /**
     * User changed the contrast level value.
     *
     * @param input The new contrast level value
     */
    data class ChangeContrastLevel(val input: ColorSchemeContrastLevel) : DesignUiEvent

    /**
     * The next screen can be travelled to.
     */
    data object Continue : DesignUiEvent

    /**
     * The user clocked on the back button
     */
    data object Return : DesignUiEvent

}

/**
 * Events fired by the view model of the ui screen.
 */
sealed interface DesignEvent : ViewModelEvent {

    /**
     * The next screen can be travelled to.
     */
    data object Continue : DesignEvent

    /**
     * The user clocked on the back button
     */
    data object Return : DesignEvent

}