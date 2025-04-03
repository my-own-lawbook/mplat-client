package me.bumiller.mol.network.di

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.scope.Scope

/**
 * Platform-dependant method to create an [HttpClientEngineFactory].
 */
internal actual fun Scope.instantiateClientEngineFactory(): HttpClientEngineFactory<*> = OkHttp