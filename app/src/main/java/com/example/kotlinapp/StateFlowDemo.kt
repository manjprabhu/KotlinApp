package com.example.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect

class StateFlowDemo : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        performStateFlowOperation()
    }

    private val counter = MutableStateFlow(0)

    private fun performStateFlowOperation() {

        //has problem in case of multiple coroutine if we use stateflow.value to update
        // the state flow instead use update method
        lifecycleScope.launch {
            repeat(100) {
                launch {
                    counter.value = counter.value + 1
                }
            }
        }

        lifecycleScope.launch {
            counter.collect {
                println("==>> . Counter  : $it")
            }
        }
        println("==>> Counter : ${counter.value}")
    }


    private fun updateStateFlow() {
        lifecycleScope.launch {

            repeat(10000) {
                launch {

                }
            }
        }
    }

    @OptIn(InternalCoroutinesApi::class)
    private fun stateFlowDemo() = runBlocking {
        val mutableFlow = MutableStateFlow<Int>(0)
        val stateFlow: StateFlow<Int> = mutableFlow

        val mutableStateFlow = MutableStateFlow(0)
        // Represents this mutable state flow as a read-only state flow.
        val mStateFlow = mutableStateFlow.asStateFlow()

        val scope = CoroutineScope(Job())

        repeat(25) {
            mutableFlow.value = it
        }

        scope.launch {
            stateFlow.collect {
                println(it)
            }
        }

        scope.launch {
            stateFlow.collect {
                println(it)
            }
        }

    }
}