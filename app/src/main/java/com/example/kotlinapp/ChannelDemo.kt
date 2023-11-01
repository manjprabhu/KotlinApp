package com.example.kotlinapp

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

class ChannelDemo {

    private val channel = Channel<Language>()

    private var receiveChannel: ReceiveChannel<Language> = Channel()

    private fun demo() = runBlocking {

        val scope = CoroutineScope(Job())

        //channel with buffer capacity 2 (i.e Buffer channel)
        scope.launch {
            receiveChannel = produce(capacity = 2) {
                send(Language.python)
                send(Language.kotlin)
                send(Language.javascript)
                send(Language.java)
            }
        }

        //Conflated channel, capacity limit is 1 and override the previous element.
        scope.launch {
            receiveChannel = produce(capacity = Channel.CONFLATED) {
                send(Language.python)
                send(Language.kotlin)
                send(Language.javascript)
                send(Language.java)
            }
        }


        //UNLIMITED channel,buffer capacity is unlimited
        scope.launch {
            receiveChannel = produce(capacity = Channel.UNLIMITED) {
                send(Language.python)
                send(Language.kotlin)
                send(Language.javascript)
                send(Language.java)
            }
        }

        //Capacity zero , rendezvous
        scope.launch {
            receiveChannel = produce {
                send(Language.python)
                send(Language.javascript)
                send(Language.kotlin)
            }
        }


        scope.launch {
            channel.send(Language.java)
            channel.send(Language.python)
            channel.close() // close the channel once all the element are sent.
        }

        scope.launch {
            println(" $channel.isClosedForReceive")
            channel.receive()
            channel.receive()
            println(" $channel.isClosedForReceive")
        }

        scope.launch {
            channel.consumeEach {
                println(it)
            }
        }

        //receiver with channel capacity producer
        scope.launch {
            println("${receiveChannel.receive()}")
            delay(3000)
            println("${receiveChannel.receive()}")
            delay(3000)
            println("${receiveChannel.receive()}")
            delay(3000)
            println("${receiveChannel.receive()}")
        }

        //Receiver for Conflated channel
        scope.launch {
            receiveChannel.consumeEach {
                println("${receiveChannel.receive()}") // this will receive only the last value from channel producer
            }
        }

    }

    enum class Language {
        java,
        kotlin,
        javascript,
        python

    }
}