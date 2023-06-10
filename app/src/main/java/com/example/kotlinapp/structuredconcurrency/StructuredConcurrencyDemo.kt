package com.example.kotlinapp.structuredconcurrency

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class StructuredConcurrencyDemo : AppCompatActivity() {

//    5 rule of structured concurrency
//    1. Every coroutine needs to be started in a logical scope with limited lifetime
//    2. Coroutine started in same scope form a hierarchy
//    3. A parent job wont complete until all of its children have completed
//    4. Cancelling a parent will cancel all children. Cancelling a child wont cancel  the parent or siblings
//    5. If a child coroutine fails , the exception is propagated upwards & depending on the job type either all siblings are cancelled or not

    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onResume() {
        super.onResume()
        performOperationFive_2()
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
        println(
            "==>> is childCoroutineJob is child of coroutineJob: ${
                coroutineJob.children.contains(
                    childCoroutineJob
                )
            }"
        )
    }


    private fun performOperationThree() = runBlocking {

        val scope = CoroutineScope(Dispatchers.Default)

        val parentJob = scope.launch {

            launch {
                delay(1000)
                println("==>> Child coroutine 1 is completed")
            }

            launch {
                delay(1000)
                println("==>> Child coroutine 2 is completed")
            }
        }

        parentJob.join()
        println("==>> Parent coroutine is completed")
    }

    private fun performOperationFour() = runBlocking {

        val scope = CoroutineScope(Dispatchers.Default)

        scope.launch {
            delay(1000)
            println("===>> Coroutine 1 Completed")
        }.invokeOnCompletion {
            if (it is CancellationException) {
                println("===>> Coroutine 1 cancelled")
            }
        }

        scope.launch {
            delay(1000)
            println("===>> Coroutine 2 Completed")
        }.invokeOnCompletion {
            if (it is CancellationException) {
                println("===>> Coroutine 2 cancelled")
            }
        }
        scope.coroutineContext[Job]?.cancelAndJoin()
    }

    //Canclleing one child job wont cancel second child and also parent job
    private fun performOperationFour_2() = runBlocking {

        val scope = CoroutineScope(Dispatchers.Default)

        scope.coroutineContext[Job]?.invokeOnCompletion {
            if(it is CancellationException) {
                println("==>> Parent job cancelled")
            }
        }

        val childJob1 = scope.launch {
            delay(1000)
            println("===>> Coroutine 1 Completed")
        }
        childJob1.invokeOnCompletion {
            if (it is CancellationException) {
                println("===>> Coroutine 1 cancelled")
            }
        }

        scope.launch {
            delay(1000)
            println("===>> Coroutine 2 Completed")
        }.invokeOnCompletion {
            if (it is CancellationException) {
                println("===>> Coroutine 2 cancelled")
            }
        }
        delay(200)
        childJob1.cancelAndJoin()
    }

    private fun performOperationFive() = runBlocking {

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            println("==>> Caught exception $throwable")
        }

        val scope = CoroutineScope(Job() + exceptionHandler)

        scope.launch {
            println("==>> Coroutine 1 started")
            delay(50)
            println("==>> Coroutine 1 failed")
            throw RuntimeException()
        }

        scope.launch {
            println("==>> Coroutine 2 started")
            delay(500)
            println("==>> Coroutine 2 Completed")
        }.invokeOnCompletion {
            if (it is CancellationException) {
                println("==>> Coroutine 2 got cancelled")
            }
        }
    }

    private fun performOperationFive_2() = runBlocking {

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            println("==>> Caught exception $throwable")
        }

        val scope = CoroutineScope(SupervisorJob() + exceptionHandler)

        scope.launch {
            println("==>> Coroutine 1 started")
            delay(50)
            println("==>> Coroutine 1 failed")
            throw RuntimeException()
        }

        scope.launch {
            println("==>> Coroutine 2 started")
            delay(500)
            println("==>> Coroutine 2 Completed")
        }.invokeOnCompletion {
            if (it is CancellationException) {
                println("==>> Coroutine 2 got cancelled")
            }
        }

        Thread.sleep(1000)
        println("==>> is Scope still alive ${scope.isActive}")

    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

}