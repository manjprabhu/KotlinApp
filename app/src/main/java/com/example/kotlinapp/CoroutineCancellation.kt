package com.example.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class CoroutineCancellation : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        performCoroutineCancellationTwo()
    }


    //When we use suspend function from coroutine library eg. delay() /withContext() then coroutine are already cooperative w.r.t cancellation
    // these suspend function regularly check if coroutine in which they are called are active or not, if not they throw "CancellationException"
    private fun performCoroutineCancellationCooperative() = runBlocking {
        val job = launch {
            repeat(10) { index ->
                println("==>> Element is $index")
                delay(100)
            }
        }
        delay(250)
        println("==>> Jon cancelled")
        job.cancel()

//        Output
//    2023-06-07 13:51:15.224 12137-12137/com.example.kotlinapp I/System.out: ==>> Element is 0
//    2023-06-07 13:51:15.325 12137-12137/com.example.kotlinapp I/System.out: ==>> Element is 1
//    2023-06-07 13:51:15.425 12137-12137/com.example.kotlinapp I/System.out: ==>> Element is 2
//    2023-06-07 13:51:15.474 12137-12137/com.example.kotlinapp I/System.out: ==>> Job cancelled
    }


    private fun performCoroutineCancellation() = runBlocking {
        val job = launch {
            repeat(10) { index ->
                println("==>> Element is $index")
                Thread.sleep(100)
            }
        }
        delay(250)
        println("==>> Cancelling coroutine")
        job.cancel()
    }
    //Output
//    2023-06-07 13:56:45.238 13480-13480/com.example.kotlinapp I/System.out: ==>> Element is 0
//    2023-06-07 13:56:45.379 13480-13480/com.example.kotlinapp I/System.out: ==>> Element is 1
//    2023-06-07 13:56:45.521 13480-13480/com.example.kotlinapp I/System.out: ==>> Element is 2
//    2023-06-07 13:56:45.656 13480-13480/com.example.kotlinapp I/System.out: ==>> Element is 3
//    2023-06-07 13:56:45.798 13480-13480/com.example.kotlinapp I/System.out: ==>> Element is 4
//    2023-06-07 13:56:45.927 13480-13480/com.example.kotlinapp I/System.out: ==>> Element is 5
//    2023-06-07 13:56:46.039 13480-13480/com.example.kotlinapp I/System.out: ==>> Element is 6
//    2023-06-07 13:56:46.159 13480-13480/com.example.kotlinapp I/System.out: ==>> Element is 7
//    2023-06-07 13:56:46.272 13480-13480/com.example.kotlinapp I/System.out: ==>> Element is 8
//    2023-06-07 13:56:46.415 13480-13480/com.example.kotlinapp I/System.out: ==>> Element is 9
//    2023-06-07 13:56:46.562 13480-13480/com.example.kotlinapp I/System.out: ==>> Cancelling coroutine


//Making suspend function cooperative
//    1. using  ensureActive() : if job is no longer active it throws CancellationException
//    2. Yield()

    private fun performCoroutineCancellationTwo() = runBlocking {
        val job = launch(Dispatchers.Default) {
            repeat(10) { index ->
//                ensureActive()
                yield()
                println("==>> Element is two:  $index")
                Thread.sleep(100)
            }
        }
        delay(250)
        println("==>> Cancelling coroutine")
        job.cancel()

        //output  ensureActive() / yield()
//        2023-06-07 14:04:17.701 13816-13845/com.example.kotlinapp I/System.out: ==>> Element is two:  0
//        2023-06-07 14:04:17.817 13816-13845/com.example.kotlinapp I/System.out: ==>> Element is two:  1
//        2023-06-07 14:04:17.932 13816-13845/com.example.kotlinapp I/System.out: ==>> Element is two:  2
//        2023-06-07 14:04:17.977 13816-13816/com.example.kotlinapp I/System.out: ==>> Cancelling coroutine

    }

    private fun performCoroutineCancellationThree() = runBlocking {
        val job = launch(Dispatchers.Default) {
            repeat(10) { index ->
                if (isActive) {
                    println("==>> Element is two:  $index")
                    Thread.sleep(100)
                } else {
                    println("==>> Clean up..")
                    throw CancellationException()
                }
            }
        }
        delay(250)
        println("==>> Cancelling coroutine")
        job.cancel()
    }


    //If we attempt to use a suspending function once the coroutine is cancelled, then CancellationException
    //is thrown , because the coroutine running this code is cancelled, so we use withContext() if we want to use
    //suspend function in a cancelled coroutine.
    private fun performNonCancellableWork() = runBlocking {
        val job = launch(Dispatchers.Default) {

            repeat(10) { index ->
                if (isActive) {
                    println("==>> Element is two:  $index")
                    Thread.sleep(100)
                } else {
                    withContext(NonCancellable) {
                        delay(100)
                        println("==>> Clean up..")
                        throw CancellationException()
                    }

                }
            }
        }
        delay(250)
        println("==>> Cancelling coroutine")
        job.cancel()
    }

    //Finally block
    //Here cancelAndJoin will wait for finally block to complete the execution..
    private fun finallyBlockDemo() = runBlocking {
        val job = launch {
            try {
                repeat(10) { index ->
                    println("Value : $index")
                    delay(100)
                }
            } finally {
                println("Running finally block...")
            }
        }
        delay(200)
        println("Cancelling the job...")
        job.cancelAndJoin()
        println("Job is cancelled....")
    }
}