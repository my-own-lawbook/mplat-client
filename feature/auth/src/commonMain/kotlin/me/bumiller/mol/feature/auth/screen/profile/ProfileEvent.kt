package me.bumiller.mol.feature.auth.screen.profile

import kotlinx.datetime.LocalDate
import me.bumiller.mol.common.ui.event.UiEvent
import me.bumiller.mol.common.ui.event.ViewModelEvent
import me.bumiller.mol.model.user.Gender

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

/**
 * Events fired by the profile view model.
 */
internal sealed interface ProfileEvent : ViewModelEvent {

    /**
     * Profile has been set.
     */
    data object Continue : ProfileEvent

}