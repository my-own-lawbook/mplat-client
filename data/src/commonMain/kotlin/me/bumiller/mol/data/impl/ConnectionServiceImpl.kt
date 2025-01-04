package me.bumiller.mol.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.bumiller.mol.data.ConnectionService
import me.bumiller.mol.data.ConnectionState
import me.bumiller.mol.network.ServerStatusChecker
import me.bumiller.mol.settings.UserSettingsSource

internal class ConnectionServiceImpl(

    private val serverStatusChecker: ServerStatusChecker,

    private val settingsSource: UserSettingsSource

) : ConnectionService {

    override suspend fun getConnectionState(): ConnectionState {
        val serverUrl = settingsSource.settings.value.backendUrl ?: return ConnectionState.NoUrl

        return getConnectionState(serverUrl.toString())
    }

    override suspend fun getConnectionState(url: String): ConnectionState =
        withContext(Dispatchers.IO) {
            if (!isConnectedToInternet()) return@withContext ConnectionState.NoInternet

            return@withContext if (serverStatusChecker.checkServerConnection(url))
                ConnectionState.Connected
            else ConnectionState.CantReachServer
        }

}

/**
 * Checks whether the device is connected to the internet
 */
internal expect fun isConnectedToInternet(): Boolean