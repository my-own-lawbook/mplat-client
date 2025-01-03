package me.bumiller.mol.network

/**
 * Base class for a service that helps to evaluate the connection to the server.
 */
interface ServerStatusChecker {

    /**
     * Checks the connection to a server with a specified url.
     *
     * @param url The base url of the server
     * @return True if the server is reached, false otherwise
     */
    suspend fun checkServerConnection(url: String): Boolean

}