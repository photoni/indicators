package org.photoni.indicators.controller

import com.trendrating.commons.JsonUtil
import org.photoni.indicators.analysis.MA
import org.photoni.indicators.analysis.ZigZag
import org.photoni.indicators.data.PriceHistoryConverter
import org.photoni.indicators.data.Repository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
class HtmlController {

    @GetMapping("/")
    fun index(model: Model, @RequestParam(required = false) ticker: String?, @RequestParam(required = false) overlays: String?): String {
        val ticker = ticker ?: "MSFT"
        val overlaysList = overlays?.split(",")
        var timeSeries: List<Any> = Repository.loadTimeSeries(ticker, 4000)
        val price: List<Any> = PriceHistoryConverter.convert(timeSeries, PriceHistoryConverter.FormatMode.XY)
        var dataset = LinkedList<Any>()
        dataset.add(price)
        val values = PriceHistoryConverter.extractValuesAsDoubleArray(price as List<Map<String, Any>>);

        computeOverlays(overlaysList, values, price as List<Map<String, Any>>, dataset)
        model["datasetOverlays"] = JsonUtil.toJson(dataset).replace("-Infinity", "0")
        return "index"
    }

    /**
     * Computes the overlays specified in the [overlaysList] on the array of [values].
     * Adds the result to the [dataset] with a format based on the [price] timeseries template
     */
    private fun computeOverlays(overlaysList: List<String>?, values: DoubleArray, price: List<Map<String, Any>>, dataset: MutableList<Any>) {
        overlaysList?.forEach { element ->
            var indicator = DoubleArray(values.size)
            if (element.startsWith("sma")) {
                val tokens = element.split("_")
                val n = Integer.parseInt(tokens[1])
                indicator = MA.sma(n, values)
            } else if (element.startsWith("ema")) {
                val tokens = element.split("_")
                val n = Integer.parseInt(tokens[1])
                indicator = MA.ema(n, values)
            } else if (element.startsWith("zigzag")) {
               ZigZag.zigZag(values)
                indicator=values
            }



            var indicatorPriceHistory = PriceHistoryConverter.mergeValues(price, indicator)
            dataset.add(indicatorPriceHistory)
        }
    }

}