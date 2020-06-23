package org.photoni.indicators.data

import com.trendrating.commons.TimeUtils

object PriceHistoryConverter {
    fun convert(timeSeries: List<Any>, mode: FormatMode): ArrayList<Any> {

        var result = ArrayList<Any>(timeSeries.size)
        timeSeries.forEachIndexed { index,it ->
            val list = it as List<Any>
            val v = list[11] as Double
            val d = list[0] as String
            val entry = formatEntry(v, d,mode)
            result[index]=entry
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

    fun extractValuesAsDoubleArray(timeSeries: List<Map<String,Any>>): DoubleArray {

        var result :DoubleArray = DoubleArray(timeSeries.size)
        timeSeries.forEachIndexed { index, element ->
            val y= element["y"];
            result[index]=y as Double
        }
        return result
    }

    fun mergeValues(timeSeries: List<Map<String,Any>>, values : DoubleArray):List<Map<String,Any>> {
        var result = ArrayList<Map<String,Any>>()
        timeSeries.forEachIndexed { index, element ->
            val map= mutableMapOf<String,Any>()
            map.put("y",values[index])
            element.get("x")?.let { map.put("x", it) }
            result[index]=map
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