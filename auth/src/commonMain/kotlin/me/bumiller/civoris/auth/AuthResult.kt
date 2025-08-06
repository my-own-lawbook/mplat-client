package me.bumiller.civoris.auth

import me.bumiller.civoris.network.model.ErrorInfo

/**
 * Errors that can occur when trying to login.
 */
enum class LoginError {

    /**
     * Credentials didn't match.
     */
    BadCredentials,

    /**
     * Token was invalid.
     */
    BadToken

}

/**
 * Errors that can occur when trying to sign up.
 */
enum class SignupError {

    /**
     * The email is already in use.
     */
    EmailNotUnique,

    /**
     * The username is already in use.
     */
    UsernameNotUnique

}

/**
 * Errors that can occur when trying to submit an email token.
 */
enum class SubmitEmailTokenError {

    /**
     * The token is invalid.
     */
    InvalidToken

}

/**
 * Errors that can occur when trying to request an email token.
 */
enum class RequestEmailTokenError {

    /**
     * Invalid authentication was presented.
     */
    NotAuthenticated

}

/**
 * Errors that can occur when trying to get the profile of a user.
 */
enum class GetProfileError {

    /**
     * The profile has not been set.
     */
    NotSet,

    /**
     * No authentication was presented.
     */
    NotAuthenticated

}

/**
 * Class that encapsulates states an authentication request can have.
 */
sealed class AuthResult<Data, Error>(

    /**
     * Whether the call successfully authenticated the client.
     */
    val success: Boolean

) {

    /**
     * The request was successful.
     */
    data class Success<Data, ErrorType>(val data: Data) : AuthResult<Data, ErrorType>(true)

    /**
     * A network error caused the request to not be sent.
     */
    class NetworkError<Data, ErrorType> : AuthResult<Data, ErrorType>(false)

    /**
     * An unknown error occurred.
     */
    data class UnknownError<Data, ErrorType>(
        val code: Int? = null,
        val errorInfo: ErrorInfo? = null,
        val message: String? = null
    ) : AuthResult<Data, ErrorType>(false)

    /**
     * A specific error occurred.
     *
     * @param errorType The error that occurred.
     */
    data class Error<Data, ErrorType>(val errorType: ErrorType) : AuthResult<Data, ErrorType>(false)

}