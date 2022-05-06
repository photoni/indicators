package org.photoni.indicators

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IndicatorsApplication

fun main(args: Array<String>) {

	try {
		runApplication<IndicatorsApplication>(*args)
	}catch (e: Exception){
		e.printStackTrace()
	}


}
