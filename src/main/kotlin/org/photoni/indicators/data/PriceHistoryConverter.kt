package org.photoni.indicators.data

import com.trendrating.commons.TimeUtils
import java.util.*

object PriceHistoryConverter {
    fun convert(timeSeries: List<Any>, mode: FormatMode): LinkedList<Any> {

        var result = LinkedList<Any>()
        timeSeries.forEach {
            val list = it as List<Any>
            val v = list[11] as Double
            val d = list[0] as String
            val entry = formatEntry(v, d, mode)
            result.add(entry)
        }
        return result
    }

    fun extractValues(timeSeries: List<Map<String, Any>>): Array<Double> {

        var result: Array<Double> = arrayOfNulls<Double>(timeSeries.size) as Array<Double>
        timeSeries.forEachIndexed { index, element ->
            val y = element["y"]
            result[index] = y as Double
        }
        return result
    }

    fun extractValuesAsDoubleArray(timeSeries: List<Map<String, Any>>): DoubleArray {

        var result: DoubleArray = DoubleArray(timeSeries.size)
        for ((index, value) in timeSeries.listIterator().withIndex()) {
            val y = value["y"]
            result[index] = y as Double
        }
        return result
    }

    fun mergeValues(timeSeries: List<Map<String, Any>>, values: DoubleArray): List<Map<String, Any>> {
        var result = LinkedList<Map<String, Any>>()
        for ((index, value) in timeSeries.listIterator().withIndex()) {
            val map = mutableMapOf<String, Any>()
            map["y"] = values[index]
            value["x"]?.let { map.put("x", it) }
            result.add(map)
        }
        return result
    }

    /**
     * Formats the entry according to the mode parameter.
     * - with mode is DATEVALUE the entry is untouched
     * - with mode is XY then the x is supposed to be in ISO Unix format YYYY-mm-dd and is transformed into SBDay
     * @param y
     * @param x
     * @param mode How to format
     */
    private fun formatEntry(y: Double, x: Any, mode: FormatMode): MutableMap<String, Any> {
        val entry = mutableMapOf<String, Any>()
        when (mode) {
            FormatMode.DATEVALUE -> {
                entry["y"] = y
                entry["x"] = x
            }
            FormatMode.XY -> {
                val sbDay = TimeUtils.ISODateToSBDay(x as String)
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