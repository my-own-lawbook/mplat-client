package me.bumiller.mol.di

import me.bumiller.mol.MolAppViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin module for the composeApp module.
 */
val appModule = module {
    viewModelOf(::MolAppViewModel)
}