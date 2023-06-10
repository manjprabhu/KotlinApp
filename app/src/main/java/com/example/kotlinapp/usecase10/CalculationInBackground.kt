package com.example.kotlinapp.usecase10

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

class CalculationInBackground : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        performCalculation()
    }

    private fun performCalculation() {
        lifecycleScope.launch(Dispatchers.Default) {
            println("==>> CoroutineContext : $coroutineContext")
            val factorial = calculateFactorial(100)
            println("==>> Factorial value is : ${factorial.toString()}")
        }
    }

    private fun calculateFactorial(value: Int): BigInteger {
        var factorial = BigInteger.ONE

        for (i in 1..value) {
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        }
        return factorial
    }

    private suspend fun calculateFactorialTwo(value: Int) = withContext(Dispatchers.Default) {
        var factorial = BigInteger.ONE

        for (i in 1..value) {
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        }
        factorial

    }
}