package me.bumiller.mol.network.di

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO
import org.koin.core.scope.Scope

/**
 * Platform-dependant method to create an [HttpClientEngineFactory].
 */
internal actual fun Scope.instantiateClientEngineFactory(): HttpClientEngineFactory<*> = CIO