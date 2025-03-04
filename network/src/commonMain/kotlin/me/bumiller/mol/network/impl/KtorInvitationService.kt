package me.bumiller.mol.network.impl

import io.ktor.client.HttpClient
import me.bumiller.mol.network.InvitationService
import me.bumiller.mol.network.model.NetworkResponse
import me.bumiller.mol.network.response.BookInvitationResponse
import me.bumiller.mol.network.wrapper.performGet

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