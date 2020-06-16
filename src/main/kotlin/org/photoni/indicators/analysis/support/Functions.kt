package org.photoni.indicators.analysis.support

object Functions {
    /**
     * @param index the index of the array to start from
     * @param days the number of days to go backward
     * @param arr the array of values
     * @return the index of the high or low in the specified period
     */
    fun indexOfHighLow(index: Int, days: Int, arr: DoubleArray, findLow: Boolean): Int {
        var indexOfHighLow: Int = Integer.MIN_VALUE;
        var highLow: Double = if (findLow) Double.POSITIVE_INFINITY else Double.NEGATIVE_INFINITY
        val start = Math.max(0, index - days + 1)
        val signum = if (findLow) -1 else 1
        for (i in start..index) {

            if ((arr[i] - highLow) * signum > 0) {
                highLow = arr[i]
                indexOfHighLow = i
            }
        }
        return indexOfHighLow
    }

    /**
     * @param index the index of the array to start from
     * @param days the number of days to go backward
     * @param arr the array of values
     * @return the high or low in the specified period
     */
    fun highLow(index: Int, days: Int, arr: DoubleArray, findLow: Boolean): Double {
        var highLow: Double = if (findLow) Double.POSITIVE_INFINITY else Double.NEGATIVE_INFINITY
        val start = Math.max(0, index - days + 1)
        val signum = if (findLow) -1 else 1
        for (i in start..index) {

            if ((arr[i] - highLow) * signum > 0) {
                highLow = arr[i]
            }
        }
        return highLow
    }
}