package com.narunas.n26test.model.pojo

import com.google.gson.annotations.SerializedName

data class ChartResponse(
    @SerializedName("status")
    var status: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("unit")
    var unit : String?,
    @SerializedName("period")
    var period: String?,
    @SerializedName("description")
    var description : String?,
    @SerializedName("values")
    val values: List<ChartValue>
)
