package com.example.kotlinapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class CoroutineDemo : AppCompatActivity() {

    lateinit var scope: CoroutineScope
    lateinit var job1: Job
    lateinit var job2: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performLaunchExceptionOperation()
    }

    private fun coroutineScopeOne() {
        Log.d("TAG", "==>> coroutineScopeOne")
        scope = CoroutineScope(Dispatchers.Main)
        job1 = scope.launch {
            for (i in 0..50) {
                println("==>> $i")
                delay(1000)
            }
        }
        job2 = scope.launch {
            println("==>> loading .....")
            delay(5000)
            println("==>> Job2 is DONE .....")

        }
    }

    private fun performRunBlocking() {
        runBlocking {
            println("==>> Started running run bloking block.........")
            delay(10000)
            println("==>> Done run blocking...")
        }
        println("==>> Outside runblocking.....")
    }

    private fun performRunBlockingTwo() {
        runBlocking {
            println("==>> Started running run blocking block.........")
            launch {
                delay(1000)
                println("==>> Inside launch.........")
            }
            println("==>>Outside Launch.........")
        }

        println("==>> End running run blocking block.........")
    }

    override fun onDestroy() {
        super.onDestroy()

        //this will cancel both the job inside the scope
        if (scope.isActive) {
            scope.cancel()
        }
        //this will cancel only job2
//        if (job2.isActive) {
//            Log.d("TAG", "==>> Cancel coroutine")
//            job2.cancel()
//        }
    }


    private fun performLaunchOperation() {
        scope = CoroutineScope(Dispatchers.IO)
        val job = scope.launch {
            Log.d("TAG", "==>> Inside launched coroutine")
            return@launch
        }
    }

    private fun performAsyncOperation() {
        scope = CoroutineScope(Dispatchers.IO)

        val result = scope.async {
            val list = mutableListOf<Int>(10, 3, 4, 66)
            return@async list.size
        }

        CoroutineScope(Dispatchers.Main).launch {
            Log.d("TAG", "==>> ${result.await()}")
        }
    }

    private fun performLaunchExceptionOperation() {
        Log.d("TAG", "==>> performLaunchExceptionOperation()")
        scope = CoroutineScope(Dispatchers.IO)
        val job = scope.launch {
            try {
                exceptionDemo()
            } catch (e: Exception) {
                Log.d("TAG", "==>> Exception occured: ${e.printStackTrace()}")
            }

        }
    }

    private fun performAsyncExceptionOperation() {
        Log.d("TAG", "==>> performAsyncExceptionOperation()")
        scope = CoroutineScope(Dispatchers.IO)
        val deferred = scope.async {
            exceptionDemo()
        }
    }

    private fun exceptionDemo() {
        throw Exception("exceptionDemo")
    }


}