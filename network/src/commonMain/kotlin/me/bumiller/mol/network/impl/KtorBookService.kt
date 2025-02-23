package me.bumiller.mol.network.impl

import io.ktor.client.HttpClient
import me.bumiller.mol.network.BookService
import me.bumiller.mol.network.model.NetworkResponse
import me.bumiller.mol.network.response.LawBookResponse
import me.bumiller.mol.network.wrapper.performGet

internal class KtorBookService(
    private val client: HttpClient
) : BookService {

    override suspend fun getAll(): NetworkResponse<List<LawBookResponse>> {
        return client.performGet(BASE_PATH)
    }

    companion object {

        private const val BASE_PATH = "law-books/"

    }

}