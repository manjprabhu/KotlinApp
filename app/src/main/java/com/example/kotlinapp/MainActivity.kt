package com.example.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlinapp.databinding.ActivityMainBinding
import androidx.databinding.DataBindingUtil

class MainActivity : AppCompatActivity() {

    lateinit var mBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding =  DataBindingUtil.setContentView(this, R.layout.activity_main)
        add(5, 10)
        addition(12,7)
        nullcheckMethod()
        elvisOperator()
        nullcheckOperator()
    }

    private fun add(a : Int, b: Int ) {
        val sum  = a + b;
        Log.v("sum:", sum.toString())
    }

    //function with default argument
    private fun addition(x: Int = 100, y:Int = 20) {
        val sum =  x + y;
        Log.v("Sum:", sum.toString())
    }

    private fun nullcheckMethod() {
        var a: String? = null
        Log.v("Value of A:", ""+a?.length)

        var b = "this is test"
        Log.v("Value of B:", ""+b.length)

       var c: String? ="hello"
        Log.v("Value of C:", ""+c?.length)

        var d: String?
        d = null
        Log.v("Value of D:", ""+d?.length)

    }

    private fun elvisOperator() {
        var x : String? = "null"

        var y: String?  = "world"

        var z = x?.length?: -1

        Log.v("Value of Z:",""+z);
    }

    private fun nullcheckOperator() {
        var x: String? = null
        val y = x!!.length
        Log.v("Value of x:",""+x);
    }
}