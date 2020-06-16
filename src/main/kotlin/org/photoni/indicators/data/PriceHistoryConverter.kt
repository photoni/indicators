package org.photoni.indicators.data

import com.trendrating.commons.TimeUtils

object PriceHistoryConverter {
    fun convert(timeSeries: List<Any>, mode: FormatMode): List<Any> {
        var timeSeries1 = timeSeries
        var result: MutableList<Any> = mutableListOf()
        timeSeries1.forEach {
            val list = it as List<Any>
            val v = list[11] as Double
            val d = list[0] as String
            val entry = formatEntry(v, d,mode)
            result.add(entry)
        }
        return result
    }
    fun extractValues(timeSeries: List<Map<String,Any>>): Array<Double> {

        var result :Array<Double> = arrayOfNulls<Double>(timeSeries.size) as Array<Double>
        timeSeries.forEachIndexed { index, element ->
           val y= element["y"];
            result[index]=y as Double
        }
        return result
    }

    private fun formatEntry(y: Double, x: Any, mode: FormatMode): MutableMap<String, Any> {
        val entry = mutableMapOf<String, Any>()
        when (mode) {
            FormatMode.DATEVALUE -> {
                entry["y"] = y
                entry["x"] = x
            }
            FormatMode.XY -> {
                val sbDay=TimeUtils.ISODateToSBDay(x as String)
                entry["y"] = y
                entry["x"] = sbDay
            }
        }
        return entry
    }

    enum class FormatMode {
        DATEVALUE, XY
    }
}