package com.narunas.n26test.api

import com.narunas.n26test.model.pojo.ChartResponse
import io.reactivex.Observable
import javax.inject.Inject

class BlockchainChartService @Inject constructor() {

    companion object {
        const val format = "json"
    }

    @Inject
    lateinit var api: BlockchainChartApi

    fun fetchBlockchainTransactionsPerSecondChart(
        timeSpan: String,
        rollingAverage: String
    ): Observable<ChartResponse> {

        return api.fetchBlockchainTransactionsPerSeconChartData(timeSpan, rollingAverage, format)
    }

    fun fetchBlockchainPriceForPeriod(
        daysAverageString: String,
        timeSpan: String
    ) : Observable<ChartResponse> {

        return api.fetchBlockchainMarketPriceChartData(daysAverageString, timeSpan, format)
    }

}