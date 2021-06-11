package org.photoni.indicators.analysis

import kotlin.math.abs

/**
 * ZigZag
 * Filters out movements smaller than a threshold
 *
 */
object ZigZag {
    private const val threshold = 0.006


    /**
     * Zig Zag
     * @param arr
     */
    fun zigZag(arr: DoubleArray) {
       var lastValue =arr[0]
       var lastIndex=0
       for ( i in 1 until arr.size){
           if((abs((arr[i]-lastValue)/lastValue))< threshold)
               arr[i]=Double.NEGATIVE_INFINITY
           else{

               val steps = (i - lastIndex)
               val stepValue=(arr[i]-lastValue)/steps

               for ( j in i-steps+1 until i){
                   arr[j]=arr[j-1]+stepValue
               }

               lastValue=arr[i]
               lastIndex=i
           }
       }
    }

}