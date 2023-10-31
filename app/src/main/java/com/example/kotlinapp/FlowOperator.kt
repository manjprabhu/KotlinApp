package com.example.kotlinapp

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class FlowOperator {

    private fun flowBuilderOne(): Flow<Int> = flow {
        for (i in 1..10) {
            emit(i)
            delay(100)
        }
    }

    private fun flowBuilderTwo(): Flow<Int> = flow {
        emit(1)
        delay(100)

        emit(2)
        delay(100)

        emit(3)
        delay(100)

        emit(4)
        delay(100)

        emit(5)
        delay(100)
    }


    //Size limiting operator

    private fun operatorDemo() = runBlocking {
        flowBuilderOne().take(5).collect { println(it) }
    }

    //terminal operator

    private fun operatorTwo() = runBlocking {
        val product = flowBuilderOne().reduce { a, b -> a * b }
        println(product)
    }

}