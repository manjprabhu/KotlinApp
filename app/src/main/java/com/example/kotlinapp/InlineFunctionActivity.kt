package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class InlineFunctionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calculatorDemo()
    }

    private fun testInline() {
        println("==>> Test Inline ")

        helloMethod()

        helloMethod()

        helloMethod()
    }

    private inline fun helloMethod() {
        println("==>> Hello method")
    }


    //Usage in higherOrder function
    //Each higherOrder function we create leads to function object creation and memory allocation, this introduces runtime overhead
    //because of inline keyword , compiler copies the content of the function to call site, rather than creating new function object

    // To decrease the memory allocation caused by lamda expression/ higerOrder function use inline
    //make sure inline is applied to small function that lamda as param

    private fun calculatorDemo() {
        val sum = calculate(10, 20, ::add)
        println("==>> Sum is : $sum")

        val diff = calculate(20, 10, ::subtract)
        println("==>> Difference is : $diff")
    }

    private inline fun calculate(paramOne: Int, paramTwo: Int, operation: (Int, Int) -> Int): Int {
        return operation(paramOne, paramTwo)
    }

    private fun add(one: Int, two: Int): Int {
        return one + two
    }

    private fun subtract(one: Int, two: Int): Int {
        return one - two
    }

    private fun multiply(one:Int,two:Int) = one * two

}