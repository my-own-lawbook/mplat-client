package me.bumiller.mol.common.ui.input.validation

/**
 * Collection of semantics for input fields.
 *
 * Each is bound to a specific set of criteria.
 */
sealed interface InputSemantic {

    /**
     * A password field
     */
    data object Password : InputSemantic

    /**
     * An email field
     */
    data object Email : InputSemantic

    /**
     * A normal field for names
     */
    data object Name : InputSemantic

    /**
     * A field that should not be empty
     */
    data object NonEmpty : InputSemantic

    /**
     * A field that should not be null (essentially the same as [NonEmpty])
     */
    data object NotNull : InputSemantic

    /**
     * A field that should not lie in the future (for date field)
     */
    data object NotInFuture : InputSemantic

    /**
     * A field that should be a valid url
     */
    data object Url : InputSemantic

}