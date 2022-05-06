package org.photoni.indicators.analysis

import org.photoni.indicators.analysis.support.Functions

/**
 * Stocastic Oscillator indicator.
 *
 * %K = (Current Close - Lowest Low)/(Highest High - Lowest Low) * 100
 * %D = 3-day SMA of %K
 * Lowest Low = lowest low for the look-back period
 * Highest High = highest high for the look-back period
 * %K is multiplied by 100 to move the decimal point two places
 *
 * SO=%D
 * SSO=3-day SMA of SO
 *
 */
object SSO {
    const val n14 = 14
    const val n3 = 3
    private const val upThreshold = 80
    private const val downThreshold = 20


    /**
     * Slow Stochastic oscillator signal
     * @param arr
     * @param days look-back period
     * @return smoothed Stochastic Oscillator
     */
    fun ssos(arr: DoubleArray, days: Int): DoubleArray {
        var ssos = DoubleArray(arr.size) { Double.NEGATIVE_INFINITY }
        var sso = sso(arr, days)
        for (i in days - 1 until sso.size) {
            if (sso[i] < downThreshold)
                ssos[i] = 1.0
            else if (sso[i] > downThreshold && sso[i] < upThreshold)
                ssos[i] = 2.0
            else if (sso[i] > upThreshold)
                ssos[i] = 3.0
        }
        return ssos
    }

    /**
     * Slow Stochastic oscillator
     * @param arr
     * @param days
     * @return smooted Stochastic Oscillator
     */
    fun sso(arr: DoubleArray, days: Int): DoubleArray {
        var d = so(arr, days)
        return MA.sma(n3, d)
    }

    /**
     * K
     * @param arr
     * @param days
     */
    private fun k(arr: DoubleArray, days: Int): DoubleArray {
        var ks = DoubleArray(arr.size) { Double.NEGATIVE_INFINITY }
        for (i in days - 1 until arr.size) {
            var highestHigh = Functions.highLow(i, days, arr, false)
            var lowestLow = Functions.highLow(i, days, arr, true)
            var currentClose = arr[i]
            var k = (currentClose - lowestLow) / (highestHigh - lowestLow)
            ks[i] = k * 100
        }

        return ks
    }

    /**
     * Stochastic oscillator
     * @param arr
     * @param days
     */
    private fun so(arr: DoubleArray, days: Int): DoubleArray {
        var ks = k(arr, n14)
        return MA.sma(n3, ks)
    }


}