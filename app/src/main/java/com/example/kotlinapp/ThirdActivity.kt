package com.example.kotlinapp

import android.app.Activity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ThirdActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        flowofBuilder()
    }


    private fun testFlow() {
        CoroutineScope(Dispatchers.IO).launch {
            (1..10).asFlow()
                .filter {
                    it % 2 == 0
                }.map {
                    it * it
                }.collect {
                    Log.d("", "Third : $it")
                }
        }
    }

    private fun testflowBuilderTwo() {
        CoroutineScope(Dispatchers.IO).launch {
            flowBuilderTwo().collect {
                Log.d("Third:", "$it")
            }
        }
    }

    private fun flowBuilderTwo(): Flow<Int> = flow {
        for (i in 1..10) {
            kotlinx.coroutines.delay(100)
            emit(i)
        }
    }

    private fun flowBuilderThree(): Flow<Int> = flowOf(1, 2, 7, 8, 9, 0, 9)

    private fun flowofBuilder() {
        CoroutineScope(Dispatchers.IO).launch {
            flowOf(10,4,10,5,6,8,1,34,7)
                .map {
                    it * 1
                }.filter {
                    it%2 ==0
                }.collect {
                    Log.d("Third flowofBuilder:", "$it")
                }
        }
    }

    private fun testflowBuilderThree() {
        CoroutineScope(Dispatchers.Default).launch {
            flowBuilderThree().collect() {

                Log.d("Third Three:", "$it")
            }
        }
    }

}