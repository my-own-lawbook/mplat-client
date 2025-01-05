package me.bumiller.mol.feature.auth.di

import me.bumiller.mol.feature.auth.screen.email.EmailViewModel
import me.bumiller.mol.feature.auth.screen.login.LoginViewModel
import me.bumiller.mol.feature.auth.screen.signup.SignupViewModel
import me.bumiller.mol.feature.auth.screen.welcome.WelcomeViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin module for the auth module
 */
val authFeatureModule = module {
    viewModelOf(::WelcomeViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::SignupViewModel)
    viewModelOf(::EmailViewModel)
}