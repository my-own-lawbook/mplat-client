package me.bumiller.mol.common.ui.input

import me.bumiller.mol.common.ui.input.validation.InputSemantic
import me.bumiller.mol.common.ui.input.validation.ValidationError

/**
 * A box type which wraps a type [Type] for easier handling in input forms
 */
data class InputValue<Type>(

    /**
     * The actual value
     */
    val value: Type,

    /**
     * The [InputSemantic] of this field, used for validating
     */
    val semantic: InputSemantic? = null,

    /**
     * The error of this field, or null if there is no error
     */
    val error: ValidationError? = null,

    /**
     * Whether this field can be edited
     */
    val canEdit: Boolean = true

) {

    /**
     * Wrapper for updating the value but keeping other data. Returns a new [InputValue]
     */
    fun update(value: Type) = copy(value = value)

    /**
     * Whether this field has an error
     */
    fun isError() = error != null

    /**
     * Maps the field to another type. Returns a new [InputValue]
     */
    fun <Type2> map(mapper: (Type) -> Type2) = InputValue(mapper(value), semantic, error, canEdit)

}
