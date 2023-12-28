package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class ColdFlows : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flowCancellation()
    }

    private fun coldFlow(): Flow<Int> = flow {
        emit(1)
        delay(1000)

        emit(2)
        delay(1000)

        emit(3)
        delay(1000)
    }

    private fun coldFlowTwo(): Flow<Int> = flow {
        println("Generate cold flow ....")
        val list = intArrayOf(1, 2, 3, 4, 5, 6, 78, 9, 10, 20, 30, 40)
        for (i in list.indices) {
            emit(i)
            delay(100)
        }
    }

    // To demonstrate that flows are cold
    private fun coldFlowDemo() = runBlocking {
        println("==>> Started collecting cold flow....")
        val f = coldFlowTwo()
        f.collect {
            println("1: $it")
        }

        println("==>> Started collecting cold flow again....")

        f.collect {
            println("2: $it")
        }
    }

    //Flow will cancelled after 300 ms and only 3 element will be printed...
    private fun flowCancellation() {
        runBlocking {
            withTimeoutOrNull(300) {
                coldFlowTwo().collect { value ->
                    println("==>> $value")
                }
            }
        }
    }

    private fun performColdFlowCancelTest() = runBlocking {
        val job = launch {
            coldFlow().onCompletion {
                println("==>> Flow of collector 1 is completed")
            }.collect {
                println("==>> Received : $it")
            }
        }
        delay(1500)
        job.cancelAndJoin()
    }

    private fun performColdFlowOperation() {
        lifecycleScope.launch {
            launch {
                val coldFlow = coldFlow().collect {
                    println("==>> Received at Collector1 : $it")
                }
            }

            launch {
                val coldFlow = coldFlow().collect {
                    println("==>> Received at Collector2 : $it")
                }
            }
        }
    }

    //returning multiple values
    private fun simple(): List<Int> = listOf(1, 2, 3, 5, 6)

    private

    fun display() {
        simple().forEach {
            println(it)
        }
    }

    fun methodOne(): Flow<Int> = flow {
        for (i in 1..10) {
            emit(i)
            delay(100)
        }
    }


    //Different flow builders

    private fun builderOne(): Flow<Int> = flow {
        for (i in 1..10) {
            emit(i)
            delay(100)
        }
    }

    private fun builderTwo(): Flow<Int> = flowOf(1, 2, 3, 4, 5)

    private fun builderThree(): Flow<Int> = (1..5).asFlow()

    private fun builderFour(): Flow<Int> = flow {
        (1..10).forEach {
            emit(it)
        }
    }

    private fun simple2(): List<Int> = listOf(1, 2, 3, 5, 6)

    private fun builderFive(): Flow<Int> = simple2().asFlow()


}