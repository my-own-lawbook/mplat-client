package me.bumiller.civoris.common.ui.input.validation

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import me.bumiller.civoris.common.ui.input.InputValue

/**
 * Validates an input value based on the semantic that is bound to it.
 *
 * @param inputValue The value for validate
 * @return The updated value with the error added, or no error if none occurred
 */
fun <Type> validateValue(inputValue: InputValue<Type>): InputValue<Type> {
    val error = inputValue.semantics.firstNotNullOfOrNull { getError(inputValue.value, it) }


    return inputValue.copy(error = error)
}

private fun <T> getError(value: T, semantic: InputSemantic): ValidationError? {
    return when (value) {

        is String -> when (semantic) {
            InputSemantic.Password -> validatePassword(value)
            InputSemantic.Email -> validateEmail(value)
            InputSemantic.Name -> validateName(value)
            InputSemantic.NonEmpty -> validateNonEmpty(value)
            InputSemantic.Url -> validateUrl(value)
            InputSemantic.Username -> validateUsername(value)
            else -> null
        }

        is LocalDate -> when (semantic) {
            InputSemantic.NotInFuture -> validateDateNotInFuture(value)
            else -> null
        }

        is LocalDateTime -> when (semantic) {
            InputSemantic.NotInFuture -> validateDateTimeNotInFuture(value)
            else -> null
        }

        is LocalTime -> when (semantic) {
            InputSemantic.NotInFuture -> validateTimeNotInFuture(value)
            else -> null
        }

        null -> when (semantic) {
            InputSemantic.NotNull -> ValidationError.Empty
            else -> null
        }

        else -> null
    }
}

/**
 * Shorthand extension for [validate]
 */
fun <Type> InputValue<Type>.validate() = validateValue(this)

private const val HTTPS_URL =
    "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)"

private fun validateUrl(string: String): ValidationError? =
    if (!Regex(HTTPS_URL).matches("https://$string")) ValidationError.BadUrl
    else null

private const val USERNAME =
    "^[a-zA-Z0-9-_]{8,16}\$"

private fun validateUsername(string: String): ValidationError? =
    if (!Regex(USERNAME).matches(string)) ValidationError.UsernameFormat
    else null

private fun validateDateNotInFuture(value: LocalDate): ValidationError? =
    if (value > Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
        ValidationError.DateInFuture
    else null

private fun validateDateTimeNotInFuture(value: LocalDateTime): ValidationError? =
    if (value > Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
        ValidationError.DateInFuture
    else null

private fun validateTimeNotInFuture(value: LocalTime): ValidationError? =
    if (value > Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time)
        ValidationError.DateInFuture
    else null

private const val EMAIL_ADDRESS =
    "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c" +
            "\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:" +
            "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(2(5[0-5]|" +
            "[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-" +
            "9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x" +
            "01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])"

private fun validateEmail(str: String): ValidationError? =
    if (!Regex(EMAIL_ADDRESS).matches(str)) ValidationError.EmailFormat
    else null

private fun validatePassword(str: String): ValidationError? =
    if (
        str.length < 8 ||
        str.count { it.isDigit() } < 1 ||
        str.count { it.isUpperCase() } < 1 ||
        str.count { it.isLowerCase() } < 1 ||
        str.any { it.isWhitespace() }
    ) ValidationError.PasswordFormat
    else null

private fun validateName(str: String): ValidationError? =
    if (str.length < 2) ValidationError.NameFormat
    else null

private fun validateNonEmpty(str: String): ValidationError? =
    if (str.isEmpty()) ValidationError.Empty
    else null