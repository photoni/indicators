package org.photoni.indicators.analysis

import org.photoni.indicators.analysis.support.Functions

/**
 * Aroon indicator.
 *
 *
 */
object Aroon {
    const val n25 = 25
    private const val upThreshold = 50
    private const val downThreshold = -50
    private const val neutralThreshold = 0


    /**
     * Aroon oscillator signal
     * @param arr
     * @param days
     * @return smooted Stochastic Oscillator
     */
    fun aroonSignal(arr: DoubleArray, days: Int): DoubleArray {
        var aroonSignal = DoubleArray(arr.size) { Double.NEGATIVE_INFINITY }
        var ao = aroonOscillator(arr, days)
        for (i in days - 1 until ao.size) {
            if (ao[i] < downThreshold)
                aroonSignal[i] = 1.0
            else if (ao[i] > downThreshold && ao[i] < upThreshold)
                aroonSignal[i] = 2.0
            else if (ao[i] > upThreshold)
                aroonSignal[i] = 3.0
        }
        return aroonSignal
    }

    /**
     * Aroon
     * @param arr
     * @param days
     */
    fun aroonOscillator(arr: DoubleArray, days: Int): DoubleArray {
        var aroon = DoubleArray(arr.size) { Double.NEGATIVE_INFINITY }
        var ups = up(arr, days)
        var downs = down(arr, days)
        for (i in 0 until arr.size ) {
            var up = ups[i]
            var down = downs[i]
            if (up != Double.NEGATIVE_INFINITY && down != Double.NEGATIVE_INFINITY)
                aroon[i] = ups[i] - downs[i]
        }
        return aroon
    }

    /**
     * Aroon Up
     * @param arr
     * @param days
     */
    fun up(arr: DoubleArray, days: Int): DoubleArray {
        var up = DoubleArray(arr.size) { Double.NEGATIVE_INFINITY }
        for (i in days - 1 until arr.size) {
            var indexOfHigh = Functions.indexOfHighLow(i, days, arr, false)
            up[i] = ((days - (i - indexOfHigh)) / days.toDouble()) * 100
        }

        return up
    }

    /**
     * Aroon Down
     * @param arr
     * @param days
     */
    fun down(arr: DoubleArray, days: Int): DoubleArray {
        var down = DoubleArray(arr.size) { Double.NEGATIVE_INFINITY }
        for (i in days - 1 until arr.size) {
            var indexOfDown = Functions.indexOfHighLow(i, days, arr, true)
            down[i] = ((days - (i - indexOfDown)) / days.toDouble())* 100
        }

        return down
    }




}