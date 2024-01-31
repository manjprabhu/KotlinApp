package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RangeAndProgression: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rangeOperator()
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
}