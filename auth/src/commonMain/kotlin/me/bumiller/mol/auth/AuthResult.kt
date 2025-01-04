package me.bumiller.mol.auth

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
 * Class that encapsulates states an authentication request can have.
 */
sealed class AuthResult<Error>(

    /**
     * Whether the call successfully authenticated the client.
     */
    val success: Boolean

) {

    /**
     * The request was successful.
     */
    class Success<ErrorType> : AuthResult<ErrorType>(true)

    /**
     * A network error caused the request to not be sent.
     */
    class NetworkError<ErrorType> : AuthResult<ErrorType>(false)

    /**
     * An unknown error occurred.
     */
    class UnknownError<ErrorType> : AuthResult<ErrorType>(false)

    /**
     * A specific error occurred.
     *
     * @param errorType The error that occurred.
     */
    data class Error<ErrorType>(val errorType: ErrorType) : AuthResult<ErrorType>(false)

}