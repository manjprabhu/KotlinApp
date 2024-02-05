package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RangeAndProgression : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        demo()
    }

    //rangeTo(..) and rangeUntil(..<) operator

    private fun rangeOperator() {

        //rangeTo()
        for (a in 1..10)
            println("==>> a:$a")

        for (b in 10 downTo 1)
            println("==>> b: $b")

        //step function

        for (c in 0..10 step 2)
            println("==>> c : $c")

        for (d in 10 downTo 1 step 2)
            println("==>> d : $d")
    }

    private fun demo() {
        val arr = intArrayOf(10, 20, 30, 40, 50, 60)

        for (x in arr) {
            println("==>> x : $x")
        }

        println("==>> ************************")

        for (y in arr.size-1 downTo 1) {
            println("==>> y: $y")
            println("==>> ${arr[y]}")
        }

        println("==>> ************************")

        val k = 4
        for (i in 0..k) {
            println("==>> i :$i")
            println("==>> ${arr[i]}")
        }

        println("==>> ************************")

        //until means excluding k
        for(j in 0 until k ) {
            println("==>>j :$j")
            println("==>> ${arr[j]}")
        }

        println("==>> ************************")
        for(j2 in 0 until arr.size-1 step 2 ) {
            println("==>>j2 :$j2")
            println("==>> ${arr[j2]}")
        }

        println("==>> ************************")

        for(k in arr.size-1 downTo 0 step 2) {
            println("==>>k :$k")
            println("==>> ${arr[k]}")
        }
    }

    private fun iteratorDemo() {
        val numbers = listOf("One", "Two", "three", "four", "five")

        val itr = numbers.iterator()
        while (itr.hasNext())
            println("==>> Number : ${itr.next()}")

        println("==>> ************************")
        //another way using for loop

        for (num in numbers)
            println("==>> number : $num")

        println("==>> ************************")

        numbers.forEach {
            println("==>> $it")
        }
    }


    //List iterator
    private fun listIteratorDemo() {
        val numbers = mutableListOf("One", "Two", "three", "four", "five")
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
        val numbers = mutableListOf("One", "Two", "three", "four", "five")

        val itr = numbers.iterator()

        itr.next()
        itr.remove()
        println("==>> After removal : $numbers")

        val itr1 = numbers.listIterator()
        itr1.next()
        itr1.add("New")
        println("==>> After Addition : $numbers")

//        itr1.set("Hello")
//        println("==>> After replace : $numbers")

        val x = setOf(1, 2, 3, 4, 1, 5, 5, 5, 5, 8)
        println("==>> x : $x")

        println("==>> ************************")
        val setTwo = mutableSetOf("One", "two", "three", "one", "One", "ONE")
        println("==>> y : $setTwo")
    }
}