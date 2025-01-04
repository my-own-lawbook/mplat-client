package me.bumiller.mol.data.di

import me.bumiller.mol.data.ConnectionService
import me.bumiller.mol.data.impl.ConnectionServiceImpl
import org.koin.dsl.module

/**
 * The koin-module for the data module
 */
val dataModule = module {
    single<ConnectionService> { ConnectionServiceImpl(get(), get()) }
}