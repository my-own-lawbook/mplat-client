package me.bumiller.mol.feature.home.di

import me.bumiller.mol.feature.home.screen.home.HomeViewmodel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin module for the home module.
 */
val homeModule = module {
    viewModelOf(::HomeViewmodel)
}