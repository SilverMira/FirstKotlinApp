package com.practical.testingactivitychild

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practical.testingactivitychild.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra("name")
        val campus = intent.getStringExtra("campus")
        val cgpa = intent.getFloatExtra("cgpa", -1F)
        val award = intent.getStringExtra("award")
        val prize = intent.getFloatExtra("prize", 0F)

        binding = ActivitySubBinding.inflate(layoutInflater)
        binding.nameTextView.text = name
        binding.CampusTextView.text = campus
        binding.CGPATextView.text = String.format("%.4f", cgpa)
        binding.AwardTextView.text = award
        binding.PrizeTextView.text = String.format("%.2f", prize)
        binding.closeButton.setOnClickListener {
            finish()
        }
        setContentView(binding.root)

    }
}