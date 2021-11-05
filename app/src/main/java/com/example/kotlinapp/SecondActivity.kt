package com.example.kotlinapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*

class SecondActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        executeTaskThreeSequentially(this)
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
        CoroutineScope(Dispatchers.IO).async {
            val one = withContext(Dispatchers.IO) { longRunningTaskOne()}
            val two = withContext(Dispatchers.IO) { longRunningTaskTwo()}

            Log.v("Download one ", "$one")
            Log.v("Download two ", "$two")
            val total = one + two;
            Log.v("Download", "$total")
        }
    }

    private fun executeTaskThreeSequentially(context: Context) {
      CoroutineScope(Dispatchers.Main).launch {
          val current = System.currentTimeMillis();
          val  one =  longRunningTaskOne()
          val two = longRunningTaskTwo()

          withContext(Dispatchers.Main) {
              Toast.makeText(context,"${one}, ${two}, ${(System.currentTimeMillis() - current)/1000}", Toast.LENGTH_LONG).show()
          }
      }
    }

    //spawn the task one
    //spawn the task two
    //await on task one
    //await on task two
    private fun executeJobParalley(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {

            val current = System.currentTimeMillis();

            val one = async { longRunningTaskOne() }

            val  two = async { longRunningTaskTwo() }

            withContext(Dispatchers.Main) {
                Toast.makeText(context,"${one.await()}, ${two.await()}, ${(System.currentTimeMillis() - current)/1000}", Toast.LENGTH_LONG).show()
            }
        }
    }

    //Below code execute in sequential manner.
    //spawn the task one
    //await on task one
    //spawn the task two
    //await on task two
    private fun executeSequentiallyWithAsync(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            val current = System.currentTimeMillis();

            val one = async { longRunningTaskOne() }.await()

            val  two = async { longRunningTaskTwo() }.await()

            withContext(Dispatchers.Main) {
                Toast.makeText(context,"${one}, ${two}, ${(System.currentTimeMillis() - current)/1000}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun withContextExecution(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            val current = System.currentTimeMillis();

            val one  = withContext(Dispatchers.IO) { longRunningTaskOne()}
            val two  = withContext(Dispatchers.IO) { longRunningTaskTwo()}

            withContext(Dispatchers.Main) {
                Toast.makeText(context,"${one}, ${two}, ${(System.currentTimeMillis() - current)/1000}", Toast.LENGTH_LONG).show()
            }
        }
    }


    private suspend fun longRunningTaskOne():Int {
        delay(3000)
        return 100;
    }

    private suspend fun longRunningTaskTwo():Int {
        delay(5000)
        return 50
    }

    suspend fun fun1(): String {
        delay(1000)
        return "hello function1";
    }

    suspend fun fun2(): String {
        delay(5000)
        return "hello funtion2"
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