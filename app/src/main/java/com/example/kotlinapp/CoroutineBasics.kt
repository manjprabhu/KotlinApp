package com.example.kotlinapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

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

    //Running 1000 coroutine
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

    // Unconfied dispatchers example
    fun demoFour() {
        runBlocking {
            println("Run blocking  ${Thread.currentThread()}")
            launch(Dispatchers.Unconfined) {
                println("Before suspension ... ${Thread.currentThread()}")
                delay(5000L)
                println("After suspension ${Thread.currentThread()}")

            }
        }
    }

    //Print coroutine job from context element
    fun demoFive() {
        runBlocking {
            launch {
                delay(1000L)
                println("This coroutine is running on job  ${coroutineContext.job}")
            }
        }
    }


    //Children of a coroutine

    fun demoSix() {
        runBlocking {

            val request = launch {
                launch {
                    delay(100L)
                    println("Child of REQUEST coroutine....")
                    delay(1000L)
                    println("Parent is cancelled so cancel this ....")
                }
                launch(Job()) {
                    delay(100L)
                    println("Independent coroutine...")
                    delay(1000L)
                    println("Still running after cancellation of  REQUEST coroutine ")
                }
            }

            delay(500L)
            request.cancel()
            println("Cancelled the coroutine....")
            delay(1000L)
        }
    }

    //Parent coroutine waits for child coroutine to complete...
    fun demoSeven() {
        runBlocking {
            val parent = launch {
                repeat(5) { i ->
                    launch {
                        delay((i + 1) * 200L)
                        println(i)
                    }
                }
                println("Parent coroutine nothing to execute, just waiting for child coroutine to complete..")
            }

            parent.join()
            println("Completed all the child coroutine....")
        }
    }

}