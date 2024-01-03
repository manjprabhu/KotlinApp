package com.example.kotlinapp.interoperability

//Generally Companion object is used in fatorydesign pattern
class CompanionClass {

    object myObject {

        @JvmStatic
        fun f() {
            println("==>> Hello im from Object declaration!!!!")
        }
    }

    companion object anotherObject {
        fun g() {
            println("==>> Hello Im from Companion Object ....")
        }
    }



}