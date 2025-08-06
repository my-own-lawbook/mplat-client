package me.bumiller.mol.di

import me.bumiller.mol.app.CivorisAppViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for the composeApp module.
 */
val appModule = module {
    viewModel {
        CivorisAppViewModel(get(), get())
    }
}