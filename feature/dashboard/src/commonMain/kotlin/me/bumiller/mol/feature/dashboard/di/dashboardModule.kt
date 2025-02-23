package me.bumiller.mol.feature.dashboard.di

import me.bumiller.mol.feature.dashboard.screen.DashboardViewmodel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin module for the dashboard module.
 */
val dashboardModule = module {
    viewModelOf(::DashboardViewmodel)
}