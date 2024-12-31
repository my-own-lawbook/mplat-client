package me.bumiller.mol.model.state

/**
 * Extension of the [SimpleState] class. Also allows for error states.
 */
sealed class BasicState<Data, ErrorType> : SimpleState<Data>() {

    /**
     * The data could not be get because an error occurred.
     */
    data class Error<Data, ErrorType>(

        /**
         * The error that occurred.
         */
        val error: ErrorType

    ) : BasicState<Data, ErrorType>()

    companion object {

        /**
         * Creates an error state.
         *
         * @param error The error that occurred
         */
        fun <Data, ErrorType> error(error: ErrorType) = Error<Data, ErrorType>(error)

    }

}