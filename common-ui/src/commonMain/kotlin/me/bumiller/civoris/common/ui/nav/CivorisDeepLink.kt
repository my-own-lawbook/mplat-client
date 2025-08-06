package me.bumiller.civoris.common.ui.nav

import com.eygraber.uri.Uri

/**
 * Contains deep links that the app reacts to.
 */
sealed interface CivorisDeepLink {

    /**
     * The deep link from the verify email email.
     *
     * @param otp The one time password to verify the email
     */
    data class VerifyEmail(val otp: String) : CivorisDeepLink

    /**
     *
     */
    fun initialTopLevelLocation(): CivorisTopLevelLocation =
        when (this) {
            is VerifyEmail -> CivorisTopLevelLocation.Auth
        }

    companion object {

        private const val EMAIL_VERIFY_LINK = "/email-verify"
        private const val EMAIL_VERIFY_OTP_QUERY = "otp"

        /**
         * Gets the according [CivorisDeepLink] from the given uri.
         *
         * @param uri The uri to get the deep link for
         * @return The deep link, or null if it could not be parsed
         */
        fun fromUri(uri: Uri): CivorisDeepLink? = uri.path?.let {
            if (it.startsWith(EMAIL_VERIFY_LINK)) {
                val otp = uri.getQueryParameter(EMAIL_VERIFY_OTP_QUERY) ?: return null
                VerifyEmail(otp)
            } else {
                return null
            }
        }

    }

}