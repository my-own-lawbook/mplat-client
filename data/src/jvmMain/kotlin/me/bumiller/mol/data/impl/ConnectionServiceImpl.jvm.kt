package me.bumiller.mol.data.impl

import java.net.InetAddress
import java.net.URI

private const val ReliablyOnlineUrl = "https://www.google.com/"
private const val Timeout = 2000

/**
 * Checks whether the device is connected to the internet
 */
internal actual fun isConnectedToInternet(): Boolean {
    val inetAddress = InetAddress.getByName(URI(ReliablyOnlineUrl).host)
    return inetAddress.isReachable(Timeout)
}