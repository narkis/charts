package com.narunas.n26test

import android.app.Application
import com.narunas.n26test.injection.AppModule
import com.narunas.n26test.injection.BlockchainAppComponent
import com.narunas.n26test.injection.DaggerBlockchainAppComponent

class App : Application() {

    lateinit var blockchainChartComponent: BlockchainAppComponent
        protected set

    override fun onCreate() {
        super.onCreate()

        instance = this
        blockchainChartComponent = DaggerBlockchainAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        blockchainChartComponent.inject(this)
    }

    companion object {
        lateinit var instance : App
            private set
    }

}