package me.bumiller.mol.data.impl

import java.net.HttpURLConnection
import java.net.URI

private const val ReliablyOnlineUrl = "https://www.google.com/"
private const val Timeout = 2000

/**
 * Checks whether the device is connected to the internet
 */
internal actual val isConnectedToInternetCallback: () -> Boolean = {
    try {
        val url = URI(ReliablyOnlineUrl).toURL()
        val connection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = Timeout
        connection.connect()
        connection.responseCode == 200
    } catch (e: Exception) {
        false
    }
}