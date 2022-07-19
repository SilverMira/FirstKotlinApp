package com.silvermira.practicals.constrained

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerClickableBox()
    }

    private fun makeColored(view: View) {
        view.setBackgroundColor(
            when (view.id) {
                R.id.boxOne -> Color.YELLOW
                R.id.boxTwo -> Color.BLUE
                R.id.boxThree -> Color.MAGENTA
                R.id.boxFour -> Color.GREEN
                R.id.boxFive -> Color.RED
                else -> Color.LTGRAY
            }
        )
    }

    private fun registerClickableBox() {
        val boxes = listOf(
            R.id.boxOne,
            R.id.boxTwo,
            R.id.boxThree,
            R.id.boxFour,
            R.id.boxFive
        )
        for (box in boxes) {
            findViewById<TextView>(box).setOnClickListener { makeColored(it) }
        }
    }
}