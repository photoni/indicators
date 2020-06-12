package org.photoni.indicators.controller

import com.trendrating.commons.JsonUtil
import org.photoni.indicators.data.PriceHistoryConverter
import org.photoni.indicators.data.Repository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController {

    @GetMapping("/")
    fun index(model: Model): String {
        val ticker = "MSFT"
        var timeSeries:List<Any> = Repository.loadTimeSeries(ticker)
        val result: List<Any> = PriceHistoryConverter.convert(timeSeries, PriceHistoryConverter.FormatMode.XY)
        model["dataset"] = JsonUtil.toJson(result)
        return "index"
    }

}