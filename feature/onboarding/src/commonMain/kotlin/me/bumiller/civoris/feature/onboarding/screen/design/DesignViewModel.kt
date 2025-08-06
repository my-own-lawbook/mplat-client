package me.bumiller.civoris.feature.onboarding.screen.design

import kotlinx.coroutines.flow.map
import me.bumiller.civoris.common.ui.viewmodel.CivorisViewModel
import me.bumiller.civoris.model.state.SimpleState
import me.bumiller.civoris.settings.UserSettingsSource

/**
 * View model for the [DesignScreen]
 */
class DesignViewModel(

    /**
     * The data source to get the settings of the user.
     */
    private val settingsSource: UserSettingsSource

) : CivorisViewModel<DesignUiEvent, DesignEvent>() {

    /**
     * Contains the updated settings of the user.
     */
    val settings = settingsSource.settings
        .map(SimpleState.Companion::success)
        .loadingStateIn()

    override suspend fun handleEvent(event: DesignUiEvent) = with(event) {
        val oldSettings = settings.value.dataOrNull() ?: return

        val newSettings = when (this) {
            is DesignUiEvent.ChangeContrastLevel -> oldSettings.copy(contrastLevel = input)
            is DesignUiEvent.ChangeMode -> oldSettings.copy(colorMode = input)
            is DesignUiEvent.ChangeScheme -> oldSettings.copy(colorScheme = input)
            DesignUiEvent.Continue -> {
                fireEvent(DesignEvent.Continue)
                return@with
            }

            DesignUiEvent.Return -> {
                fireEvent(DesignEvent.Return)
                return@with
            }
        }

        settingsSource.update(newSettings)
    }

}