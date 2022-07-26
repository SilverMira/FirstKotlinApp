package com.silvermira.helloworld

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.silvermira.helloworld.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var count = 0
        set(value) {
            field = value
            binding.clickButton.text = String.format(getString(R.string.num_clicks), value)
        };

    private var name = ""
        set(value) {
            field = value
            binding.welcomeText.text = String.format(getString(R.string.welcome), value)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initState()
        registerListeners()
    }

    private fun initState() {
        count = 0
        name = ""
    }

    private fun registerListeners() {
        binding.clickButton.setOnClickListener {
            count++;
        }
        binding.rollDiceButton.setOnClickListener {
            rollDice();
        }
        binding.updateNameButton.setOnClickListener {
            updateName();
        }
    }

    private fun updateName() {
        name = binding.nameInput.text.toString()
        binding.nameInput.text.clear()
        binding.nameInput.clearFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.nameInput.windowToken, 0)
    }

    private fun rollDice() {
        val randomInt = (1..6).random();
        binding.diceImage.setImageResource(getDrawableByNumber(randomInt));
    }

    private fun getDrawableByNumber(randomInt: Int): Int {
        return when (randomInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }
}