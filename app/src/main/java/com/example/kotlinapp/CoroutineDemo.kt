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
        coroutineScopeOne()
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

    override fun onDestroy() {
        super.onDestroy()

      //this will cancel both the job inside the scope
        if(scope.isActive) {
            scope.cancel()
        }
        //this will cancel only job2
//        if (job2.isActive) {
//            Log.d("TAG", "==>> Cancel coroutine")
//            job2.cancel()
//        }
    }
}