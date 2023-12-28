package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class FourthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testFunctionParalalley()
    }


    private fun testFunction() {
        lifecycleScope.launch {
            val time = System.currentTimeMillis()

            val r1 = launch {
                networkCall(1)
                println("==>> Network call 1 completed at ${getTime(time)}")
            }

            val r2 = launch {
                networkCall(2)
                println("==>> Network call 2 completed at ${getTime(time)}")
            }

            //Main thread will wait until both th coroutine gets completed
            r1.join()
            r2.join()

            println("==>> Message after launch coroutines ")
        }
    }

    private fun testFunctionSequentially() {
        val time = System.currentTimeMillis()
        lifecycleScope.launch {
            val job1 = networkCall(1)
            val job2 = networkCall(2)
            println("==>> Message outside coroutines ${getTime(time)}")
        }
    }

    private fun testFunctionParalalley() {
        val startTime = System.currentTimeMillis()

        lifecycleScope.launch {

            val deferrerd1 = async {
                networkCall(1)
                println("==>> Network call 1 completed at ${getTime(startTime)}")
            }
            val deferrerd2 = async {
                networkCall(2)
                println("==>> Network call 2 completed at ${getTime(startTime)}")
            }
            println(
                "==>> Result of the computation ${deferrerd1.await()} + ${deferrerd2.await()}   total time taken ${
                    getTime(
                        startTime
                    )
                }"
            )
        }
    }

    private fun testFunctionLazyStart() {
        val startTime = System.currentTimeMillis()
        lifecycleScope.launch {

            val result1 = async(start = CoroutineStart.LAZY) {
                networkCall(1)
                println("==>> Network call 1 completed at ${getTime(startTime)}")
            }

            val result2 = async {
                networkCall(1)
                println("==>> Network call  2 completed at ${getTime(startTime)}")
            }

            delay(5000)
            result1.start()
        }

    }


    private suspend fun networkCall(value: Int): String {
        delay(3000)
        return "Return $value"
    }

    private fun getTime(time: Long) = System.currentTimeMillis() - time

    //Sequential execution

    private fun sequentialDemo() = runBlocking {
        val time = measureTimeMillis {
            val one = methodOne()
            val two = methodTwo()
            println("Sum is : ${one + two}")
        }

        println("Total time taken for execution is : $time")
    }

    private suspend fun methodOne(): Int {
        delay(1000)
        return 10
    }

    private suspend fun methodTwo(): Int {
        delay(1000)
        return 20
    }
}