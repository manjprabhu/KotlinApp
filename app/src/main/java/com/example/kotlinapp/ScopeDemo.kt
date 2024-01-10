package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

class ScopeDemo : AppCompatActivity() {

    /* Note:
        The launch(Dispatchers.Default) creates children coroutines in runBlocking scope,
        so runBlocking waits for their completion automatically.

        GlobalScope.launch creates global coroutines.
        It is now developer’s responsibility to keep track of their lifetime.
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performWorkGlobalScope()
        performWorkGlobalScopeFix()
    }

    private suspend fun work(i: Int) {
        delay(1000)
        println(" ==>> Work $i is done...")
    }

    private fun workThread(i: Int) {
        Thread.sleep(1000)
        println(" ==>> WorkThread $i is done...")
    }

    //Here launch will inherit the coroutine context from runBlocking and runs all the coroutine on single thread.
    private fun performWorkSequential() {
        val time = measureTimeMillis {
            runBlocking {
                for (i in 1..10) {
                    launch {
                        workThread(i)
                    }
                }
            }
        }
        println(" ==>> 1 Time taken is: $time")
        println(" ==>>*************************")
    }

    //Here we have launched coroutine with Dispatcher.IO
    private fun performWorkParallel() {
        val time = measureTimeMillis {
            runBlocking {
                for (i in 1..10) {
                    launch(Dispatchers.IO) {
                        work(i)
                    }
                }
            }
        }
        println("==>> 2 Time taken is :  $time")
        println(" ==>>*************************")
    }

    //We use GlobalScope to launch the coroutine. Here child coroutines (started with GlobalScope.Launch()) are global corutines
    // and not in scope of runBLocking(), hence runblocking will not wait for the child coroutine completion..
    private fun performWorkGlobalScope() {
        val time = measureTimeMillis {
            runBlocking {
                for (i in 1..2) {
                    GlobalScope.launch {
                        work(i)
                    }
                }
            }
        }
        println("==>> 3 Time taken is :  $time")
        println(" ==>>*************************")
    }

    //GlobalScope.launch creates global coroutine and its developers responsibility to track their lifetime
    private fun performWorkGlobalScopeFix() {
        val time = measureTimeMillis {
            runBlocking {
                val jobs = mutableListOf<Job>()
                for (i in 1..10) {
                    jobs += GlobalScope.launch {
                        work(i)
                    }
                }
                jobs.forEach { it.join() }
            }
        }
        println("==>> 4 Time taken is :  $time")
        println(" ==>>*************************")
    }

    private val dispatcher = Executors.newFixedThreadPool(3).asCoroutineDispatcher()

    private fun exampleCoroutine() {
        val time = measureTimeMillis {
            runBlocking {
                (1..10).forEach {
                    launch(dispatcher) {
                        println("==>> 1 Coroutine $it started on thread: ${Thread.currentThread().name}")
                        delay(1000)
                        println("==>> 1 Coroutine $it completed on thread: ${Thread.currentThread().name}")

                    }
                }
            }
        }
        println("==>> 4 Time taken is :  $time")
    }

    private fun exampleRunBlocking() {
        val time = measureTimeMillis {
            runBlocking {
                (1..10).forEach {
                    runBlocking {
                        println("==>> 2 Coroutine $it started on thread: ${Thread.currentThread().name}")
                        delay(1000)
                        println("==>> 2 Coroutine $it completed on thread: ${Thread.currentThread().name}")
                    }
                }
            }
        }
        println("==>> 5 Time taken is :  $time")
    }

    private fun repeatCoroutine() {
        runBlocking {
            repeat(100) {
                launch(dispatcher) {
                    println("==>>> Started Coroutine $it")
                    delay(10000)
                    println("==>> Ended Coroutine $it")
                }
            }
        }
    }

    private fun runBlockingDemo() {
        runBlocking {
            launch {
                delay(1000)
                println("==>> One")
            }
            println("==>> Two")
        }
        println("==>> Three")
        println(" ==>>*************************")
    }

    private fun runBLockingAndSuspend() {
        runBlocking {
            suspendingWork()
            println("==>>> Two..")
        }
        println("==>>> Three..")
    }

    private suspend fun suspendingWork() {
        delay(1000)
        println("==>> One..")
    }

    private fun runBLockingDemoOne() {
        runBlocking {
            suspendingWorkTwo()
            println("==>> One **")
        }
        println("==>> END **")
    }

    private suspend fun suspendingWorkTwo() {
        coroutineScope {
            launch {
                delay(1000)
                println("==>> Two **")
            }

            launch {
                delay(2000)
                println("==>> THREE **")
            }
            println("==>> FOUR **")
        }
    }

    private fun runBlockingDemoTwo() {
        runBlocking {
            delay(1000)
            println("==>> Hello ....")
        }
        println("==>> World...")
    }

    private fun runBlockingDemoThree() {
        runBlocking {
            launch {
                delay(1000)
                println("==>> Hello **")
            }
        }
        println("==>> World **")
    }

    private fun runBlockingDemoFour() =
        runBlocking {
            launch {
                delay(1000)
                println("==>> Hello!!")
            }
            println("==>> World!!")
        }
}