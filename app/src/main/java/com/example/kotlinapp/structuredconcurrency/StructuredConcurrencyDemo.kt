package com.example.kotlinapp.structuredconcurrency

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class StructuredConcurrencyDemo : AppCompatActivity() {

//    5 rule of structured concurrency
//    1. Every coroutine needs to be started in a logical scope with limited lifetime
//    2. Coroutine started in same scope form a hierarchy
//    3. A parent job wont complete until all of its children have completed

    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onResume() {
        super.onResume()
        performOperationTwo()
    }

    private fun performOperationOne() {
        runBlocking {
            val job = scope.launch {
                delay(100)
                println("==>> Inside coroutine")
            }

            job.invokeOnCompletion {
                if (it is CancellationException) {
                    println("==>> Coroutine job cancelled")
                }
            }
            delay(50)
            finish()
        }

    }


    private fun performOperationTwo() {
        val scopeJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + scopeJob)

        var childCoroutineJob: Job? = null

        val coroutineJob = scope.launch {

            childCoroutineJob = launch {

                println("==>>> Inside child Coroutine")
                delay(1000)
            }

            println("==>>> Inside Coroutine")
            delay(1000)
        }

        Thread.sleep(1000)
        println("==>> Is Coroutine job is child of job:  ${scopeJob.children.contains(coroutineJob)}")
        println("==>> is childCoroutineJob is child of coroutineJob: ${coroutineJob.children.contains(childCoroutineJob)}")
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

}