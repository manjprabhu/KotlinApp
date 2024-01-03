package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

class FlowOperator : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        conflationDemo()
        collectAndCollectLatest()
    }

    private fun flowBuilderOne(): Flow<Int> = flow {
        for (i in 1..10) {
            emit(i)
            delay(100)
        }
    }

    private fun flowBuilderTwo(): Flow<Int> = flow {
        emit(1)
        delay(100)

        emit(2)
        delay(100)

        emit(3)
        delay(100)

        emit(4)
        delay(100)

        emit(5)
        delay(100)
    }


    //Size limiting operator
    private fun operatorDemo() = runBlocking {
        flowBuilderOne().take(5).collect { println(it) }
    }

    //terminal operator

    //REDUCE Operator
    private fun operatorTwo() = runBlocking {
        val product = flowBuilderOne().reduce { a, b -> a * b }
        println(product)
    }

    private fun flowTerminalOperator() {
        val numbersFlow = flowOf(1, 2, 3, 4, 5, 6, 7, 8, 8, 4, 1, 10)

        //FIRST operator
        CoroutineScope(Dispatchers.Main).launch {
            val first = numbersFlow.first { it % 2 == 0 }
            println("==>> Result of First    ...$first")
        }

        //COLLECT operator
        CoroutineScope(Dispatchers.Main).launch {
            numbersFlow.collect {
                println("==>> Collect  Elements are ...$it")
            }
        }

        //TOLIST
        CoroutineScope(Dispatchers.Main).launch {
            val numberList = numbersFlow.toList()
            println("==>> Number list is :$numberList")
        }

        //TOSET
        CoroutineScope(Dispatchers.Main).launch {
            val numberSet = numbersFlow.toSet()
            println("==>> Number set is :$numberSet")
        }

        //REDUCE operator: used to aggregate the flow data in some way
        //e.g to find the sum of all data in a flow
        CoroutineScope(Dispatchers.Main).launch {
            val result = numbersFlow.reduce { accumulator, value -> accumulator + value }
            println("==>> Reduce result is  :$result")
        }
    }

    private fun collectAndCollectLatest() {
        val intFlow = flow {
            (1..5).forEach {
                delay(50)
                println("==>> Emitting $it")
                emit(it)
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            intFlow.collect {
                delay(100)
                println("==>> Collecting $it")
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            intFlow.collectLatest {
                delay(100)
                println("==>> Collecting Latest $it")
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun flowIntermediateOperator() {
        val numberFlow = (1..10).asFlow()

        //MAP operator
        CoroutineScope(Dispatchers.Main).launch {
            numberFlow.map { it * it }
                .collect { r ->
                    println("==>> Result of Map Operator    :$r")
                }
        }

        //TRANSFORM operator: Used to transfer the one data type to another
        CoroutineScope(Dispatchers.Main).launch {
            numberFlow.transform { value -> emit(value + value) }
                .collect { result ->
                    println("==>> result of Transform is : $result")
                }
        }


        //FILTER operator
        CoroutineScope(Dispatchers.Main).launch {
            numberFlow.filter { it % 2 == 0 }
                .collect { r ->
                    println("==>> Filter Result is  :$r")
                }
        }

        //ZIP operator : used to combine multiple flow operator into single flow.
        val flow1 = listOf(0, 2, 4, 6, 8, 10).asFlow()
        val flow2 = flowOf(1, 3, 5, 7, 9, 11)

        CoroutineScope(Dispatchers.Main).launch {
            flow1.zip(flow2) { a, b ->
                a + b
            }.collect { r ->
                println("==>> Result of Zip operator is :$r")
            }
        }

        //COMBINE operator: same as Zip , but difference b/w 2 is combine will emit the value
        // as soon as one of the flow emits
        CoroutineScope(Dispatchers.Main).launch {
            flow1.combine(flow2) { a, b ->
                a + b
            }.collect { r ->
                println("==>> Result of Combine operator is :$r")
            }
        }

        //MERGE operator: same as Combine , It keeps the order of items as they emitted from flows.
        // But it doesn’t guarantee the order of items.
        val mergedFlow = merge(flow1, flow2)

        CoroutineScope(Dispatchers.Main).launch {
            mergedFlow.collect {
                println(" ==>> Merged flow is : $it")
            }
        }

        //DROP : used to drop the first specified values
        CoroutineScope(Dispatchers.Main).launch {
            numberFlow.drop(2).collect {
                println("==>> Result of Drop is :$it")
            }
        }

        //TAKE : used to take the first specified values
        CoroutineScope(Dispatchers.Main).launch {
            numberFlow.take(2).collect {
                println("==>> Result of take is :$it")
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            coldFlow().collect {
                println(it)
            }
        }
    }

    private fun coldFlow() = flow {
        for (i in 1..3) {
            emit(i)
            delay(100)
        }
    }.flowOn(Dispatchers.Default)

    //We can use a buffer operator on a flow to run emitting code of the simple flow concurrently with collecting code,
    // as opposed to running them sequentially:
    //100ms to produce the flow and 300ms to collect the flow, without buffer operator collecting 3 date from flow would have taken 1200 ms

    fun bufferDemo() = runBlocking {
        CoroutineScope(Job()).launch {
            coldFlow().buffer().collect { value ->
                delay(300)
                println(value)
            }
        }
    }

    //Conflate the emission nd don't process the each and  every item
    private fun conflationDemo() = runBlocking {
        val time = measureTimeMillis {
            CoroutineScope(Job()).launch {
                coldFlow().conflate().collect { value ->
                    println("==>> Conflate Collecting value  $value")
                    delay(300)
                    println("==>> Done $value")
                }
            }
        }
        println("==>> 2  Time taken $time")
    }

    private fun processLatestValue() = runBlocking {
        val time = measureTimeMillis {
            CoroutineScope(Job()).launch {
                coldFlow().collectLatest { value ->
                    println("==>> collectLatest Collecting value  $value")
                    delay(300)
                    println("==>> Done $value")
                }
            }
        }
        println("==>> 3  Time taken $time")
    }
}