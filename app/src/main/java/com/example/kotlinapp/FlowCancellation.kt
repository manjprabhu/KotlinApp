package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.math.BigInteger

class FlowCancellation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        performFlowCancellationThree()
    }


    private suspend fun intFlow(): Flow<Int> = flow {
        emit(1)
        emit(2)
        emit(3)
        emit(4)
        emit(5)
        emit(6)
    }

    //Cooperative flow cancellation
    private fun performFlowCancellationCooperative() {
        lifecycleScope.launch {
            intFlow().onCompletion {
                println("==>> Flow got cancelled")
            }.collect {
                println("==>> Received : $it")
                if (it == 3)
                    cancel()
            }
        }
    }


    //Cooperative flow cancellation
    private fun performFlowCancellationNonCooperative() {
        lifecycleScope.launch {
            intFlowTwo().onCompletion {
                println("==>> Flow got cancelled")
            }.collect {
                println("==>> Received : $it")
                if (it == 3)
                    cancel()
            }
        }
    }

    private suspend fun intFlowTwo(): Flow<Int> = flow {
        emit(1)
        emit(2)
        emit(3)

        currentCoroutineContext().ensureActive()

        println("==>> Start factorial calculation")
        calculateFactorial(5)
        println("==>> End factorial calculation")

        emit(4)
        emit(5)

//        output  before adding  currentCoroutineContext().ensureActive()
//        2023-06-07 13:12:20.423 6878-6878/com.example.kotlinapp I/System.out: ==>> Received : 1
//        2023-06-07 13:12:20.423 6878-6878/com.example.kotlinapp I/System.out: ==>> Received : 2
//        2023-06-07 13:12:20.423 6878-6878/com.example.kotlinapp I/System.out: ==>> Received : 3
//        2023-06-07 13:12:20.425 6878-6878/com.example.kotlinapp I/System.out: ==>> Start factorial calculation
//        2023-06-07 13:12:20.425 6878-6878/com.example.kotlinapp I/System.out: ==>> End factorial calculation
//        2023-06-07 13:12:20.426 6878-6878/com.example.kotlinapp I/System.out: ==>> Flow got cancelled

//        After adding currentCoroutineContext().ensureActive()
//        2023-06-07 13:14:29.843 6952-6952/com.example.kotlinapp I/System.out: ==>> Received : 1
//        2023-06-07 13:14:29.843 6952-6952/com.example.kotlinapp I/System.out: ==>> Received : 2
//        2023-06-07 13:14:29.843 6952-6952/com.example.kotlinapp I/System.out: ==>> Received : 3
//        2023-06-07 13:14:29.846 6952-6952/com.example.kotlinapp I/System.out: ==>> Flow got cancelled


    }

    private fun calculateFactorial(value: Int): BigInteger {
        var factorial = BigInteger.ONE
        for (i in 1..value) {
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        }
        return factorial
    }

    private suspend fun calculateFactorialCooperative(value: Int): BigInteger = coroutineScope {
        var factorial = BigInteger.ONE
        for (i in 1..value) {
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
            ensureActive()
        }
        factorial
    }


    //flowOf() does not internally check if collecting coroutine is active or not , so we have to check manually if
    //coroutine is cancelled or not by adding "cancellable"
    private fun performFlowCancellationThree() {
        lifecycleScope.launch {
            flowOf(1, 2, 3, 4, 5).onCompletion {
                println("==>> Flow completed")
            }.collect {
                println("==>> Received $it")
                if (it == 3)
                    cancel()
            }
        }
    }

    //    Output before adding "cancellable"
//    2023-06-07 13:26:59.830 8185-8185/com.example.kotlinapp I/System.out: ==>> Received 1
//    2023-06-07 13:26:59.830 8185-8185/com.example.kotlinapp I/System.out: ==>> Received 2
//    2023-06-07 13:26:59.830 8185-8185/com.example.kotlinapp I/System.out: ==>> Received 3
//    2023-06-07 13:26:59.832 8185-8185/com.example.kotlinapp I/System.out: ==>> Received 4
//    2023-06-07 13:26:59.832 8185-8185/com.example.kotlinapp I/System.out: ==>> Received 5
//    2023-06-07 13:26:59.833 8185-8185/com.example.kotlinapp I/System.out: ==>> Flow completed


//    //Output after adding "cancellable"
//    2023-06-07 13:26:02.930 8103-8103/com.example.kotlinapp I/System.out: ==>> Received 1
//    2023-06-07 13:26:02.930 8103-8103/com.example.kotlinapp I/System.out: ==>> Received 2
//    2023-06-07 13:26:02.930 8103-8103/com.example.kotlinapp I/System.out: ==>> Received 3
//    2023-06-07 13:26:02.932 8103-8103/com.example.kotlinapp I/System.out: ==>> Flow completed

}