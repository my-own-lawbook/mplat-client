package me.bumiller.mol.network.impl

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException
import me.bumiller.mol.network.ServerStatusChecker
import java.nio.channels.UnresolvedAddressException

private const val PingPath = "api/v1/ping/"

internal class KtorServerStatusChecker : ServerStatusChecker {

    private val client: HttpClient = HttpClient(CIO)

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