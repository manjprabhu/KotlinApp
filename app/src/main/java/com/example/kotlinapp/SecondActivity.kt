package com.example.kotlinapp

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class SecondActivity : Activity() {

    override fun onResume() {
        super.onResume()
//        runBlockingDemo()
        coroutineScopeDemo()
    }

    //By default code within the coroutine is executed sequentially
    private fun sequentialExecution() {
        runBlocking {
            println("==>> Main program starts .....")

            val time = measureTimeMillis {
                val one = greetingOne()
                val two = greetingTwo()
                println("==>> $one$two")
            }
            println("==>> Task completed in $time ms")
            println("==>> Main program ends...")
        }
    }

    private suspend fun greetingOne(): String {
        delay(2000)
        println("==>> greetingOne")
        return "Hello"
    }

    private suspend fun greetingTwo(): String {
        delay(2000)
        println("==>> greetingTwo")
        return "World"
    }

    // By default this coroutine runs paralley
    private fun parallelExecutionUsingAsync() {

        lifecycleScope
        runBlocking {
            println("==>> Async Main program starts...")
            val time = measureTimeMillis {
                val one = async {
                    greetingOne()
                }

                val two = async {
                    greetingTwo()
                }
                println("Async Result ==>> ${one.await() + two.await()}")
            }
            println("==>> Async Task completed in $time ms")
            println("==>> Async Main program ends...")
        }
    }


    //This will run two task sequentially
    private fun executeThree() {
        runBlocking {
            println("==>> 3 Main program started....")
            val time = measureTimeMillis {
                val one = async {
                    greetingOne()
                }.await()

                val two = async {
                    greetingTwo()
                }.await()

                println("==>> 3 Result : ${one + two}")
            }
            println("==>> 3 Execution completed in $time ms")
            println("==>> 3 Main program Ended....")
        }
    }


    //Greeting one and two are executed even though we are not using the result
    private fun executeFour() {
        runBlocking {
            println("==>>4  Main Program started....")
            val time = measureTimeMillis {
                val one = async {
                    greetingOne()
                }

                val two = async {
                    greetingTwo()
                }
            }
            println("==>> 4 Execution completed in $time ms")
            println("==>> 4 Main program Ended....")
        }
    }

    //Lazily started async, below code runs sequentially
    private fun executeFive() {
        runBlocking {
            println("==>>5  Main Program started....")

            val time = measureTimeMillis {
                val one = async(start = CoroutineStart.LAZY) {
                    greetingOne()
                }

                val two = async(start = CoroutineStart.LAZY) {
                    greetingTwo()
                }
                println(" 5 Result ==>> ${one.await() + two.await()}")
            }
            println("==>> 5 Execution completed in $time ms")
            println("==>> 5 Main program Ended....")
        }
    }

    //Below task runs paralley
    private fun executeSix() {
        runBlocking {
            println("==>>6  Main Program started....")

            val time = measureTimeMillis {
                val one = async(start = CoroutineStart.LAZY) {
                    greetingOne()
                }

                val two = async(start = CoroutineStart.LAZY) {
                    greetingTwo()
                }
                one.start()
                two.start()
                println(" 6 Result ==>> ${one.await() + two.await()}")
            }
            println("==>> 6 Execution completed in $time ms")
            println("==>> 6 Main program Ended....")
        }
    }

    //Thread is blocked until coroutine is completed execution
    private fun runBlockingDemo() {
        runBlocking {
            launch {
                println("==>> Main program started...")
                val one = greetingOne()
                val two = greetingTwo()

                println("==>> Result ${"$one  $two"}")
            }
        }
        println("==>> Main program Ended...")
    }

    //Thread is suspended and continue with execution.
    private fun coroutineScopeDemo() {
        CoroutineScope(Dispatchers.Main).launch {
            println("==>> 2 Main program started...")
            val one = greetingOne()
            val two = greetingTwo()

            println("==>> 2 Result ${"$one  $two"}")
        }
        println("==>>2  Main program Ended...")
    }

//************************************************************

    private fun executeTask() {
        CoroutineScope(Dispatchers.IO).async() {
            val one = async { longRunningTaskOne() }
            val two = async { longRunningTaskTwo() }

            Log.v(" 1 Download one ", "${one.await()}")
            Log.v("1 Download two ", "${two.await()}")

            val total = one.await() + two.await()
            Log.v("Download", "$total")
        }
    }

    private fun executeTasktwo() {
        CoroutineScope(Dispatchers.IO).async {
            val one = withContext(Dispatchers.IO) { longRunningTaskOne() }
            val two = withContext(Dispatchers.IO) { longRunningTaskTwo() }

            Log.v("Download one ", "$one")
            Log.v("Download two ", "$two")
            val total = one + two;
            Log.v("Download", "$total")
        }
    }

    private fun executeTaskThreeSequentially(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            val current = System.currentTimeMillis();
            val one = longRunningTaskOne()
            val two = longRunningTaskTwo()

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "${one}, ${two}, ${(System.currentTimeMillis() - current) / 1000}",
                    Toast.LENGTH_LONG
                ).show()
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

            val two = async { longRunningTaskTwo() }

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "${one.await()}, ${two.await()}, ${(System.currentTimeMillis() - current) / 1000}",
                    Toast.LENGTH_LONG
                ).show()
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

            val two = async { longRunningTaskTwo() }.await()

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "${one}, ${two}, ${(System.currentTimeMillis() - current) / 1000}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun withContextExecution(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            val current = System.currentTimeMillis();

            val one = withContext(Dispatchers.IO) { longRunningTaskOne() }
            val two = withContext(Dispatchers.IO) { longRunningTaskTwo() }

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "${one}, ${two}, ${(System.currentTimeMillis() - current) / 1000}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    private suspend fun longRunningTaskOne(): Int {
        delay(3000)
        return 100;
    }

    private suspend fun longRunningTaskTwo(): Int {
        delay(5000)
        return 50
    }

    private suspend fun fun1(): String {
        CoroutineScope(Dispatchers.Unconfined).launch {
            delay(6000)
            Log.d("TAG", "==>> fun1 ${Thread.currentThread().name}")
        }
        return "hello function1";
    }

    private suspend fun fun2(): String {
        delay(5000)
        Log.d("TAG", "==>> fun2 ${Thread.currentThread().name}")
        return "hello function2"
    }


    private fun download(context: Context) {
        Toast.makeText(this, "Inside download task", Toast.LENGTH_LONG).show();
        CoroutineScope(Dispatchers.IO).launch {
            val fCount = async(Dispatchers.IO) { fileCount() }
            val cCount = async(Dispatchers.IO) { charCount() }
            Log.v("Download", "Thread:" + Thread.currentThread().name)
            Log.v("Download", "${fCount.await()}")
            Log.v("Download", "${cCount.await()}")

            withContext(Dispatchers.IO) {
                Toast.makeText(
                    context,
                    "Total: ${fCount.await() + cCount.await()}",
                    Toast.LENGTH_LONG
                ).show()
            }

//            Toast.makeText(context,"Downloading the count",Toast.LENGTH_LONG).show()
        }

    }

    private suspend fun fileCount(): Int {
        Log.v("Download fileCount", "Thread:" + Thread.currentThread().name)
        delay(3000)
        return 100
    }

    private suspend fun charCount(): Int {
        Log.v("Download charCount", "Thread:" + Thread.currentThread().name)
        delay(7000)
        return 50
    }

    private fun SuspendDemoOne() {

        CoroutineScope(Dispatchers.IO).launch {

            val resultOne = fun1()
            val resultTwo = fun2()

            Log.d("TAG", "==>> ${Thread.currentThread().name}")

        }
    }

}