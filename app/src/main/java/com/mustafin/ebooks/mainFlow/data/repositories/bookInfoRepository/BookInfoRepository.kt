package com.mustafin.ebooks.mainFlow.data.repositories.bookInfoRepository

import com.mustafin.ebooks.core.domain.enums.ResponseStatus
import com.mustafin.ebooks.mainFlow.domain.models.BookInfoModel

interface BookInfoRepository {
    suspend fun getBookInfoByFragment(fragment: String): Pair<ResponseStatus, BookInfoModel?>
}