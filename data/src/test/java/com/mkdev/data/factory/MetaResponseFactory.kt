package com.mkdev.data.factory

import com.mkdev.data.datasource.remote.model.response.base.MetaResponse

object MetaResponseFactory {
    fun createMetaResponse(
        page: Int? = 1,
        pages: Int? = 10,
        pageSize: Int? = 20,
        records: Int? = 200,
        message: String? = "Success"
    ) = MetaResponse(page, pages, pageSize, records, message)
}