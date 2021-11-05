package com.example.kotlinapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SecondActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
//        Toast.makeText(this,"This is second activity", Toast.LENGTH_LONG).show()
//        download(this)
        executeTask()
//        executeTasktwo()
    }

    override fun onStart() {
        super.onStart()
    }

    private fun executeTask() {
        CoroutineScope(Dispatchers.IO).launch {
            val one = async {  longRunningTaskOne()}
            val two= async { longRunningTaskTwo() }

            Log.v(" 1 Download one ", "${one.await()}")
            Log.v("1 Download two ", "${two.await()}")

            val total = one.await() + two.await()
            Log.v("Download","$total")
        }
    }

    private fun executeTasktwo(){
        CoroutineScope(Dispatchers.IO).launch {
            val one = withContext(Dispatchers.IO) { longRunningTaskOne()}
            val two = withContext(Dispatchers.IO) { longRunningTaskTwo()}

            Log.v("Download one ", "$one")
            Log.v("Download two ", "$two")
            val total = one + two;
            Log.v("Download", "$total")
        }
    }

    private suspend fun longRunningTaskOne():Int {
        delay(5000)
        return 100;
    }

    private suspend fun longRunningTaskTwo():Int {
        delay(7000)
        return 50
    }

    private fun download(context: Context) {
        Toast.makeText(this,"Insode download task",Toast.LENGTH_LONG).show();
        CoroutineScope(Dispatchers.IO).launch {
            val fCount = async(Dispatchers.IO) { fileCount() }
            val cCount = async(Dispatchers.IO) { charCount() }
            Log.v("Download","Thread:"+Thread.currentThread().name)
            Log.v("Download","${fCount.await()}")
            Log.v("Download","${cCount.await()}")

            withContext(Dispatchers.IO) {
                Toast.makeText(context,"Total: ${fCount.await() + cCount.await()}",Toast.LENGTH_LONG).show()
            }

//            Toast.makeText(context,"Downloading the count",Toast.LENGTH_LONG).show()
        }

    }

    private suspend fun fileCount() : Int {
        Log.v("Download fileCount" ,"Thread:"+Thread.currentThread().name)
        delay(3000)
        return 100
    }

    private suspend fun charCount() : Int {
        Log.v("Download charCount" ,"Thread:"+Thread.currentThread().name)
        delay(7000)
        return 50
    }

}