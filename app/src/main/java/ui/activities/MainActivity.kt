package ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.narunas.n26test.App
import com.narunas.n26test.R
import com.narunas.n26test.model.FetchParams
import com.narunas.n26test.model.FetchType
import model.N26ViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.blockchainChartComponent.inject(this)
        setContentView(R.layout.activity_main)

        when (savedInstanceState) {
            null -> {

                val model = ViewModelProviders.of(this).get(N26ViewModel::class.java)

                /** initializing params - these could also be retrieved from persistence **/
                val initParams = FetchParams(FetchType.MARKET_PRICE)
                initParams.daysAverageString = "7"
                initParams.timespan = "720days"
                model.paramData.value = initParams
            }
        }

        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener(object :
            NavController.OnDestinationChangedListener {

            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                supportActionBar?.let {
                    title = destination.label
                }
            }
        })
    }
}
