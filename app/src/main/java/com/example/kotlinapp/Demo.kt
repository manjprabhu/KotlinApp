package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Demo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        demoMethod()
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
        for(i in list.lastIndex downTo 1) {
            println("==>> Index :$i")
            println("==>> Ele :${list[i]}")
        }

        println("==>> *************************")
        for(i in list.indices step 2){
            println("==>> u Index :$i")
            println("==>> u Ele :${list[i]}")
        }

        println("==>> *************************")
        for(i in 0 until list.lastIndex) {
            println("==>>  Index :$i")
            println("==>>  Ele :${list[i]}")
        }


    }
}