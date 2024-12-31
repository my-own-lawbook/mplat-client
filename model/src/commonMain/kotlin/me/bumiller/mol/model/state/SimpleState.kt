package me.bumiller.mol.model.state

/**
 * Wrapper class for a state that can have a loading state.
 */
sealed class SimpleState<Data> {

    /**
     * The data is still loading.
     */
    class Loading<Data> : SimpleState<Data>()

    /**
     * The data was successfully loaded.
     */
    data class Success<Data>(

        /**
         * The data
         */
        val data: Data

    ) : SimpleState<Data>()

    /**
     * Gets the data, or null if the state is still loading
     *
     * @return The data, or null
     */
    fun dataOrNull(): Data? = (this as? Success)?.data

    companion object {

        /**
         * Creates a loading state
         */
        fun <Data> loading() = Loading<Data>()

        /**
         * Creates a success state
         *
         * @param data The data
         */
        fun <Data> success(data: Data) = Success(data)

    }

}