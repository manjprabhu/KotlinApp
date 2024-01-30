package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class StructuredConcurrency : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        methodOne()
    }


    private fun methodOne() {
        val time = measureTimeMillis {

            CoroutineScope(Dispatchers.Main).launch {
                val result = doWorkTwo()
                println("==>> Result : $result")
            }
        }

        println("==>> Operation completed...")
        println("==>> Time taken $time")

    }

    //Example of unStructured concurrency...
    private fun doWork(): Int {
        var result = 0
        CoroutineScope(Dispatchers.IO).launch {
            (1..10).forEach {
                delay(1000)
                result += it
            }
        }
        return result
    }

    //Example of STRUCTURED concurrency...
    private suspend fun doWorkTwo(): Int {
        var result = 0
        coroutineScope {
            (1..10).forEach {
                delay(1000)
                result += it
            }
        }
        return result
    }


    //Another example of STRUCTURED concurrency...
    private fun execute() {
        val time = measureTimeMillis {
            runBlocking {
                val sum = calculateSum()
                println("==>> Sum is : $sum")
            }
        }
        println("==>> Operation completed...")
        println("==>> Time taken $time")
    }

    private suspend fun calculateSum(): Int {
        println("==>> calculateSum")
        var sum = 0
        //This is unstructured, here sum =0  is returned even before coroutine are completed.
        val scope =CoroutineScope(Job()).launch {
                val one = async { taskOne() }
                val two = async { taskTwo() }
                sum = (one.await() + two.await())
        }
        scope.join() // this line makes above code structured concurrent

        //This structured concurrency version of above code
        /*coroutineScope {
            val one = async { taskOne() }
            val two = async { taskTwo() }
            sum = (one.await() + two.await())
        }*/
        return sum
    }

    private suspend fun taskOne(): Int {
        delay(1000)
        return 10
    }

    private suspend fun taskTwo(): Int {
        delay(2000)
        return 20
    }
//example ends here

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