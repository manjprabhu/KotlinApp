package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RangeAndProgression: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mutableListIteratorDemo()
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


    //List iterator
    private fun listIteratorDemo() {
        val numbers = mutableListOf("One","Two","three","four","five")
        numbers.also {
            it.add("six")
        }

        val itr = numbers.listIterator()
        while (itr.hasNext())
            println("==>> Number : ${itr.next()}")

        println("==>> ********** Iterating backwards **********")
        while (itr.hasPrevious())
            println("==>> Number : ${itr.previous()}")
    }

    //Mutable iterator

    private fun mutableListIteratorDemo() {
        val numbers = mutableListOf("One","Two","three","four","five")

        val itr = numbers.iterator()

        itr.next()
        itr.remove()
        println("==>> After removal : $numbers")

        val itr1 = numbers.listIterator()
        itr1.next()
        itr1.add("New")
        println("==>> After Addition : $numbers")

        itr1.set("Hello")
        println("==>> After replace : $numbers")
    }
}