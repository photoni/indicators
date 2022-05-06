package org.photoni.indicators.data


import org.junit.jupiter.api.Test

class DataTest {

    @Test
    fun data() {
        val ticker = "AAPL"
        Repository.download(ticker)


    }
}