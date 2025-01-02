package me.bumiller.mol.network.impl

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import me.bumiller.mol.network.ServerStatusChecker

private const val PingPath = "ping/"

internal class KtorServerStatusChecker : ServerStatusChecker {

    private val client: HttpClient = HttpClient()

    override suspend fun checkServerConnection(url: String): Boolean {
        val response = client.get("$url$PingPath")

        return response.status == HttpStatusCode.OK
    }

}