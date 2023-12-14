package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

class HigherOrderFun : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        higherOrder(10, 20, ::add)

        generateRandom(1..6, 4) { result ->
            println("Result : $result")
        }

        val res = generateNumber {
            println(it)//will print value 25
        }

        println(res) // will print "Number generating"
    }

    private fun add(a: Int, b: Int): Int {
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
}