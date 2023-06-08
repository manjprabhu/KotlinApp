package com.example.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StateFlowDemo : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        performStateFlowOperation()
    }

    val counter = MutableStateFlow(0)

    private fun performStateFlowOperation() {

        //has problem in case of multiple coroutine if we use stateflow.value to update the state flow instead use update method
        lifecycleScope.launch {
            repeat(10000) {
                launch {
                    counter.value = counter.value + 1
                }
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

}