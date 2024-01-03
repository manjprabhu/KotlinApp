package com.example.kotlinapp.interoperability

//Example of factory pattern using companion

class Notification(private val type: String) {

    object Factory {
        // this is factory object

        fun createNotification(type: String): Notification {
            return when (type) {
                "Message" -> Notification("Message ")
                "Call" -> Notification("Call Notification")
                else -> Notification("Default Notification")
            }
        }
    }

    override fun toString(): String {
        return "==>> Notification (type = $type)"
    }
}