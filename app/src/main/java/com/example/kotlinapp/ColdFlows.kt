package com.example.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ColdFlows : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
    }

    private fun coldFlow(): Flow<Int> = flow {
        emit(1)
        delay(1000)

        emit(2)
        delay(1000)

        emit(3)
        delay(1000)
    }

    private fun coldFlowTwo() : Flow<Int> = flow {
        println("Generate cold flow ....")
        for(i in 1..10) {
            emit(i)
            delay(100)
        }
    }

    // To demonstrate that flows are cold
    private fun coldFlowDemo() = runBlocking {
        println("Started collecting cold flow....")
        val f = coldFlowTwo()
        f.collect {
            println(it)
        }

        println("Started collecting cold flow again....")

        f.collect {
            println(it)
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
}