package me.bumiller.mol.common.ui.input.validation

/**
 * Errors that may occur during input validation.
 */
sealed interface ValidationError {

    /**
     * The field does not match the necessary email format.
     */
    data object EmailFormat : ValidationError

    /**
     * The field is not strong enough.
     */
    data object PasswordFormat : ValidationError

    /**
     * The field does not conform to the name format.
     */
    data object NameFormat : ValidationError

    /**
     * The field was empty
     */
    data object Empty : ValidationError

    /**
     * The field does not match the entered password.
     */
    data object PasswordConfirm : ValidationError

    /**
     * The field was a non-valid email token
     */
    data object InvalidEmailToken : ValidationError

    /**
     * The email is already taken
     */
    data object EmailTaken : ValidationError

    /**
     * The field is a non-valid sign up token
     */
    data object InvalidSignupToken : ValidationError

    /**
     * The field is a non-valid secret
     */
    data object InvalidSecret : ValidationError

    /**
     * The fields do not match valid credentials
     */
    data object InvalidCredentials : ValidationError

    /**
     * The date/time/datetime lies in the future.
     */
    data object DateInFuture : ValidationError

    /**
     * The field is not a valid url
     */
    data object BadUrl : ValidationError

    /**
     * The url that the field depicts can not be reached
     */
    data object CantReachUrl : ValidationError

}
