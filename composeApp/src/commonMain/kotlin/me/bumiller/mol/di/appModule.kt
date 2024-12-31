package me.bumiller.mol.di

import me.bumiller.mol.MolAppViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for the composeApp module.
 */
val appModule = module {
    viewModel {
        MolAppViewModel(get())
    }
}