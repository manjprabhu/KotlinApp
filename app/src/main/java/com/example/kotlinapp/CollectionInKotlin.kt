package com.example.kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.TreeMap

class CollectionInKotlin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        methodSeven()
    }

    private fun methodOne() {
        val map = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 4)
        println("==>> $map")
        println("==>> Entries: ${map.entries}")
        println("==>> Keys :  ${map.keys}")
        println("==>> Values: ${map.values}")

    }

    private fun methodTwo() {
        val map = mutableMapOf("Ten" to 10, "Eleven" to 11)
        map["One"] = 1
        map["two"] = 2
        map["three"] = 3
        map["four"] = 4
        map["five"] = 5

        map.also {
            it["Six"] = 6
            it["Seven"] = 7
        }
        println("==>> $map")
    }

    private fun methodThree() {
        val map = HashMap<String, Int>(10, 2F)
        map["One"] = 22
        map["two"] = 23
        map["three"] = 73
        map["four"] = 64
        map["five"] = 95

        //println("==>> $map")

        // println("==>>*************** After ***************************")

        map.also {
            it["Six"] = 60
            it["Seven"] = 70
            it.remove("five")
        }
        //println("==>> $map")


        //Another way to create hashmap
        val mapOne = hashMapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 4)
        println("==>> $mapOne")

        println("==>>*************** After ***************************")

        mapOne["Key5"] = 5

        mapOne.also {
            mapOne.remove("key2")
            mapOne["Key6"] = 6
            mapOne["key2"] = 122
        }
        println("==>>> $mapOne")
    }

    private fun methodFour() {
        val map = LinkedHashMap<String, Int>()
        map.also {
            it["three"] = 73
            it["One"] = 22
            it["five"] = 95
            it["two"] = 23
            it["four"] = 64

        }
        println("==>>*************** LinkedHashMap ***************************")
        //println("==>>> $map")

        val mapOne = linkedMapOf<String,Int>()

        mapOne.also {
            it["three"] = 12
            it["One"] = 34
            it["five"] = 44
            it["two"] = 78
            it["four"] = 3
        }

        println("==>>> $mapOne")
    }

    private fun methodFive() {
        val map = TreeMap<String,Int>()
        map.also {
            it["three"] = 73
            it["one"] = 22
            it["five"] = 95
            it["two"] = 23
            it["four"] = 64
        }
        println("==>>********** TreeMap ******************")
        println("==>>> $map")
    }

    private fun methodSeven() {
        val set = setOf(1,2,3,6,8,9,7,41,2,3,4)
        println("==>> $set")

        val mSet = mutableSetOf(1,2,4,6,8,92,3,4,5,6,7)

        mSet.also {
            mSet.add(19)
            mSet.add(69)
            mSet.add(19)
            mSet.add(49)
            mSet.add(79)
        }
        println("==>>******** MutableSet ***********")
        println("==>>> $mSet")
    }
}