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
    fun index(model: Model, @RequestParam(required = false) ticker:String?, @RequestParam(required = false) indicators:String?): String {
        val ticker = ticker?:"MSFT"
        val indicatorsList=indicators?.split("'")
        var timeSeries:List<Any> = Repository.loadTimeSeries(ticker,4000)
        val result: List<Any> = PriceHistoryConverter.convert(timeSeries, PriceHistoryConverter.FormatMode.XY)
        var dataset= mutableListOf<Any>()
        dataset.add(result)
        val values=PriceHistoryConverter.extractValuesAsDoubleArray(timeSeries as List<Map<String,Any>>);

        indicatorsList?.forEach({element ->
            var res=DoubleArray(values.size)
            when (element) {
                "sma" -> res=MA.sma(6,values )
            }
            PriceHistoryConverter
        })
        model["dataset"] = JsonUtil.toJson(result)
        return "index"
    }

}