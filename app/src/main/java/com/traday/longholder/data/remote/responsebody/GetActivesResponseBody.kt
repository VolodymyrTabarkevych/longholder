package com.traday.longholder.data.remote.responsebody

import com.google.gson.annotations.SerializedName
import com.traday.longholder.data.remote.dto.ActiveDto

data class GetActivesResponseBody(
    @SerializedName("value") val actives: List<ActiveDto>
)