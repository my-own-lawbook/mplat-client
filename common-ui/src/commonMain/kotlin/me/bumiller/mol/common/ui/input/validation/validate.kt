package me.bumiller.mol.common.ui.input.validation

import com.eygraber.uri.Url
import me.bumiller.mol.common.ui.input.InputValue
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Validates an input value based on the semantic that is bound to it.
 *
 * @param inputValue The value for validate
 * @return The updated value with the error added, or no error if none occurred
 */
fun <Type> validateValue(inputValue: InputValue<Type>): InputValue<Type> {
    val error = when (inputValue.value) {
        is String -> when (inputValue.semantic) {
            InputSemantic.Password -> validatePassword(inputValue.value)
            InputSemantic.Email -> validateEmail(inputValue.value)
            InputSemantic.Name -> validateName(inputValue.value)
            InputSemantic.NonEmpty -> validateNonEmpty(inputValue.value)
            else -> null
        }

        is LocalDate -> when (inputValue.semantic) {
            InputSemantic.NotInFuture -> validateNotInFuture(inputValue.value)
            else -> null
        }

        is LocalDateTime -> when (inputValue.semantic) {
            InputSemantic.NotInFuture -> validateNotInFuture(inputValue.value)
            else -> null
        }

        is LocalTime -> when (inputValue.semantic) {
            InputSemantic.NotInFuture -> validateNotInFuture(inputValue.value)
            else -> null
        }

        else -> null
    }


    return inputValue.copy(
        error = error
    )
}

/**
 * Shorthand extension for [validate]
 */
fun <Type> InputValue<Type>.validate() = validateValue(this)

private fun validateUrl(string: String): ValidationError? {
    val url = Url.parseOrNull(string)
    val valid = url?.scheme == "https"

    return if (valid) null
    else ValidationError.BadUrl
}


private fun <Type> validateNotNull(value: Type) =
    if (value == null) ValidationError.Empty
    else null

private fun validateNotInFuture(value: LocalDate): ValidationError? =
    if (value.isAfter(LocalDate.now()))
        ValidationError.DateInFuture
    else null

private fun validateNotInFuture(value: LocalDateTime): ValidationError? =
    if (value.isAfter(LocalDateTime.now()))
        ValidationError.DateInFuture
    else null

private fun validateNotInFuture(value: LocalTime): ValidationError? =
    if (value.isAfter(LocalTime.now()))
        ValidationError.DateInFuture
    else null

const val EMAIL_ADDRESS =
    "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

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