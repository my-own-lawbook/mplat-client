package me.bumiller.civoris.feature.auth.screen

import me.bumiller.civoris.common.ui.event.ViewModelEvent
import me.bumiller.civoris.feature.auth.model.SignupStage

/**
 * Events fired from the [SignupStageViewmodel] to the ui.
 */
internal interface SignupStageEvent : ViewModelEvent {

    /**
     * The signup stage changed and the user should be redirected to the according stage.
     */
    data class SignupStageChanged(val stage: SignupStage) : SignupStageEvent

}