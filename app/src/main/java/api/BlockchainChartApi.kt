package com.narunas.n26test.api

import com.narunas.n26test.model.pojo.ChartResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface BlockchainChartApi {

    @GET("transactions-per-second")
    fun fetchBlockchainTransactionsPerSeconChartData(
        @Query("timespan") timespan: String,
        @Query("rollingAverage") rollingAverage: String,
        @Query("format") format: String
    ): Observable<ChartResponse>

    @GET("market-price")
    fun fetchBlockchainMarketPriceChartData(
        @Query("daysAverageString") daysAverageString: String,
        @Query("timespan") timespan: String,
        @Query("format") format: String
    ): Observable<ChartResponse>


}