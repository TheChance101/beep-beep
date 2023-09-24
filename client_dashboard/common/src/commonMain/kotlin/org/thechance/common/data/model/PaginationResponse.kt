package org.thechance.common.data.model

import com.google.gson.annotations.SerializedName

data class PaginationResponse<T>(
    @SerializedName("items") val items: List<T>,
    @SerializedName("total") val total: Int,
)