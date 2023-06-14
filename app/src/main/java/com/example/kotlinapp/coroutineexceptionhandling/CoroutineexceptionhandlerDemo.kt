package com.example.kotlinapp.coroutineexceptionhandling

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class CoroutineexceptionhandlerDemo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        performOperationOne()
    }


    fun performOperationOne() {

        val scope = CoroutineScope(Job())

        scope.launch {
            throw RuntimeException()
        }
    }

    fun performExceptionHandling() {

        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("==>> Caught $throwable by exception handler")
        }

        val scope = CoroutineScope(Job() + exceptionHandler)
        scope.launch {
            throw RuntimeException()
        }
    }

    private fun multipleCoroutineOperation() {

        lifecycleScope.launch {

            supervisorScope {
                val job1 = async { functionOne() }

                val job2 = async { functionTwo() }

                val job3 = async { functionThree() }

                try {
                    println("==>> Job1 : ${job1.await()}")
                } catch (e: Exception) {
                    println("==>> Caught exception in $e")
                }

                try {
                    println("==>> Job2 : ${job3.await()}")
                } catch (e: Exception) {
                    println("==>> Job2 Caught exception in $e")
                }

                try {
                    println("==>> Job3 : ${job2.await()}")
                } catch (e: Exception) {
                    println("==>>Job3  Caught exception in $e")
                }
            }
        }
    }


    private fun multipleCoroutineOperationTwo() {

        lifecycleScope.launch {

            supervisorScope {
                val job1 = async { functionOne() }

                val job2 = async { functionTwo() }

                val job3 = async { functionThree() }

                val result= listOf(job1, job2, job3).mapNotNull {
                    try {
                        it.await()
                    } catch (e: Exception) {
                        println("==>> Caught exception in $e")
                        null
                    }
                }

                println("==>> Result of computation: $result")

            }
        }
    }

    private suspend fun functionOne() {
        delay(500)
        throw RuntimeException("functionOne exception")
    }

    private suspend fun functionTwo(): String {
        delay(400)
        return "functionTwo"
    }

    private suspend fun functionThree(): String {
        delay(400)
        return "functionThree"
    }
}