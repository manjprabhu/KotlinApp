package com.example.kotlinapp.interoperability

data class Session @JvmOverloads constructor(
    @JvmField val paramOne: String,
    @JvmField val paramTwo: String,
    @JvmField val paramThree: String = "default third param"
)