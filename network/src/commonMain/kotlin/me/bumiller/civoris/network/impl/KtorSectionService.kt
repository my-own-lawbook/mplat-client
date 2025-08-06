package me.bumiller.civoris.network.impl

import io.ktor.client.HttpClient
import me.bumiller.civoris.network.SectionService
import me.bumiller.civoris.network.model.NetworkResponse
import me.bumiller.civoris.network.response.LawSectionResponse
import me.bumiller.civoris.network.wrapper.performGet

internal class KtorSectionService(
    private val client: HttpClient
) : SectionService {

    override suspend fun getByParent(parentId: Long): NetworkResponse<List<LawSectionResponse>> {
        return client.performGet(PARENT_BASE_PATH.replace(PARENT_ID_PARAM, parentId.toString()))
    }

    override suspend fun getAll(): NetworkResponse<List<LawSectionResponse>> {
        return client.performGet(BASE_PATH)
    }

    companion object {

        private const val BASE_PATH = "user/law-sections/"

        private const val PARENT_ID_PARAM = "{entryId}"

        private const val PARENT_BASE_PATH = "law-entries/$PARENT_ID_PARAM/law-sections/"

    }

}