package org.photoni.indicators.data


import com.trendrating.commons.JsonUtil
import com.trendrating.commons.MapUtil
import org.junit.jupiter.api.Test
import java.util.*

class PriceHistoryConverterTest {

    @Test
    fun convert() {
        val ticker = "MSFT"
        var timeSeries:List<Any> =Repository.loadTimeSeries(ticker)
        val result: List<Any> = PriceHistoryConverter.convert(timeSeries,PriceHistoryConverter.FormatMode.DATEVALUE)
        result.forEach {
            println(it)
        }
    }

    @Test
    fun convertXY() {
        val ticker = "MSFT"
        var timeSeries:List<Any> =Repository.loadTimeSeries(ticker)
        val result: List<Any> = PriceHistoryConverter.convert(timeSeries,PriceHistoryConverter.FormatMode.XY)
        result.forEach {
            println(it)
        }
    }

    @Test
    fun convertAndExtractValues() {
        val ticker = "MSFT"
        var timeSeries:List<Any> =Repository.loadTimeSeries(ticker)
        val timeSeriesConverted: List<Any> = PriceHistoryConverter.convert(timeSeries,PriceHistoryConverter.FormatMode.DATEVALUE)
        var result :Array<Double> = PriceHistoryConverter.extractValues(timeSeriesConverted as List<Map<String, Any>>);
        result.forEach {
            println(it)
        }
    }



}