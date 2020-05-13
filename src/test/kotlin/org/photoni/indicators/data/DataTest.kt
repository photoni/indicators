package org.photoni.indicators.data


import com.trendrating.commons.NetworkUtil
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class DataTest {

    @Test
    fun data() {
        var inputStream: InputStream = NetworkUtil.readURL("https://www.quandl.com/api/v3/datasets/WIKI/AAPL.json");

        var file: File = File("/var/data/transfer/quandl/AAPL.json");

        var fos = FileOutputStream(file)
        inputStream.use { input ->
            fos.use { output -> input.copyTo(output) }
        }


    }
}