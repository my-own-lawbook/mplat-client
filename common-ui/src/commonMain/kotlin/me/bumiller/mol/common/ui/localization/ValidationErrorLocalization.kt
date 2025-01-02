package me.bumiller.mol.common.ui.localization

import androidx.compose.runtime.Composable
import me.bumiller.mol.common.ui.input.validation.ValidationError
import me.bumiller.mol.common_ui.Res
import me.bumiller.mol.common_ui.validation_date_in_future
import me.bumiller.mol.common_ui.validation_email
import me.bumiller.mol.common_ui.validation_email_taken
import me.bumiller.mol.common_ui.validation_email_token
import me.bumiller.mol.common_ui.validation_empty
import me.bumiller.mol.common_ui.validation_invalid_credentials
import me.bumiller.mol.common_ui.validation_name
import me.bumiller.mol.common_ui.validation_password
import me.bumiller.mol.common_ui.validation_password_repeat
import me.bumiller.mol.common_ui.validation_secret_invalid
import me.bumiller.mol.common_ui.validation_signup_token_invalid
import me.bumiller.mol.common_ui.validation_url
import me.bumiller.mol.common_ui.validation_url_reach
import org.jetbrains.compose.resources.stringResource

/**
 * Creates a localized description of the validation error.
 *
 * @return The localized description
 */
@Composable
fun ValidationError.localizedDescription() = stringResource(
    when (this) {
        ValidationError.EmailFormat -> Res.string.validation_email
        ValidationError.Empty -> Res.string.validation_empty
        ValidationError.NameFormat -> Res.string.validation_name
        ValidationError.PasswordConfirm -> Res.string.validation_password_repeat
        ValidationError.PasswordFormat -> Res.string.validation_password
        ValidationError.InvalidEmailToken -> Res.string.validation_email_token
        ValidationError.EmailTaken -> Res.string.validation_email_taken
        ValidationError.InvalidSignupToken -> Res.string.validation_signup_token_invalid
        ValidationError.InvalidSecret -> Res.string.validation_secret_invalid
        ValidationError.InvalidCredentials -> Res.string.validation_invalid_credentials
        ValidationError.DateInFuture -> Res.string.validation_date_in_future
        ValidationError.BadUrl -> Res.string.validation_url
        ValidationError.CantReachUrl -> Res.string.validation_url_reach
    }
)