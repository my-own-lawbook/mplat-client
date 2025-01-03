package me.bumiller.mol.network.model

/**
 * Wrapper class for any network request.
 *
 * @param Data The response body, serialized
 */
sealed class NetworkResponse<Data> {

    /**
     * The request was successful.
     *
     * @param data The data
     */
    data class Success<Data>(val data: Data) : NetworkResponse<Data>()

    /**
     * The request was performed successfully, but the server responded with an error http code.
     *
     * @param code The http error code
     * @param info The information that was passed an an error description
     */
    data class HttpError<Data>(val code: Int, val info: ErrorInfo) : NetworkResponse<Data>()

    /**
     * The request was not sent successfully, an error occurred on the local device.
     *
     * @param exception The exception that prevented the request from being sent.
     */
    data class NetworkError<Data>(val exception: Exception) : NetworkResponse<Data>()

    /**
     * Maps the possibly data to another type.
     *
     * @param mapper The conversion function
     */
    fun <Data2> map(mapper: (Data) -> Data2): NetworkResponse<Data2> = when (this) {
        is Success -> Success(mapper(data))
        is HttpError -> HttpError(code, info)
        is NetworkError -> NetworkError(exception)
    }

    /**
     * Returns the data, or null if the data is not available.
     *
     * @return Null if the response was an error, false otherwise.
     */
    fun dataOrNull(): Data? = if (this is Success) data else null

}