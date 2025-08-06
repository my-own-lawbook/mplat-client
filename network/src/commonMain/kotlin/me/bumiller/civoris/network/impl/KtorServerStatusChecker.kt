package me.bumiller.civoris.network.impl

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException
import me.bumiller.civoris.network.ServerStatusChecker
import java.nio.channels.UnresolvedAddressException

private const val PingPath = "/ping/"

internal class KtorServerStatusChecker(clientEngineFactory: HttpClientEngineFactory<*>) :
    ServerStatusChecker {

    private val client: HttpClient = HttpClient(clientEngineFactory)

    override suspend fun checkServerConnection(url: String): Boolean {
        val response = try {
            client.get("$url$PingPath")
        } catch (e: IOException) {
            null
        } catch (e: UnresolvedAddressException) {
            null
        }

        return response?.status == HttpStatusCode.OK
    }

}