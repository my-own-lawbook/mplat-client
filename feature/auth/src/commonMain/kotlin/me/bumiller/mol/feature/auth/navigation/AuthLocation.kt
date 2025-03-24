package me.bumiller.mol.feature.auth.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import me.bumiller.mol.common.ui.LocalNavGraphSetupState
import me.bumiller.mol.common.ui.nav.CivorisDeepLink
import me.bumiller.mol.common.ui.nav.CivorisNavHost
import me.bumiller.mol.feature.auth.model.SignupStage
import me.bumiller.mol.feature.auth.screen.email.EmailScreen
import me.bumiller.mol.feature.auth.screen.login.LoginScreen
import me.bumiller.mol.feature.auth.screen.profile.ProfileScreen
import me.bumiller.mol.feature.auth.screen.signup.SignupScreen
import me.bumiller.mol.feature.auth.screen.welcome.WelcomeScreen

/**
 * Navigation destination for the auth location.
 */
@Serializable
data object AuthLocation

/**
 * Navigation destination for the email screen.
 *
 * @param initialOtp The OTP that will be prefilled on the screen
 */
@Serializable
internal data class EmailScreen(val initialOtp: String? = null)

/**
 * Navigation destination for the welcome screen.
 */
@Serializable
internal data object WelcomeScreen

/**
 * Navigation destination for the login screen.
 */
@Serializable
internal data object LoginScreen

/**
 * Navigation destination for the signup screen.
 */
@Serializable
internal data object SignupScreen

/**
 * Navigation destination for the profile screen.
 */
@Serializable
internal data object ProfileScreen

/**
 * Root composable for the auth location.
 *
 * @param onUrlChange Callback for when the user wants to change the url.
 * @param onAuthenticate Callback for when the user authenticated successfully.
 */
fun NavGraphBuilder.authLocation(
    onUrlChange: () -> Unit,
    onAuthenticate: () -> Unit,
    deepLink: CivorisDeepLink?
) = composable<AuthLocation> {
    val navController = rememberNavController()
    val navGraphSetupBefore = LocalNavGraphSetupState.current

    val onStageChanged = { stage: SignupStage ->
        stage.navigationDestination()?.let(navController::navigate)
            ?: onAuthenticate()
    }

    val initialLocation: Any =
        if (deepLink !is CivorisDeepLink.VerifyEmail || navGraphSetupBefore) WelcomeScreen else EmailScreen

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CivorisNavHost(
            navController = navController,
            startDestination = initialLocation,
            deepLink = deepLink,
            destinationForDeepLink = {
                when (it) {
                    is CivorisDeepLink.VerifyEmail -> EmailScreen(it.otp)
                }
            }
        ) {
            welcomeScreen(
                onLogin = { navController.navigate(LoginScreen) },
                onUrlClick = onUrlChange,
                onSignup = { navController.navigate(SignupScreen) }
            )
            loginScreen(
                onBack = { navController.popBackStack() },
                onAuthenticate = { isEmailVerified, isProfileSet ->
                    if (isEmailVerified && isProfileSet)
                        onAuthenticate()
                    else if (!isEmailVerified)
                        navController.navigate(EmailScreen(null))
                    else
                        navController.navigate(ProfileScreen)
                },
                onSignup = {
                    navController.navigate(SignupScreen) {
                        popUpTo(WelcomeScreen) {
                            inclusive = false
                        }
                    }
                }
            )
            signupScreen(
                onBack = { navController.popBackStack() },
                onStageChange = onStageChanged,
                onLogin = {
                    navController.navigate(LoginScreen) {
                        popUpTo(SignupScreen) {
                            inclusive = true
                        }
                    }
                }
            )
            profileScreen(
                onStageChanged = onStageChanged
            )
            emailScreen(
                onStageChange = onStageChanged
            )
        }
    }
}

/**
 * Builds the welcome-screen-destination inside a nav-graph-builder.
 *
 * @param onLogin Callback when the user chose to login
 * @param onSignup Callback when the user chose to signup
 * @param onUrlClick Callback when the wants to change the url.
 */
internal fun NavGraphBuilder.welcomeScreen(
    onLogin: () -> Unit,
    onSignup: () -> Unit,
    onUrlClick: () -> Unit
) {
    composable<WelcomeScreen> {
        WelcomeScreen(onLogin, onSignup, onUrlClick)
    }
}

/**
 * Builds the login-screen-destination inside a nav-graph-builder.
 *
 * @param onBack The callback invoked when the back button is clicked.
 * @param onSignup The callback invoked when the link to the signup screen is clicked.
 * @param onAuthenticate The callback invoked when the user successfully authenticated with information about the auth state of the user.
 */
internal fun NavGraphBuilder.loginScreen(
    onBack: () -> Unit,
    onSignup: () -> Unit,
    onAuthenticate: (isEmailVerified: Boolean, isProfileSet: Boolean) -> Unit
) {
    composable<LoginScreen> {
        LoginScreen(onBack, onSignup, onAuthenticate)
    }
}

/**
 * Builds the signup-screen-destination inside a nav-graph-builder.
 *
 * @param onBack The callback for when the user wants to return to the recent screen
 * @param onStageChange The callback for when the signup stage changes
 * @param onLogin The callback for when the user navigates to the login screen
 */
internal fun NavGraphBuilder.signupScreen(
    onBack: () -> Unit,
    onStageChange: (SignupStage) -> Unit,
    onLogin: () -> Unit
) {
    composable<SignupScreen> {
        SignupScreen(onBack, onStageChange, onLogin)
    }
}

/**
 * Builds the email-screen-destination inside a nav-graph-builder.
 *
 * @param onStageChange The callback for when the signup stage changes
 */
internal fun NavGraphBuilder.emailScreen(
    onStageChange: (SignupStage) -> Unit
) {
    composable<EmailScreen> {
        EmailScreen(onStageChange)
    }
}

/**
 * Builds the profile-screen-destination inside a nav-graph-builder.
 *
 * @param onStageChanged The callback for when the signup stage changes
 */
internal fun NavGraphBuilder.profileScreen(
    onStageChanged: (SignupStage) -> Unit
) {
    composable<ProfileScreen> {
        ProfileScreen(onStageChanged)
    }
}

private fun SignupStage.navigationDestination(): Any? = when (this) {
    SignupStage.NotStarted -> SignupScreen
    SignupStage.AccountCreated -> EmailScreen(null)
    SignupStage.EmailVerified -> ProfileScreen
    SignupStage.Finished -> null
}