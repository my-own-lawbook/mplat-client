package me.bumiller.civoris.network.impl

import io.ktor.client.HttpClient
import me.bumiller.civoris.network.BookService
import me.bumiller.civoris.network.model.NetworkResponse
import me.bumiller.civoris.network.response.LawBookResponse
import me.bumiller.civoris.network.wrapper.performGet

internal class KtorBookService(
    private val client: HttpClient
) : BookService {

    override suspend fun getAll(): NetworkResponse<List<LawBookResponse>> {
        return client.performGet(BASE_PATH)
    }

    companion object {

        private const val BASE_PATH = "user/law-books/"

    }

}