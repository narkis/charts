package model

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.narunas.n26test.App
import com.narunas.n26test.api.BlockchainChartService
import com.narunas.n26test.model.FetchParams
import com.narunas.n26test.model.FetchType
import com.narunas.n26test.model.pojo.ChartResponse
import com.narunas.n26test.model.pojo.ChartValue
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class N26ViewModel : ViewModel() {

    @Inject
    lateinit var service: BlockchainChartService

    var chartData = MutableLiveData<ChartResponse>()
    var paramData = MutableLiveData<FetchParams>()


    init {

        App.instance.blockchainChartComponent.inject(this)

    }

    fun populateChart() {

        when(paramData.value?.type) {
            FetchType.MARKET_PRICE -> {
                fetchBlockchainMarketData()
            }
            FetchType.TRANSACTION_AVERAGE -> {
                fetchBlockchainTransactionsPerSecondChartData()
            }
        }
    }


    private fun fetchBlockchainTransactionsPerSecondChartData() {

        paramData.value?.let { params ->

            val responseFlowable = service.fetchBlockchainTransactionsPerSecondChart(
                params.timespan,
                params.rollingAverage).toFlowable(BackpressureStrategy.DROP)
                .onBackpressureBuffer()
            responseFlowable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .onExceptionResumeNext(Flowable.just(getErrorChartResponse()))
                .onErrorResumeNext(Flowable.just(getErrorChartResponse()))
                .doOnError{ Flowable.just(getErrorChartResponse())}
                .subscribe {
                    it?.let { response ->

                        chartData.postValue(response)
                    }
                }

        } ?: throw Exception(" unable to proceed without parameters")
    }

    @SuppressLint("CheckResult")
    private fun fetchBlockchainMarketData() {

        paramData.value?.let { params ->

            val responseFlowable = service.fetchBlockchainPriceForPeriod(
                params.daysAverageString,
                params.timespan).toFlowable(BackpressureStrategy.DROP)
                .onBackpressureBuffer()
            responseFlowable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .onExceptionResumeNext(Flowable.just(getErrorChartResponse()))
                .onErrorResumeNext(Flowable.just(getErrorChartResponse()))
                .doOnError{ Flowable.just(getErrorChartResponse())}
                .subscribe {
                    it?.let { response ->
                        if(response.name != null) {
                            chartData.postValue(response)
                        } else {
                            /** handle error **/
                        }
                    }
                }
        } ?: throw Exception(" unable to proceed without parameters")

    }

    private fun getErrorChartResponse() :ChartResponse {

        val empty = ChartValue(0.0, 0.0)
        val emptyList = List(1){empty}
        return ChartResponse(
            null, null, null, null,null, emptyList)
    }
}