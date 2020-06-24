package org.photoni.indicators.controller

import com.trendrating.commons.JsonUtil
import org.photoni.indicators.analysis.MA
import org.photoni.indicators.data.PriceHistoryConverter
import org.photoni.indicators.data.Repository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class HtmlController {

    @GetMapping("/")
    fun index(model: Model, @RequestParam(required = false) ticker: String?, @RequestParam(required = false) overlays: String?): String {
        val ticker = ticker ?: "MSFT"
        val overlaysList = overlays?.split(",")
        var timeSeries: List<Any> = Repository.loadTimeSeries(ticker, 4000)
        val price: List<Any> = PriceHistoryConverter.convert(timeSeries, PriceHistoryConverter.FormatMode.XY)
        var dataset = mutableListOf<Any>()
        dataset.add(price)
        val values = PriceHistoryConverter.extractValuesAsDoubleArray(price as List<Map<String, Any>>);

        computeOverlays(overlaysList, values, price as List<Map<String, Any>>, dataset)
        model["dataset"] = JsonUtil.toJson(dataset).replace("-Infinity","0")
        return "index"
    }

    /**
     * Computes the overlays specified in the [overlaysList] on the array of [values].
     * Adds the result to the [dataset] with a format based on the [price] timeseries template
     */
    private fun computeOverlays(overlaysList: List<String>?, values: DoubleArray, price: List<Map<String, Any>>, dataset: MutableList<Any>) {
        overlaysList?.forEach { element ->
            var indicator = DoubleArray(values.size)
            when (element) {
                "sma_5" -> {
                    indicator = MA.sma(5, values)

                }
                "sma_10" -> {
                    indicator = MA.sma(10, values)

                }
                "sma_20" -> {
                    indicator = MA.sma(20, values)

                }
                "sma_40" -> {
                    indicator = MA.sma(20, values)

                }
            }

            var indicatorPriceHistory = PriceHistoryConverter.mergeValues(price, indicator)
            dataset.add(indicatorPriceHistory)
        }
    }

}