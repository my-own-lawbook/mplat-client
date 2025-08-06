package me.bumiller.civoris.feature.onboarding.screen.url

import me.bumiller.civoris.common.ui.input.InputValue
import me.bumiller.civoris.common.ui.input.inputValue
import me.bumiller.civoris.common.ui.input.validation.InputSemantic

/**
 * State for the url screen
 */
data class UrlState(

    /**
     * The url field input
     */
    val url: InputValue<String> = inputValue("", InputSemantic.Url)

)