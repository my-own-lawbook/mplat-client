package me.bumiller.civoris.data.impl

import com.eygraber.uri.Url
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import me.bumiller.civoris.data.ConnectionService
import me.bumiller.civoris.data.ConnectionState
import me.bumiller.civoris.data.test.BaseDataTest
import me.bumiller.civoris.model.settings.ColorMode
import me.bumiller.civoris.model.settings.ColorScheme
import me.bumiller.civoris.model.settings.ColorSchemeContrastLevel
import me.bumiller.civoris.model.settings.UserSettings
import me.bumiller.civoris.network.ServerStatusChecker
import me.bumiller.civoris.settings.UserSettingsSource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * Contains tests for the [ConnectionServiceImpl] service.
 */
class ConnectionServiceImplTest : BaseDataTest() {

    private val settings = UserSettings(
        ColorMode.Dark,
        ColorScheme.App,
        ColorSchemeContrastLevel.High,
        Url.parse("https://example.com/"),
        null,
        null
    )

    private lateinit var serviceWithInternet: ConnectionService
    private lateinit var serviceWithoutInternet: ConnectionService

    private lateinit var serverStatusChecker: ServerStatusChecker
    private lateinit var userSettingsSource: UserSettingsSource

    @BeforeEach
    fun setup() {
        serverStatusChecker = mockk()
        userSettingsSource = mockk()

        serviceWithInternet =
            ConnectionServiceImpl(serverStatusChecker, userSettingsSource) { true }
        serviceWithoutInternet =
            ConnectionServiceImpl(serverStatusChecker, userSettingsSource) { false }

        every { userSettingsSource.settings } returns MutableStateFlow(settings)
    }

    @Test
    @DisplayName("When no backend url is specified, that state is returned")
    fun noUrlReturnedWhenNoUrlSpecified() = runTest {
        every { userSettingsSource.settings } returns MutableStateFlow(settings.copy(backendUrl = null))

        val state = serviceWithInternet.getConnectionState()
        val state2 = serviceWithoutInternet.getConnectionState()

        Assertions.assertEquals(ConnectionState.NoUrl, state)
        Assertions.assertEquals(ConnectionState.NoUrl, state2)
    }

    @Test
    @DisplayName("When not connection to internet is available, that is returned")
    fun noInternetReturnedWhenNoInternet() = runTest {
        val state = serviceWithoutInternet.getConnectionState("https://example.com/")
        val state2 = serviceWithoutInternet.getConnectionState()

        Assertions.assertEquals(ConnectionState.NoInternet, state)
        Assertions.assertEquals(ConnectionState.NoInternet, state2)
    }

    @Test
    @DisplayName("When the server can't be reached, that is returned")
    fun serverNotReachableWhenServerNotReachable() = runTest {
        coEvery { serverStatusChecker.checkServerConnection(any()) } returns false
        val state = serviceWithInternet.getConnectionState("https://example.com/")
        val state2 = serviceWithInternet.getConnectionState()

        Assertions.assertEquals(ConnectionState.CantReachServer, state)
        Assertions.assertEquals(ConnectionState.CantReachServer, state2)
    }

    @Test
    @DisplayName("When the server can be reached, that is returned")
    fun serverReachedReturnedWhenServerReached() = runTest {
        coEvery { serverStatusChecker.checkServerConnection(any()) } returns true
        val state = serviceWithInternet.getConnectionState("https://example.com/")
        val state2 = serviceWithInternet.getConnectionState()

        Assertions.assertEquals(ConnectionState.Connected, state)
        Assertions.assertEquals(ConnectionState.Connected, state2)
    }

}