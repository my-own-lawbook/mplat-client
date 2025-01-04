package me.bumiller.mol.data

/**
 * States the connection of the app can be in.
 */
sealed class ConnectionState(

    /**
     * Whether the server can be reached.
     */
    val hasConnection: Boolean

) {

    /**
     * Server can be reached.
     */
    data object Connected : ConnectionState(true)

    /**
     * Connection to server cant be tested, because the device is not connected to the internet.
     */
    data object NoInternet : ConnectionState(false)

    /**
     * Connection to server cant be tested, because the app has no URL set.
     */
    data object NoUrl : ConnectionState(false)

    /**
     * Connection to server is unavailable, because the server is not reachable.
     */
    data object CantReachServer : ConnectionState(false)

}

/**
 * Service that informs about the state of connection to the internet and the server.
 */
interface ConnectionService {

    /**
     * Gets the current connection state.
     *
     * @return The connection state
     */
    suspend fun getConnectionState(): ConnectionState

}