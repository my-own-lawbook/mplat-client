package me.bumiller.civoris.network.impl

import io.ktor.client.HttpClient
import me.bumiller.civoris.network.ForeignUserService
import me.bumiller.civoris.network.model.NetworkResponse
import me.bumiller.civoris.network.response.ForeignUserResponse
import me.bumiller.civoris.network.wrapper.performGet

internal class KtorForeignUserService(
    private val client: HttpClient
) : ForeignUserService {

    override suspend fun getAll(): NetworkResponse<List<ForeignUserResponse>> {
        return client.performGet(BASE_PATH)
    }

    companion object {

        private const val BASE_PATH = "users/"

    }

}