package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class Demo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nine()
    }

    private fun demoMethod() {
        val list = mutableListOf(1, 2, 4, 5, 6, 7, 8)
        val lastIndex = list.lastIndex

        println("==>> Last index : $lastIndex")
        println("==>> *************************")

        for (n in list)
            println("==>> Element : $n")

        println("==>> *************************")

        for (i in list.indices)
            println("==>> ${list[i]}")

        println("==>> *************************")
        for (i in list.lastIndex downTo 1) {
            println("==>> Index :$i")
            println("==>> Ele :${list[i]}")
        }

        println("==>> *************************")
        for (i in list.indices step 2) {
            println("==>> u Index :$i")
            println("==>> u Ele :${list[i]}")
        }

        println("==>> *************************")
        for (i in 0 until list.lastIndex) {
            println("==>>  Index :$i")
            println("==>>  Ele :${list[i]}")
        }
    }

    private fun demoMethodTwo() {
        val list = mutableSetOf<Job>()
        val time = measureTimeMillis {
            runBlocking {
                repeat(100) {
                    list += launch(Dispatchers.IO) {
                        delay(100)
                        println("==>> Task $it running on ${Thread.currentThread().name}")
                    }
                }
            }
        }
        println("==>> Task is completed... $time")
    }

//Output
    /**
     * I  ==>> the
     * I  ==>>> Follow
     * I  ==>> execution..**/
    private fun three() {
        runBlocking {
            workThree()
            println("==>>> Follow ")
        }
        println("==>> execution...")
    }

    private suspend fun workThree() {
        delay(1000)
        println("==>> the ")
    }


    //Output
    /**
     * I  ==>> Follow
     * I  ==>> the
     * I  ==>> execution..**/
    private fun four() {
        runBlocking {
            launch {
                workFour()
            }
            println("==>> Follow ")
        }
        println("==>> execution...")
    }

    private suspend fun workFour() {
        delay(1000)
        println("==>> the ")
    }

    //Output
    /**
     * Hello
     * Work Five
     * Work FIve
     * Completed....
     * **/
    private fun five() {
        runBlocking {
            launch {
                workFive()
            }
            launch {
                workFive()
            }
            println("==>> Hello")
        }
        println("==>> Completed.....")
    }

    private suspend fun workFive() {
        delay(1000)
        println("==>> Work Five")
    }


    //output
    /**
     *  Follow
     *  the
     *  printing
     *  Completed...
     * **/
    private fun six() {
        runBlocking {
            launch {
                delay(100)
                printSix("printing")
            }
            launch {
                printSix("the")
            }
            printSix("Follow")
        }
        println("==>> Completed...")
    }

    private fun printSix(value: String) = println("==>> $value")

    //Output
    /**
     * One
     * Two
     * Three
     * Four
     * Completed
     * **/
    private fun seven() {
        runBlocking {
            workSeven()
            println("==>> Four")
        }
        println("==>>> Completed...")
    }

    private suspend fun workSeven() = coroutineScope {
        launch {
            delay(2000L)
            println("==>> Three")
        }
        launch {
            delay(1000)
            println("==>> Two")
        }

        println("==>>> One")
    }


    //Output
    /**
     * One
     * Four
     * Completed
     * Two
     * Three
     * **/
    private fun eight() {
        runBlocking {
            workEight()
            println("==>> Four")
        }
        println("==>>> Completed...")
    }

    private suspend fun workEight() {
        lifecycleScope.launch {
            launch {
                delay(2000L)
                println("==>> Three")
            }
            launch {
                delay(1000)
                println("==>> Two")
            }
        }
        println("==>>> One")
    }

    private fun nine() {
      /*  runBlocking {
            var r: Int = nineWork()
            println("==>> Result : $r")
        }*/
        lifecycleScope.launch {
            val r: Int = nineWork()
            println("==>> Result : $r")
        }
        println("==>> Completed.....")
    }


    //coroutineScope without launch{} or async{} is of no use, does not make difference
    private  suspend fun nineWork():Int {
        var result = 0
        coroutineScope {
            launch {
                for(i in 1..10) {
                    result+=i;
                }
            }
        }
        return result
    }
}