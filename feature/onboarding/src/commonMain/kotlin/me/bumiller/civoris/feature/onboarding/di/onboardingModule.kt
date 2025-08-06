package me.bumiller.civoris.feature.onboarding.di

import me.bumiller.civoris.feature.onboarding.screen.design.DesignViewModel
import me.bumiller.civoris.feature.onboarding.screen.url.UrlViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin module for the onboarding module
 */
val onboardingModule = module {
    viewModelOf(::UrlViewModel)
    viewModelOf(::DesignViewModel)
}