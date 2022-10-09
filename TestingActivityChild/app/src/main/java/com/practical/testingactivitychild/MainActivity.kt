package com.practical.testingactivitychild

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.practical.testingactivitychild.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.campusSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.campus,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.resetButton.setOnClickListener {
            reset()
        }
        binding.submitButton.setOnClickListener {
            clearErrors()
            if (validateInput()) {
                val name = binding.nameTextInputEditText.text.toString()
                val campus = binding.campusSpinner.selectedItem.toString()
                val cgpaFloat = binding.cgpaTextInputEditText.text.toString().toFloat()
                val award =
                    if (cgpaFloat >= 3.75) "Distinction" else if (cgpaFloat >= 2.75) "Merit" else if (cgpaFloat >= 2.0) "Pass" else "Fail";
                val prize: Float =
                    if (cgpaFloat >= 3.75) 100F else if (cgpaFloat >= 2.75) 50F else 0F
                val intent = Intent(this, SubActivity::class.java).apply {
                    putExtra("name", name)
                    putExtra("campus", campus)
                    putExtra("cgpa", cgpaFloat)
                    putExtra("award", award)
                    putExtra("prize", prize)
                }
                startActivity(intent)
            }
        }
        binding.websiteButton.setOnClickListener {
            val urlIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://www.tarc.edu.my")
            }
            startActivity(urlIntent)
        }
        reset()
        setContentView(binding.root)
    }

    private fun clearErrors() {
        binding.nameTextInputLayout.error = null
        binding.cgpaTextInputLayout.error = null
    }

    private fun validateInput(): Boolean {
        val name = binding.nameTextInputEditText.text.toString()
        val cgpa = binding.cgpaTextInputEditText.text.toString().toFloatOrNull()
        var passing = true
        if (name.isEmpty()) {
            binding.nameTextInputLayout.error = "Name is required"
            passing = false
        }
        if (cgpa == null) {
            binding.cgpaTextInputLayout.error = "CGPA is required"
            passing = false
        } else if (cgpa < 0 || cgpa > 4) {
            binding.cgpaTextInputLayout.error = "CGPA must be within 0.0000 - 4.0000"
            passing = false
        }

        return passing
    }

    private fun reset() {
        binding.campusSpinner.setSelection(0)
        binding.nameTextInputEditText.text?.clear()
        binding.cgpaTextInputEditText.text?.clear()
        binding.nameTextInputEditText.requestFocus()
    }
}