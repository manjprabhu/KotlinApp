package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

class HigherOrderFun : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        higherOrder(10, 20, ::addOne)

        //passing lamda as param
        higherOrder(10, 4) { a, b ->
            a + b
        }

        generateRandom(1..6, 4) { result ->
            println("Result : $result")
        }

        val res = generateNumber {
            println(it)//will print value 25
        }

        println(res) // will print "Number generating"

        calculatorDemo()
    }

    private fun addOne(a: Int, b: Int): Int {
        return a + b
    }

    private fun higherOrder(a: Int, b: Int, fn: (Int, Int) -> Int) {
        println(fn(a, b))
    }

    //second example for Higher order function
    private fun generateRandom(range: IntRange, time: Int, callBack: (result: Int) -> Unit) {
        for (i in 0 until time) {
            val result = range.random()
            callBack(result)
        }
    }

    //another usage of HigherOrder function
    private fun generateNumber(callBack: (result: Int) -> Unit): String {
        thread {
            Thread.sleep(3000)
            callBack(25)
        }
        return "Number generating"
    }

    private fun calculatorDemo() {
        val sum = calculate(10, 20, ::add)
        println("==>> Sum is : $sum")

        val diff = calculate(20, 10, ::subtract)
        println("==>> Difference is : $diff")

        val multi = calculate(20, 10, ::multiply)
        println("==>> Multiplication is : $multi")

        val result = calculate(20, 10, ::division)
        println("==>> Division is : $result")
    }

    //One more example of HigherOrder function, calculator example

    private fun calculate(paramOne: Int, paramTwo: Int, operation: (Int, Int) -> Int): Int {
        return (operation(paramOne, paramTwo))
    }

    private fun add(one: Int, two: Int): Int {
        return one + two
    }

    private fun subtract(one: Int, two: Int): Int {
        return one - two
    }

    private fun multiply(one: Int, two: Int): Int {
        return one * two
    }

    private fun division(one: Int, two: Int): Int {
        return one / two
    }

}