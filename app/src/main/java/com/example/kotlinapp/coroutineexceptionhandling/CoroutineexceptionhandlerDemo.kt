package com.example.kotlinapp.coroutineexceptionhandling

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class CoroutineexceptionhandlerDemo : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        demo()
    }


    //No try-catch to handle the exception, hence app crashes.
    private fun exceptionHandlingOne() {
        val topLevelScope = CoroutineScope(Job())
        topLevelScope.launch {
            launch {
                throw RuntimeException("Runtime exception thrown from child coroutine....")
            }
        }
    }

    //No try-catch to handle the exception, hence app crashes. exception propagated upwards..
    private fun exceptionHandlingTwo() {
        val topLevelScope = CoroutineScope(Job())
        topLevelScope.launch {
            try {
                launch {
                    throw RuntimeException("Runtime exception thrown from child coroutine....")
                }
            } catch (e: java.lang.RuntimeException) {
                println("==>> Exception handled at catch block...")
            }
        }
    }

    //coping function coroutineScope{} re-throws exceptions of its failing children instead of propagating them up the job hierarchy.
    private fun exceptionHandlingThree() {
        val topLevelScope = CoroutineScope(Job())
        topLevelScope.launch {
            try {
                coroutineScope {
                    val job1 = launch {
                        println("==>> Hello from Job1")
                        throw RuntimeException("Exception thrown from JOB2....")
                    }

                    val job2 = launch {
                        delay(100)
                        println("==>> Hello from job2")
                    }
                }
            } catch (e: java.lang.RuntimeException) {
                println("==>> Exception handled at catch block...")
            }
        }
    }


    //The scoping function supervisorScope{} installs a new independent sub-scope in the job hierarchy with a SupervisorJob as the scope’s job
    //This new scope does not propagate its exceptions “up the job hierarchy” so it has to handle its exceptions on its own
    // Coroutines that are started directly from the supervisorScope are top-level coroutines

    private fun exceptionHandlingFour() {
        val topLevelScope = CoroutineScope(Job())
        val parent = topLevelScope.launch {
            val job1 = launch {
                println("==>> Hello from Job1....")
            }

            supervisorScope {
                val job2 = launch {
                    println("==>> Hello from Job2")
                    throw RuntimeException("Exception thrown from JOB2....")
                }

                val job3 = launch {
                    delay(100)
                    println("==>> Hello from job3")
                }
            }
        }
        println("==>> Hello from parent")
    }


    private fun exceptionHandlingFive() {
        val topLevelScope = CoroutineScope(Job())
        val parent = topLevelScope.launch {
            val job1 = launch {
                println("==>> Hello from Job1....")
            }

            val scope = CoroutineScope(SupervisorJob())

            val job2 = scope.launch {
                println("==>> Hello from Job2")
                throw RuntimeException("Exception thrown from JOB2....")
            }

            val job3 = scope.launch {
                delay(100)
                println("==>> Hello from job3")
            }
        }
        println("==>> Hello from parent")
    }


    private fun exceptionHandlingSix() {
        val topLevelScope = CoroutineScope(Job())
        val parent = topLevelScope.launch {
            val job1 = launch {
                println("==>> Hello from Job1....")
            }

            supervisorScope {
                val job2 = this.launch {
                    println("==>> Hello from Job2")
                    throw RuntimeException("Exception thrown from JOB2....")
                }

                val job3 = this.launch {
                    println("==>> Hello from job3")
                }
            }
        }
        println("==>> Hello from parent")
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
                    delay(600)
                    throw RuntimeException("Thrown from job1")
                    println("==>> Job1 done...")
                }

                val job2 = launch {
                    println("==>> Job2 Started...")
                    delay(700)
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


    private fun supervisorJobExample() {

        val scope = CoroutineScope(Job())

        scope.launch(SupervisorJob()) {
            launch {
                delay(600)
                println("==>> This is First job....")
            }

            launch {
                delay(500)
                println("==>> This is Second job....")
            }

            launch() {
                println("==>> This is third job....")
                throw Exception()
            }
        }
    }

    private fun supervisorJobExample2() {
        val scope = CoroutineScope(SupervisorJob())
        scope.launch {
            launch {
                delay(600)
                println("==>> This is First job....")
            }

            launch {
                delay(500)
                println("==>> This is Second job....")
            }

            launch() {
                println("==>> This is third job....")
                throw Exception()
            }
        }
    }

    //Working...
    private fun supervisorJobExampleCorrectWay() {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("==>> Exception occured $exception")
        }

        CoroutineScope(Dispatchers.Main + handler).launch {

            supervisorScope {

                launch {
                    delay(600)
                    println("==>> This is First job....")
                }

                launch {
                    delay(500)
                    println("==>> This is Second job....")
                }

                launch {
                    println("==>> This is third job....")
                    throw Exception()
                }

            }
            println("==>> parent job completed....")
        }
    }


    // Another way to handle failure, here we pass supervisorJob() as param to CoroutineScope()
    //Working
    private fun supervisorJobExampleCorrectWay3() {

        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("==>> Handled exception $exception")
        }

        val parentScope = CoroutineScope(SupervisorJob() + exceptionHandler)

        parentScope.launch {
            delay(600)
            println("==>> This is First job....")
        }

        parentScope.launch {
            delay(500)
            println("==>> This is Second job....")
        }

        parentScope.launch {
            println("==>> This is third job....")
            throw Exception()
        }

        println("==>> Parent job completed....")
    }


    private fun supervisorJobExampleCorrectWay2() {

        val handler = CoroutineExceptionHandler { _, exception ->
            println("==>> Exception occured $exception")
        }


        val scope = CoroutineScope(SupervisorJob())
        SupervisorJob()

        scope.launch() {
            delay(600)
            println("==>> This is First job....")
        }

        scope.launch() {
            delay(500)
            println("==>> This is Second job....")
        }

        scope.launch(handler) {
            println("==>> This is third job....")
            throw Exception()
        }
    }

    private fun supervisorScopeHandling() {
        runBlocking {
            val result = supervisorScope {
                launch {
                    delay(100)
                    try {
                        throw IllegalArgumentException("A total fiasco!")
                    } catch (e: Exception) {
                        println("==>> caught IllegalArgumentException")
                    }
                }
                launch {
                    delay(200)
                    println("==>> Hi there!")
                }
                "Result!!!"
            }
            println("==>> Got result: $result")
        }
        println("==>> Task completed....")
    }

    private fun supervisorScopeHandling2() {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("==>> caught IllegalArgumentException !!!")
        }
        runBlocking {

            val result = supervisorScope {

                launch(handler) {
                    delay(100)
                    throw IllegalArgumentException("A total fiasco!")
                }

                launch {
                    delay(200)
                    println("==>> Hi there!")
                }

                "Result!!!"
            }
            println("==>> Got result: $result")

            println("==>> Task completed....")
        }
    }


    //this will crash , as exception handler only works with top level coroutine
    private fun demo() {
        val handler = CoroutineExceptionHandler { _, e -> println("==>> Caught exception $e") }
        val scope = CoroutineScope(Job())

        scope.launch {

            coroutineScope {

                launch(handler) {// here handler will not have any effect , as this is not root corutine.
                    delay(100)
                    throw IllegalArgumentException("A total fiasco!")
                }

                launch {
                    delay(200)
                    println("==>> Hi there!!")
                }
            }
        }
        println("==>> Task completed....")
    }
}

