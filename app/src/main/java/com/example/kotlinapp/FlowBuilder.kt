package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.coroutines.EmptyCoroutineContext

class FlowBuilder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_builder)
    }

    override fun onResume() {
        super.onResume()
        performLaunchInOperation()
    }

    private fun performFlowBuilderOperation() {

        lifecycleScope.launchWhenResumed {
            flowOf(1).collect {
                println("==>>> flowOne: $it")
            }

            listOf<Int>(1, 5, 9, 1, 3, 77, 32, 56).asFlow().collect {
                println("==>>> FlowTwo: $it")
            }

            flow {
                for (i in 1..10) {
                    emit(i)
                    delay(100)
                }
            }.collect {
                println("==>> Third $it")
            }
        }
    }

    //Terminal operators

    private fun performTerminalOperatorOperation() {

        val flowOne = flow {
            delay(100)

            emit(1)

            delay(200)

            emit(2)
        }

        lifecycleScope.launchWhenResumed {

            flowOne.collect { emittedValue ->
                println("==>> Collect: $emittedValue")
            }

            val first = flowOne.first()
            println("==>> First : $first")

            val firstOrNull = flowOne.firstOrNull()
            println("==>> First Or Null: $firstOrNull")

            val last = flowOne.last()
            println("==>> Last: $last")

            val itemSet = flowOne.toSet()
            println("==>> set item : $itemSet")

            val itemList = flowOne.toList()
            println("==>>> List item $itemList")

            val fold = flowOne.fold(2) { accumlator, emittedValue ->
                accumlator * emittedValue
            }
            println("==>> Fold: $fold")
        }
    }

    private fun performLaunchInOperation() {

        val flowOne = flow {

            emit(1)
            println("==>> Emitted value One")
            delay(500)

            emit(2)
            println("==>> Emitted value Two")
            delay(500)
        }

        val scope = CoroutineScope(EmptyCoroutineContext)

        flowOne
            .onEach { println("==>> LaunchIn element is $it -One") }
            .launchIn(scope)

        flowOne.onEach { println("==>>>  LaunchIn element is $it - two") }
            .launchIn(scope)

        lifecycleScope.launchWhenResumed {
            flowOne.collect {
                println("==>> Collected $it element -One")
            }

            flowOne.collect {
                println("==>> Collected $it element -Two")
            }
        }
    }

}

