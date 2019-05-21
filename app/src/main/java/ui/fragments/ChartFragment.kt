package ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.core.content.res.FontResourcesParserCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.narunas.n26test.App
import com.narunas.n26test.R
import com.narunas.n26test.model.FetchParams
import com.narunas.n26test.model.FetchType
import com.narunas.n26test.model.pojo.ChartResponse
import kotlinx.android.synthetic.main.fragment_chart.*
import model.N26ViewModel

class ChartFragment : Fragment() {

    lateinit var model: N26ViewModel

    private var chartDataObserver = Observer<ChartResponse> {
        it?.let { chartResponse ->
            processData(chartResponse)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let { mainActivity ->

            model = ViewModelProviders.of(mainActivity).get(N26ViewModel::class.java)

        } ?: throw Exception("parent Activity viewModel is not available")

        App.instance.blockchainChartComponent.inject(this)

        when(savedInstanceState) {
            null -> {
                model.populateChart()
            }
        }

        setUpButtons()

    }

    override fun onResume() {
        super.onResume()
        model.chartData.observe(this, chartDataObserver)
    }

    override fun onPause() {
        super.onPause()
        model.chartData.removeObserver(chartDataObserver)
    }

    private fun processData(response: ChartResponse) {

        chart.update(response)
    }

    private fun setUpButtons() {

        selectButton1.text = resources.getString(R.string.B180Days)
        selectButton1.setOnClickListener { handleButtonClick(it) }

        selectButton2.text = resources.getString(R.string.B1Year)
        selectButton2.setOnClickListener { handleButtonClick(it) }

        selectButton3.text = resources.getString(R.string.B2Years)
        selectButton3.setOnClickListener { handleButtonClick(it) }

        selectButton4.text = resources.getString(R.string.BAllTime)
        selectButton4.setOnClickListener { handleButtonClick(it) }
    }

    private fun handleButtonClick(v:View) {

        val params = FetchParams(FetchType.MARKET_PRICE)
        when(v.id) {
            selectButton1.id -> {
                params.timespan = resources.getString(R.string.t180)
            }
            selectButton2.id -> {
                params.timespan = resources.getString(R.string.t1year)
            }
            selectButton3.id -> {
                params.timespan = resources.getString(R.string.t2year)
            }
            selectButton4.id -> {
                params.timespan = resources.getString(R.string.tAll)
            }
        }

        params.daysAverageString = "7"

        model.paramData.value = params

        model.populateChart()
    }

}