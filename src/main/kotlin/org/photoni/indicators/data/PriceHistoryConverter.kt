package org.photoni.indicators.data

import com.trendrating.commons.JsonUtil

object PriceHistoryConverter {
    fun convert(history: String) : Map<String,Any>{
        return JsonUtil.fromJackson(history,Map::class.java) as Map<String, Any>
    }
}