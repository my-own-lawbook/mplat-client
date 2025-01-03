package me.bumiller.mol.network.wrapper

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import me.bumiller.mol.network.model.ErrorInfo
import me.bumiller.mol.network.model.NetworkResponse

/**
 * Performs a get request.
 *
 * @param url The url to which to send the request
 * @param body The request body
 */
suspend inline fun <reified Body> HttpClient.performGet(
    url: String,
    body: Any
): NetworkResponse<Body> = performRequest {
    get(url) {
        setBody(body)
    }
}

/**
 * Performs a post request.
 *
 * @param url The url to which to send the request
 * @param body The request body
 */
suspend inline fun <reified Body> HttpClient.performPost(
    url: String,
    body: Any
): NetworkResponse<Body> = performRequest {
    post(url) {
        setBody(body)
    }
}

/**
 * Wraps the call of a ktor-http request in order to create a [NetworkResponse].
 *
 * @param block The block in which the request is to be made.
 * @param Body The expected body type.
 */
suspend inline fun <reified Body> performRequest(block: () -> HttpResponse): NetworkResponse<Body> =
    try {
        val response = block()

        if (response.status.value in 200..299) {
            val body = response.body<Body>()
            NetworkResponse.Success(body)
        } else {
            val errorInfo = response.resolveErrorInfo()
            NetworkResponse.HttpError(response.status.value, errorInfo)
        }
    } catch (e: Exception) {
        NetworkResponse.NetworkError(e)
    }

private const val KeyErrorType = "errorType"
private const val KeyErrorInfo = "info"

private const val ErrorTypeUnauthorized = "unauthorized"
private const val ErrorTypeBadFormat = "bad_format"
private const val ErrorTypeBad = "bad"
private const val ErrorTypeForbidden = "forbidden"
private const val ErrorTypeNotFound = "not_found"
private const val ErrorTypeNotFoundIdentifier = "not_found_identifier"
private const val ErrorTypeConflict = "conflict"
private const val ErrorTypeConflictUnique = "conflict_unique"
private const val ErrorTypeInternal = "internal"

suspend fun HttpResponse.resolveErrorInfo(): ErrorInfo {
    val body = bodyAsText()
    val json = Json.parseToJsonElement(body).jsonObject

    val errorType = json[KeyErrorType]?.jsonPrimitive?.content
    val errorInfo = json[KeyErrorInfo]?.jsonObject

    return when (errorType) {
        ErrorTypeBad,
        ErrorTypeUnauthorized,
        ErrorTypeForbidden,
        ErrorTypeNotFound,
        ErrorTypeConflict,
        ErrorTypeInternal -> Json.decodeFromJsonElement<ErrorInfo.DescriptionInfo>(errorInfo!!)

        ErrorTypeBadFormat -> Json.decodeFromJsonElement<ErrorInfo.BadFormatInfo>(errorInfo!!)
        ErrorTypeNotFoundIdentifier -> Json.decodeFromJsonElement<ErrorInfo.NotFoundIdentifierInfo>(
            errorInfo!!
        )

        ErrorTypeConflictUnique -> Json.decodeFromJsonElement<ErrorInfo.ConflictUniqueInfo>(
            errorInfo!!
        )

        null -> ErrorInfo.NoInfo(body)
        else -> ErrorInfo.NoInfo("Received unknown error type: '$errorType' with error info '$errorInfo'")
    }
}