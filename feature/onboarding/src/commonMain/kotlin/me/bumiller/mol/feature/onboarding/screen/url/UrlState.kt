package me.bumiller.mol.feature.onboarding.screen.url

import me.bumiller.mol.common.ui.input.InputValue
import me.bumiller.mol.common.ui.input.validation.InputSemantic

/**
 * State for the url screen
 */
data class UrlState(

    /**
     * The url field input
     */
    val url: InputValue<String> = InputValue("", InputSemantic.Url)

)