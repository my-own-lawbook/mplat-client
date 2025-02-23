package me.bumiller.mol.network.impl

import io.ktor.client.HttpClient
import me.bumiller.mol.network.ForeignUserService
import me.bumiller.mol.network.model.NetworkResponse
import me.bumiller.mol.network.response.ForeignUserResponse
import me.bumiller.mol.network.wrapper.performGet

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