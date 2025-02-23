package me.bumiller.mol.network.impl

import io.ktor.client.HttpClient
import me.bumiller.mol.network.SectionService
import me.bumiller.mol.network.model.NetworkResponse
import me.bumiller.mol.network.response.LawSectionResponse
import me.bumiller.mol.network.wrapper.performGet

internal class KtorSectionService(
    private val client: HttpClient
) : SectionService {

    override suspend fun getAll(): NetworkResponse<List<LawSectionResponse>> {
        return client.performGet(BASE_PATH)
    }

    companion object {

        private const val BASE_PATH = "law-sections/"

    }

}