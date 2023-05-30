package com.example.kotlinapp

import android.app.Activity
import android.os.Bundle
import android.util.Log

class ScopeFunctionDemo : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performLetOperation()
        performLetOperationTwo()

    }

    private fun performLetOperation() {
        val p = Person()
        p.let {
            it.age = 35
            it.contactNumber = 48846895
            it.name = "ZXCVB"
        }
        Log.d("TAG", "===>>> ${p.name} ${p.age}  ${p.contactNumber}")
    }

    private fun performLetOperationTwo() {
        val p = Person()
        val x  = Person().let {
            return@let "${it.age}"
        }

        Log.d("TAG","==>> Display: $x")
    }
}