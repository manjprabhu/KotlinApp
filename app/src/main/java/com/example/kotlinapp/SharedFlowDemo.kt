package com.example.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class SharedFlowDemo : AppCompatActivity() {

    private val shareFlow = MutableSharedFlow<Int>()

    private val _mutableSharedFlow = MutableSharedFlow<Int>()

    // Represents this mutable shared flow as a read-only shared flow.
    val sharedFlow = _mutableSharedFlow.asSharedFlow()

    override fun onResume() {
        super.onResume()
        performShareInOperation()
    }

    private fun performSharedFlowOperation() {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            repeat(10) {
                println("==>> Start emitting")
                shareFlow.emit(it)
                delay(200)
            }
        }

        //Wait for 500ms then start collecting the flow, by this time,
        // already 0,1,2 are emitted and collector will get values from 3
        Thread.sleep(500)

        scope.launch {
            shareFlow.collect {
                println("==>> Collected1 $it")
            }
        }

        scope.launch {
            shareFlow.collect {
                println("==>> Collected2 $it")
            }
        }
    }


    //Converting flow to SharedFlow using ShareIn operator

    private fun convertFlowToSharedFlow(): SharedFlow<String> = flow {
        repeat(10) {
            emit(it.toString())
            delay(100)
        }
    }.shareIn(
        scope = lifecycleScope,
        started = SharingStarted.WhileSubscribed()
    )


    private fun performShareInOperation() {
        val scope = CoroutineScope(Dispatchers.Default)
        val sFlow = flow {
            repeat(10) {
                println("==>> Start emitting $it")
                emit(it.toString())
                delay(100)
            }
        }.shareIn(
            scope = lifecycleScope,
            started = SharingStarted.WhileSubscribed()
        )


        Thread.sleep(500)

        scope.launch {
            sFlow.collect {
                println("==>> Received at collector : $it")

            }
        }
    }

    private fun sharedFloeDemo() {
        val scope = CoroutineScope(Job() + Dispatchers.Main)

        val mutableSharedFlow = MutableSharedFlow<Int>(replay = 3, extraBufferCapacity = 5)
        val sharedFlow =  mutableSharedFlow.asSharedFlow()

        scope.launch {
            repeat(10) {
                mutableSharedFlow.emit(it)
                delay(100)
            }
        }

        scope.launch {
            sharedFlow.collect{
                println(it)
            }
        }

        Thread.sleep(300)

        scope.launch {
            sharedFlow.collect{
                println("Second collection :$it")
            }
        }
    }

}