package me.bumiller.civoris.network.impl

import io.ktor.client.HttpClient
import me.bumiller.civoris.network.InvitationService
import me.bumiller.civoris.network.model.NetworkResponse
import me.bumiller.civoris.network.response.BookInvitationResponse
import me.bumiller.civoris.network.wrapper.performGet

internal class KtorInvitationService(
    private val client: HttpClient
) : InvitationService {

    override suspend fun getAll(): NetworkResponse<List<BookInvitationResponse>> {
        return client.performGet(BASE_PATH)
    }

    companion object {

        private const val BASE_PATH = "user/book-invitations/"

    }

}