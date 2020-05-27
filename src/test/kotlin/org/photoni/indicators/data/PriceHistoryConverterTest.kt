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
        val result: List<Any> = PriceHistoryConverter.convert(timeSeries)
        result.forEach {
            println(it)
        }
    }


}