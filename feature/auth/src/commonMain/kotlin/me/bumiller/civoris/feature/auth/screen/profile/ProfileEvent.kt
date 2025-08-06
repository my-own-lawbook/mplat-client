package me.bumiller.civoris.feature.auth.screen.profile

import kotlinx.datetime.LocalDate
import me.bumiller.civoris.common.ui.event.UiEvent
import me.bumiller.civoris.model.user.Gender

/**
 * Events fired by the profile screen.
 */
internal sealed interface ProfileUiEvent : UiEvent {

    /**
     * User changes the first name input value.
     */
    data class ChangeFirstName(val input: String) : ProfileUiEvent

    /**
     * User changes the last name input value.
     */
    data class ChangeLastName(val input: String) : ProfileUiEvent

    /**
     * User changes the gender input value.
     */
    data class ChangeGender(val input: Gender) : ProfileUiEvent

    /**
     * User changes the birthday input value.
     */
    data class ChangeBirthday(val input: LocalDate?) : ProfileUiEvent

    /**
     * User clicked on confirm.
     */
    data object Confirm : ProfileUiEvent

}