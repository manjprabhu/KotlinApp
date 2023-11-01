package com.example.kotlinapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ChannelDemo {

    private val channel = Channel<Language>()

    private var receiveChannel: ReceiveChannel<Language> = Channel()

    private fun demo() = runBlocking {

        val scope = CoroutineScope(Job())

        scope.launch {
            receiveChannel = produce {
                send(Language.python)
                send(Language.javascript)
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
    }

    enum class Language {
        java,
        kotlin,
        javascript,
        python

    }
}