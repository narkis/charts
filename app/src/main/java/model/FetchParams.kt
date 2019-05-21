package com.narunas.n26test.model

data class FetchParams(val type: FetchType) {

    var timespan: String = ""
    var daysAverageString: String = ""
    var rollingAverage: String = ""

}