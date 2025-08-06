package me.bumiller.civoris.di

import me.bumiller.civoris.app.CivorisAppViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for the composeApp module.
 */
val appModule = module {
    viewModel {
        CivorisAppViewModel(get(), get())
    }
}