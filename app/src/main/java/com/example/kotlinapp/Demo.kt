package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class Demo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extensionDemo()
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
    private suspend fun nineWork(): Int {
        var result = 0
        coroutineScope {
            launch {
                for (i in 1..10) {
                    result += i;
                }
            }
        }
        return result
    }


    private fun demoo() {
        val state = MutableStateFlow(0)
        lifecycleScope.launch {
            repeat(5) {
                delay(100)
                state.value = it
            }
        }

        lifecycleScope.launch {
            state.collect { value ->
                println("==>> $value")
            }
        }

        val sharedFlow = MutableSharedFlow<Int>(replay = 5)

        lifecycleScope.launch {
            (10..50).forEach {
                delay(100)
                sharedFlow.emit(it)
            }
        }

        lifecycleScope.launch {
            sharedFlow.collect {
                println("==>> Collector One: $it")
            }
        }

        println("==>> **************************")
        lifecycleScope.launch {
            delay(1000)
            sharedFlow.collect {
                println("==>> Collector Two:  $it")
            }
        }
    }

    //lateinit and lazy

    data class User(var name: String = "ABC")

    private fun lateInitDemo() {
        lateinit var myUser: User

        myUser = User("Test")

        println("==>>  User: is  ${myUser.name}")
    }

    private fun lazyDemo() {

        //non - primitive type
        val myUser: User by lazy {
            User("This lazy Testing")
        }
        println("==>> Lazy: ${myUser.name}")

        //primitive type
        val counter: Int by lazy {
            10
        }
        println("==>> Lazy: $counter")


        //Nullable value
        val nullableValue: String? by lazy {
            null
        }
        println("==>> Lazy: $nullableValue")
    }


    private fun MutableList<Int>.swap(i: Int, j: Int): MutableList<Int> {
        val temp = this[i]
        this[i] = this[j]
        this[j] = temp
        return this
    }

    data class student(var name:String=  "Test") {
        var marks:Int = 35

        fun isPassed(marks:Int):Boolean{
            return marks >35
        }
    }

    private fun student.isFirstClass(marks:Int):Boolean {
        return marks > 75
    }


    private fun extensionDemo() {
        val list = mutableListOf(1, 2, 3, 4, 5, 6)
        println("==>> Before swap : $list")
        list.swap(3, 5)
        println("==>> After swap $list")

        val result  =student().isPassed(50)
        println("==>> Result : $result")

        val result1 = student().isFirstClass(34)
        println("==>> Result1 : $result1")

    }
}