package com.example.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add(5, 10)
        addition(12,7)
    }

    fun add(a : Int , b: Int ) {
        val sum  = a + b;
        Log.v("sum:", sum.toString())
    }

    //function with default argument
    fun addition(x: Int = 100, y:Int = 20) {
        val sum =  x + y;
        Log.v("Sum:", sum.toString())
    }
}