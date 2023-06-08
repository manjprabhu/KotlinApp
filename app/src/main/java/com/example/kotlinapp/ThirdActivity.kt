package com.example.kotlinapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.math.BigInteger

class ThirdActivity : AppCompatActivity() {

    val sFlow = MutableStateFlow(0)

    private val _tickFlow = MutableSharedFlow<Int>(replay = 0)
    val tickFlow: SharedFlow<Int> = _tickFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        runBlocking {
//            sharedFlowOperation()
//        }
    }

    override fun onResume() {
        super.onResume()
        testSharedFlow()

        runBlocking {
            sharedFlowOperation()
        }
    }


    private suspend fun sharedFlowOperation() {
        for (i in 1..10) {
            _tickFlow.emit(i)
        }
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
        lifecycleScope.launch {
            flowBuilderTwo().collect {
                Log.d("Third:", "$it")
            }
        }
//        CoroutineScope(Dispatchers.IO).launch {
//            flowBuilderTwo().collect {
//                Log.d("Third:", "$it")
//            }
//        }
    }

    private fun flowBuilderTwo(): Flow<Int> = flow {
        for (i in 1..10) {
            delay(100)
            emit(i)
        }
    }

    private fun flowBuilderThree(): Flow<Int> = flowOf(1, 2, 7, 8, 9, 0, 9)

    private fun flowOfBuilder() {
        CoroutineScope(Dispatchers.IO).launch {
            flowOf(10, 4, 10, 5, 6, 8, 1, 34, 7)
                .map {
                    it * 1
                }.filter {
                    it % 2 == 0
                }.collect {
                    Log.d("Third flowOfBuilder:", "$it")
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


    private fun testScopeFunctionLet() {
        Person().let {
            it.display()
            it.setPersonName("Hello")
            it.setPersonAge(30)
            it.display()
        }
    }

    private fun withoutLet() {
        val person = Person()
        person.display()
        person.setPersonName("NEW NAME")
        person.setPersonAge(78)
        person.display()
    }


    private fun testScopeFuntionapply() {
        val person = Person().apply {
            setPersonName("Name is Apply")
            setPersonAge(10)
        }
        person.display()
    }

    private fun testScopeFuntionapplyTwo() {
        val list = mutableListOf<Int>()
        list.apply {
            add(10)
            this.add(20)
            this.add(30)
            this.add(6)
            this.add(8)
        }.also {
            println("Print list $it")
        }.sort()
        println("Sorted list is $list")
    }

    private fun testScopeFuntionrun() {
        val list = mutableListOf(6, 3, 7, 11, 30)
        val result = list.run {
            this.add(10)
            this.add(40)
            this.add(4)
            count { it % 2 == 0 }
        }
        println("after run $result")
    }

    private fun testScopeFuntionrunTwo() {
        val nameList = mutableListOf<String>()
        val result = nameList.run {
            apply {
                add("One")
                add("two")
                add("three")
                add("four")
                add("five")
            }.also {
                println("Before Final result $it")
            }
            count { it.length == 3 }
        }
        println("Result List: $result")
    }


    //Cold flow

    fun getNumbersColdFlow(): Flow<Int> = flow {
        for (i in 1..10) {
            delay(1000)
            emit(i)
        }
    }

    private fun getColdFlow() {
        val numberFlowCollect = getNumbersColdFlow()

        lifecycleScope.launch {
            numberFlowCollect.collect {
                Log.d("==>>  First collector:", "$it")
            }

            delay(2000)

            numberFlowCollect.collect {
                Log.d("==>> 2nd collector:", "$it")
            }
        }
    }

    private suspend fun performStateFlowOperation() {
        for (i in 1..10)
            sFlow.value = i
    }

    private fun stateFlowTest() {
        lifecycleScope.launchWhenResumed {
            sFlow.collect {
                Log.d("TAG", "==>> $it")
            }
        }
    }

    private fun shareInOperation(): SharedFlow<Int> {
        val list = flow {
            for (i in 1..10) {
                delay(100)
                emit(i)
            }

        }
        return list.shareIn(
            CoroutineScope(Dispatchers.IO),
            started = SharingStarted.WhileSubscribed(),
            replay = 0
        )
    }

    private fun testShareIn() {
        val value = shareInOperation()

        lifecycleScope.launchWhenStarted {
            value.collect {
                Log.d("TAG", "First Collector ==>>> $it")
            }
        }
    }

    private fun testSharedFlow() {
        lifecycleScope.launchWhenStarted {
            _tickFlow.collect {
                Log.d("TAG", "testSharedFlow ==>> $it")
            }
        }

        lifecycleScope.launchWhenStarted {
            delay(3000)
            _tickFlow.collect {
                Log.d("TAG", " 2testSharedFlow ==>> $it")
            }
        }


    }

    private fun testFactorialFlow() {
        val factorial = calculateFactorial(5)
        lifecycleScope.launchWhenResumed {
            factorial.collect {
                println("==>>> ${it}")
            }
        }
    }

    private fun calculateFactorial(value: Int): Flow<BigInteger> = flow {
        var factorial = BigInteger.ONE
        for (i in 1..value) {
            delay(100)
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
            emit(factorial)
        }
    }.flowOn(Dispatchers.Default)
}