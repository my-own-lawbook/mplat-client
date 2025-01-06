package me.bumiller.mol.network.plugin

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.http.HttpHeaders
import io.ktor.util.AttributeKey
import me.bumiller.mol.settings.UserSettingsSource

/**
 * Ktor plugin that sets the authorization plugin based on the saved access token.
 */
class MolAuth(

    /**
     * The settings source
     */
    private val userSettingsSource: UserSettingsSource

) : HttpClientPlugin<Unit, MolAuth> {

    override val key = AttributeKey<MolAuth>("MolAuth")

    override fun prepare(block: Unit.() -> Unit) = this

    override fun install(plugin: MolAuth, scope: HttpClient) {
        scope.requestPipeline.intercept(HttpRequestPipeline.Before) {
            val accessToken = userSettingsSource.settings.value.accessToken

            if (accessToken != null) {
                context.headers[HttpHeaders.Authorization] = accessToken.authHeader()
            }
        }
    }

    private fun String.authHeader() = "Bearer $this"

}