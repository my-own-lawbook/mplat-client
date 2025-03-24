package me.bumiller.mol.network.plugin

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.http.URLProtocol
import io.ktor.util.AttributeKey
import kotlinx.coroutines.flow.first
import me.bumiller.mol.settings.UserSettingsSource

/**
 * Ktor plugin that sets the host of any request to the one specified by the user.
 */
class DynamicUrl(

    /**
     * The settings source
     */
    private val userSettingsSource: UserSettingsSource

) : HttpClientPlugin<Unit, DynamicUrl> {

    override val key = AttributeKey<DynamicUrl>("DynamicUrl")

    override fun prepare(block: Unit.() -> Unit) = this

    override fun install(plugin: DynamicUrl, scope: HttpClient) {
        scope.requestPipeline.intercept(HttpRequestPipeline.Before) {
            val baseHost = userSettingsSource.settings.first().backendUrl?.host
                ?: throw IllegalStateException("Tried to make a request without having a backendUrl set.")

            context.url.host = baseHost
            context.url.protocol = URLProtocol.HTTPS
        }
    }

}