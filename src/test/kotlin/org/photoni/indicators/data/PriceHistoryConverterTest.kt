package org.photoni.indicators.data


import com.trendrating.commons.JsonUtil
import com.trendrating.commons.MapUtil
import org.junit.jupiter.api.Test
import java.util.*

class PriceHistoryConverterTest {

    @Test
    fun convert() {
        val ticker = "MSFT"
        val historyBytes=Repository.load(ticker)
        val historyString= String(historyBytes)
        val history=PriceHistoryConverter.convert(historyString);
        var timeSeries:List<Any> = MapUtil.extractT(history,"dataset","data")
        timeSeries=timeSeries.reversed()
        var result : MutableList<Any> = mutableListOf()
        timeSeries.forEach { it->
            val list = it as List<Any>
            val entry = mutableMapOf<String, Any>()
            entry["v"] = list[11] as Double
            entry["d"] = list[0] as String
            result.add(entry)
        }
        result.forEach {
            println(it)
        }
    }
}