package me.bumiller.mol.feature.auth.screen.profile

import kotlinx.coroutines.flow.update
import me.bumiller.mol.auth.AuthResult
import me.bumiller.mol.auth.AuthService
import me.bumiller.mol.common.ui.input.validation.validate
import me.bumiller.mol.common.ui.viewmodel.MolViewModel
import me.bumiller.mol.model.user.Profile

/**
 * The view model for the profile screen.
 */
internal class ProfileViewModel(

    /**
     * The auth service.
     */
    private val authService: AuthService

) : MolViewModel<ProfileUiEvent, ProfileEvent>() {

    init {
        registerUiState(ProfileUiState())
    }

    /**
     * The state of the form.
     */
    val formState = uiState<ProfileUiState>()

    override suspend fun handleEvent(event: ProfileUiEvent) = with(event) {
        when (this) {
            is ProfileUiEvent.ChangeFirstName -> formState.update {
                it.copy(firstName = it.firstName.update(input))
            }

            is ProfileUiEvent.ChangeLastName -> formState.update {
                it.copy(lastName = it.lastName.update(input))
            }

            is ProfileUiEvent.ChangeGender -> formState.update {
                it.copy(gender = it.gender.update(input))
            }

            is ProfileUiEvent.ChangeBirthday -> formState.update {
                it.copy(birthday = it.birthday.update(input))
            }

            is ProfileUiEvent.Confirm -> handle()
        }
    }

    private suspend fun ProfileUiEvent.Confirm.handle() {
        formState.update {
            it.copy(
                firstName = it.firstName.validate(),
                lastName = it.lastName.validate(),
                gender = it.gender.validate(),
                birthday = it.birthday.validate()
            )
        }

        val isError =
            formState.value.run { firstName.isError() || lastName.isError() || gender.isError() || birthday.isError() }
        if (isError) return

        val profile = Profile(
            formState.value.firstName.value,
            formState.value.lastName.value,
            formState.value.gender.value!!,
            formState.value.birthday.value!!
        )

        val profileResponse = withFetchState {
            authService.setProfile(profile)
        }

        when (profileResponse) {
            is AuthResult.Success -> fireEvent(ProfileEvent.Continue)
            is AuthResult.Error, is AuthResult.UnknownError -> hasUnknownError.emit(true)
            is AuthResult.NetworkError -> hasNetworkError.emit(true)
        }
    }

}