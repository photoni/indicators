package org.photoni.indicators.data

import com.trendrating.commons.JsonUtil

object PriceHistoryConverter {
    fun convert(timeSeries: List<Any>): List<Any> {
        var timeSeries1 = timeSeries
        timeSeries1 = timeSeries1.reversed()
        var result: MutableList<Any> = mutableListOf()
        timeSeries1.forEach {
            val list = it as List<Any>
            val v = list[11] as Double
            val d = list[0] as String
            val entry = formatEntry(v, d)
            result.add(entry)
        }
        return result
    }

    private fun formatEntry(y: Double, x: Any): MutableMap<String, Any> {
        val entry = mutableMapOf<String, Any>()
        entry["v"] = y
        entry["d"] = x
        return entry
    }
}