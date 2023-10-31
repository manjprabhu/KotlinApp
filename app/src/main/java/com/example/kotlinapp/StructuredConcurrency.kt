package com.example.kotlinapp

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StructuredConcurrency {

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