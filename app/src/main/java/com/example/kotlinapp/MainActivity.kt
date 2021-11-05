package com.example.kotlinapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.kotlinapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
//        add(5, 10)
//        addition(12, 7)
//        nullcheckMethod()
//        elvisOperator()
//        assertionOperator()
    }

    private fun add(a: Int, b: Int) {
        val sum = a + b;
        Log.v("sum:", sum.toString())
    }

    //function with default argument
    private fun addition(x: Int = 100, y: Int = 20) {
        val sum = x + y;
        Log.v("Sum:", sum.toString())
    }

    private fun nullcheckMethod() {
        var a: String?
        a = "hello string"
        Log.v("Value of A:", "" + a?.length)

        var b = "this is test"
        Log.v("Value of B:", "" + b.length)

        var c: String? = "hello"
        Log.v("Value of C:", "" + c?.length)

        var d: String?
        d = null
        Log.v("Value of D:", "" + d?.length)

    }

    //Elvis operator (?:)
    //For a non null value Elvis will return left side value
    private fun elvisOperator() {
        var x: String? = "null"

        var y: String? = "world"

        var z = x?.length ?: -1

        Log.v("Value of Z:", "" + z);
    }

    // not-null assertion(!!) operator converts null reference to non null reference and throws NPE if value is null
    private fun assertionOperator() {
        var x: String? = "Hello"
        val y = x!!.length
        Log.v("Value of x:", "" + y);
    }


}