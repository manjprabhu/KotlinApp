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
        operationFive()
    }


    private fun operationOne() = runBlocking {
        supervisorScope {
            launch {
                codeThatCanThrowExceptions()
            }
        }
    }


    private fun operationTwo() = runBlocking {
        try {
            supervisorScope() {
                launch {
                    codeThatCanThrowExceptions()
                }
            }
        } catch (e: Exception) {
            println("==>> Exception :$e  is caught")
        }
    }

    private fun operationThree() = runBlocking {
        supervisorScope {
            async {
                codeThatCanThrowExceptions()
            }.await()
        }
    }

    private fun operationFour() = runBlocking {
        supervisorScope {
            val deferred = async {
                codeThatCanThrowExceptions()
            }

            launch {
                try {
                    deferred.await()
                } catch (e: Exception) {
                    println("==>> Exception$e is caught for Async()...")
                }
            }
        }
    }

    private fun operationFive() = runBlocking {
        supervisorScope {
            val deferred = async {
                codeThatCanThrowExceptions()
            }
            try {
                deferred.await()
            } catch (e: Exception) {
                println("==>> Exception$e is caught for Async()...")
            }
        }

    }

    private fun operationSix() {
        val scope = CoroutineScope(Job())

        scope.launch(SupervisorJob()) {

            val job1 = launch {
                println("==>> Job1 Started...")
                delay(1000)
                throw RuntimeException("Thrown from job1")
                println("==>> Job1 done...")
            }

            val job2 = launch {
                println("==>> Job2 Started...")
                delay(1000)
                println("==>> Job2 done...")
            }

        }
    }

    private fun operationSeven() {
        val scope = CoroutineScope(Job())

        scope.launch {
            supervisorScope {
                val job1 = launch {
                    println("==>> Job1 Started...")
                    delay(1000)
                    throw RuntimeException("Thrown from job1")
                    println("==>> Job1 done...")
                }

                val job2 = launch {
                    println("==>> Job2 Started...")
                    delay(1000)
                    println("==>> Job2 done...")
                }
            }
        }
    }


    private fun exceptionHandlingLaunch() {
        val scope = CoroutineScope(Job())

        lifecycleScope.launch {
            try {
                codeThatCanThrowExceptions()
            } catch (e: Exception) {
                println("==>> Launch() Exception Caught in catch block....")
            }
        }
    }

    private fun exceptionHandlingAsync() {
        val scope = CoroutineScope(Job())

        val deferred = lifecycleScope.async {
            codeThatCanThrowExceptions()
        }

        lifecycleScope.launch {
            try {
                deferred.await()
            } catch (e: Exception) {
                println("==>> Async() Exception Caught in catch block....")
            }
        }
    }

    private fun codeThatCanThrowExceptions() {
        throw RuntimeException("Custom Exception is thrown")
    }

    private fun performOperationOneAsync() {
        val scope = CoroutineScope(SupervisorJob())
        scope.launch {
            val deferred = async {
                throw RuntimeException()
            }

            try {
                deferred.await()
            } catch (e: Exception) {
                println("==>> Exception Caught in catch block....")
            }
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

                val result = listOf(job1, job2, job3).mapNotNull {
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