package me.bumiller.mol.feature.onboarding.di

import me.bumiller.mol.feature.onboarding.screen.design.DesignViewModel
import me.bumiller.mol.feature.onboarding.screen.url.UrlViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin module for the onboarding module
 */
val onboardingModule = module {
    viewModelOf(::UrlViewModel)
    viewModelOf(::DesignViewModel)
}