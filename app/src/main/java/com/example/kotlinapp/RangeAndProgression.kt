package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RangeAndProgression: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iteratorDemo()
    }

    //rangeTo(..) and rangeUntil(..<) operator

    private fun rangeOperator() {

       //rangeTo()
        for( a in 1..10)
            println("==>> a:$a")

        for(b in 10 downTo  1)
            println("==>> b: $b")

        //step function

        for(c in 0..10 step 2)
            println("==>> c : $c")

        for(d in 10 downTo 1 step 2)
            println("==>> d : $d")
    }

    private fun iteratorDemo() {
        val numbers = listOf("One","Two","three","four","five")

        val itr = numbers.iterator()
        while (itr.hasNext())
            println("==>> Number : ${itr.next()}")

        println("==>> ************************")
        //another way using for loop

        for(num in numbers)
            println("==>> number : $num")

        println("==>> ************************")

        numbers.forEach {
            println("==>> $it")
        }
    }
}