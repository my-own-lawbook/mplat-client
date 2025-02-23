package me.bumiller.mol.network.impl

import io.ktor.client.HttpClient
import me.bumiller.mol.network.EntryService
import me.bumiller.mol.network.model.NetworkResponse
import me.bumiller.mol.network.response.LawEntryResponse
import me.bumiller.mol.network.wrapper.performGet

internal class KtorEntryService(
    private val client: HttpClient
) : EntryService {

    override suspend fun getAll(): NetworkResponse<List<LawEntryResponse>> {
        return client.performGet(BASE_PATH)
    }

    companion object {

        private const val BASE_PATH = "law-entries/"

    }

}