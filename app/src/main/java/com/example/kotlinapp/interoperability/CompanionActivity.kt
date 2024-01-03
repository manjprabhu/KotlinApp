package com.example.kotlinapp.interoperability

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CompanionActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        factoryDemo()
    }

    private fun factoryDemo() {
        println("==>> Factory demo")
        val notification: Notification = Notification.Factory.createNotification("Message")
        println(notification)
    }
}