package com.example.kotlinapp

import android.app.Activity
import android.os.Bundle
import android.util.Log

class ScopeFunctionDemo : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performApplyOperationWithNull()
        performRunOperationWithNull()

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
        val x = Person().let {
            return@let "${it.age}"
        }
        Log.d("TAG", "==>> Display: $x")
    }

    private fun performRunOperation() {
        val p = Person()
        val x = p.run {
            name = "QWERT"
            age = 56
            contactNumber = 8832
            return@run "${display()}"
        }
        Log.d("TAG", "RUN ==>> $x  ${p.name} ${p.age}  ${p.contactNumber}")
    }

    private fun performApplyOperation() {
        val p = Person()
        val x = p.apply {
            age = 45
            name = "APPLY NAME"
            contactNumber = 4388
        }
        Log.d("TAG", "APPLY ==>> ${x.name}")
    }

    private fun performApplyOperationWithNull() {
        val p: Person? = null
        p?.apply {
            this.name = "NULL NAME"
        }
        Log.d("TAG", "APPLY NUll ==>> ${p?.name}")
    }

    private fun performWithOperationWithNull() {
        val p: Person? = null
        with(p) {
            this?.name = "NULL NAME"
            this?.age = 10
            this?.contactNumber = 3288932
            this?.displayInfo()
        }
    }

    private fun performRunOperationWithNull() {
        val p: Person? = null
        val x = p?.run {
            name = "RUN NULL NAME"
            age = 10
            contactNumber = 3288932
            displayInfo()
        }

        println("==>> $x")
    }
}