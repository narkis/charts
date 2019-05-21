package com.narunas.n26test.model.pojo

import com.google.gson.annotations.SerializedName

data class ChartValue(
    @SerializedName("x")
    var x: Double,
    @SerializedName("y")
    var y: Double) {


}