package org.photoni.indicators.data

import com.trendrating.commons.NetworkUtil
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * gateway for data data provider, defines functions for downloading and extracting data of financial instruments
 *
 *
 */
object Repository {
    private const val URL_TEMPLATE = "https://www.quandl.com/api/v3/datasets/WIKI/%s.json"
    private const val FILE_URL_TEMPLATE = "/var/data/transfer/quandl/%s.json"


    /**
     * Download historical time-series
     * @param ticker
     */
    fun download(ticker: String) {
        val url = URL_TEMPLATE.format(ticker)
        val fileUrl = FILE_URL_TEMPLATE.format(ticker)
        val inputStream: InputStream = NetworkUtil.readURL(url)

        val file = File(fileUrl)

        val fos = FileOutputStream(file)
        inputStream.use { input ->
            fos.use { output -> input.copyTo(output) }
        }

    }
    /**
     * Download historical time-series
     * @param ticker
     */
    fun load(ticker: String):ByteArray {
        val fileUrl = FILE_URL_TEMPLATE.format(ticker)
        val file = File(fileUrl)
        return file.readBytes()
    }


}