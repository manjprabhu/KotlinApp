package com.example.kotlinapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
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
        demo()
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

    open class student {
        var marks: Int = 35

        open fun isPassed(marks: Int): Boolean {
            return marks > 35
        }
    }

    private fun student.isFirstClass(marks: Int): Boolean {
        return marks > 75
    }


    private fun extensionDemo() {
        val list = mutableListOf(1, 2, 3, 4, 5, 6)
        println("==>> Before swap : $list")
        list.swap(3, 5)
        println("==>> After swap $list")

        val result = student().isPassed(50)
        println("==>> Result : $result")

        val result1 = student().isFirstClass(34)
        println("==>> Result1 : $result1")

    }

    private fun dd() {
        CoroutineScope(Job()).launch {
            val result = task()
            println("==>> Result : $result")
        }
    }

    private suspend fun task(): Int {
        var item = 0

        coroutineScope {
            launch {
                (1..10).forEach { _ ->
                    item++
                }
            }
        }
        return item
    }


    private fun calculate() {
        lifecycleScope.launch {
            for (i in 1..15) {
                if (i == 10) {
                    println("==>> Bit tired take a break...")
                    calculateWork()
                } else {
                    println("==>> $i")
                }
            }
            println("==>> Waiting for completion........")
        }
        println("==>> Completed!!!")
    }

    private suspend fun calculateWork() {
        for (i in 16..26) {
            println("==>> $i")
            delay(1000)
        }
    }

    private fun demo() {
        val st = student()

        with(st) {
            marks = 50
            println("st : $this")
            println("==>> ${isPassed(70)}")
        }

        val st1 = st.run {
            marks = 70
            println("$this")
        }

        println("==>> $st1")


        var list = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8)
            .also {
                println("==>> Before $it")
                it.add(10)
            }.filter { it > 4 }

        println("=--==>> List : $list")

        mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9).apply {
            this.filter { it > 3 }.let { list ->
                println("==>> List : $list")
            }
        }

        val numbers = mutableListOf("One", "Two", "Three", "Four", "Five")
        numbers.map { it.length }.filter { it > 3 }.let(::println)

        numbers.map { it.replaceRange(0, 2, "qwe") }.filter { it.endsWith("e") }.let {
            println("==>>> $it")
        }

        mutableListOf(1, 2, 3, 4, 5, 6, 7, 8).also {
            it.add(100)
        }.filter { it > 2 }.let { println("===>>> $it") }

        val gov = gov("India")

        val govt = gov.run {
            capital = " Delhi"
        }
        println("==>> Govt is : $govt")

        val arr = intArrayOf(1, 2, 3, 5, 7, 3, 5, 8, 9, 4, 4, 7, 9)
        for (i in arr)
            println(i)

        for (i in arr.indices step 2)
            println(arr[i])

        for (i in arr.lastIndex downTo 1)
            println(arr[i])

    }

    private fun functionOne() {
        val pz = pizza.getPizza("tomato")

    }

    class gov(name: String) {
        private var tenure: Int = 5
        var capital: String = ""

        init {
            println("==>> This is $name govt and its tenure is $tenure")
        }
    }


    // Factory pattern using companion object
    class pizza(type: String) {
        companion object Factory {
            fun getPizza(tp: String): pizza {

                return when (tp) {
                    "abc" -> pizza("abc")
                    "lmn" -> pizza("lmn")
                    else -> pizza("default")
                }
            }
        }
    }
}