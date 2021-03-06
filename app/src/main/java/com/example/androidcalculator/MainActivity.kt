package com.example.androidcalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var lastNumeric : Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        var savedTvInput = findViewById<TextView>(R.id.tvInput).text.toString()
        // Save UI state changes to the savedInstanceState.
        savedInstanceState.putString("MyString", savedTvInput)
        savedInstanceState.putBoolean("lastNumeric", lastNumeric)
        savedInstanceState.putBoolean("lastDot", lastDot)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restore UI state from the savedInstanceState.
        val myString = savedInstanceState.getString("MyString")
        var tvInput = findViewById<TextView>(R.id.tvInput)
        tvInput.text = myString
        lastNumeric = savedInstanceState.getBoolean("lastNumeric")
        lastDot = savedInstanceState.getBoolean("lastDot")
    }

    fun onDigit(view: View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

     fun onClear(view: View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View){
        if (lastNumeric && !lastDot){
            val tvInput = findViewById<TextView>(R.id.tvInput)
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View){
        if (lastNumeric){
            val tvInput = findViewById<TextView>(R.id.tvInput)
            var tvValue = tvInput.text.toString()
            var prefix = ""
            try{
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")

                    var first = splitValue[0]
                    val second = splitValue[1]

                    if(prefix.isNotEmpty()){
                       first = prefix + first
                    }

                    var result = (first.toDouble() - second.toDouble()).toString()

                    tvInput.text = removeZeroAfterDot(result)
                } else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")

                    var first = splitValue[0]
                    var second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    var result = (first.toDouble() + second.toDouble()).toString()

                    tvInput.text = removeZeroAfterDot(result)
                } else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")

                    var first = splitValue[0]
                    var second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    var result = (first.toDouble() * second.toDouble()).toString()

                    tvInput.text = removeZeroAfterDot(result)
                } else if (tvValue.contains("*")){
                    val splitValue = tvValue.split("*")

                    var first = splitValue[0]
                    var second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    var result = (first.toDouble() * second.toDouble()).toString()

                    tvInput.text = removeZeroAfterDot(result)
                } else if (tvValue.contains("/")){
                    val splitValue = tvValue.split("/")

                    var first = splitValue[0]
                    var second = splitValue[1]

                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    var result = (first.toDouble() / second.toDouble()).toString()

                    tvInput.text = removeZeroAfterDot(result)
                }

            } catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }


    }

    fun onOperation(view: View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if (value.startsWith(prefix = "-")){
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }

    private fun removeZeroAfterDot(value: String) : String {
        var result = value
        if (result.takeLast(2) == ".0"){
            result = result.dropLast(2)
        }
        return result
    }


}