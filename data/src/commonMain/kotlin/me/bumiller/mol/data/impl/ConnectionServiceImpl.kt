package me.bumiller.mol.data.impl

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

        if (!isConnectedToInternet()) return ConnectionState.NoInternet

        return if (serverStatusChecker.checkServerConnection(serverUrl.toString()))
            ConnectionState.Connected
        else ConnectionState.NoInternet
    }

}

/**
 * Checks whether the device is connected to the internet
 */
internal expect fun isConnectedToInternet(): Boolean