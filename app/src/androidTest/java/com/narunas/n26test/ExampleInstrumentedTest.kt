package com.narunas.n26test

import androidx.lifecycle.ViewModelProviders
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.narunas.n26test.common.BaseApplicationTest
import com.narunas.n26test.model.pojo.ChartResponse
import model.N26ViewModel
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import ui.activities.MainActivity

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest : BaseApplicationTest<MainActivity>(MainActivity::class.java){


    lateinit var model : N26ViewModel
    @Before
    fun setUp() {

        App.instance.blockchainChartComponent.inject(testRule.activity)
        model = ViewModelProviders.of(testRule.activity).get(N26ViewModel::class.java)

        App.instance.blockchainChartComponent.inject(model)
    }

    @After
    fun cleanUp() {

    }

    @Test
    fun doWeHaveModelView() {

        assertNotNull(" no model available: ", model)
    }

    @Test
    fun fetchDataTest() {
        val daysAverageString = "7"
        val timespan = "all"
        val format = "json"
        val chartResponse = model.service.api.fetchBlockchainMarketPriceChartData(daysAverageString, timespan, format)
        assertNotNull(" no data fetched", chartResponse)
    }

}
