package com.example.kotlinapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CoroutineBasics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    fun demo() = runBlocking {
        launch {
            delay(1000L)
            println("One")
        }
        println("Second")
    }

    fun demoOne() {
        runBlocking {
            calculate()
        }
        println("Done.....")
    }

    private suspend fun calculate() {
        coroutineScope {
            launch {
                delay(1000L)
                println("One...")
            }

            launch {
                delay(2000L)
                println("Two...")
            }

            println("Three....")
        }
    }

    fun demoTwo() {
        runBlocking {
            val job = launch {
                delay(100L)
                println("Two...")
            }
            println("One......")
            job.join()
            println("Three.....")
        }
    }

    fun demoThree() {
        runBlocking {
            repeat(1000) {
                launch {
                    delay(100L)
                    println("..")
                }
            }
        }
        println("Done......")
    }
}