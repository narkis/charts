package com.narunas.n26test.injection

import com.narunas.n26test.App
import dagger.Component
import model.N26ViewModel
import ui.activities.MainActivity
import ui.fragments.ChartFragment
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, HttpModule::class])
interface BlockchainAppComponent {

    fun inject(app: App)

    fun inject(activity: MainActivity)

    fun inject(fragment: ChartFragment)

    fun inject(viewModel: N26ViewModel)
}