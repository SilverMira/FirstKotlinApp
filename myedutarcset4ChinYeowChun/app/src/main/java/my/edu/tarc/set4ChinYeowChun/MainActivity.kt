package my.edu.tarc.set4ChinYeowChun

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import my.edu.tarc.set4ChinYeowChun.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.engineCapacitySpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.engine_ccs,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.contactEmailButton.setOnClickListener {
            contactEmail()
        }
        binding.contactPhoneButton.setOnClickListener {
            contactPhone()
        }
        binding.calculateRoadTaxButton.setOnClickListener {
            calculateRoadTax()
        }

        setContentView(binding.root)
    }

    private fun validateInput(): Boolean {
        var passes = true
        val registrationNumber = binding.carRegistrationTextInputLayout.editText?.text?.toString()
        if (registrationNumber.isNullOrEmpty()) {
            binding.carRegistrationTextInputLayout.error = "Car Registration Number is required"
            passes = false
        }

        return passes
    }

    private fun clearValidation() {
        binding.carRegistrationTextInputLayout.error = null
    }

    private fun calculateRoadTax() {
        clearValidation()
        if (validateInput()) {
            val engineCapacity = binding.engineCapacitySpinner.selectedItem.toString()
            val roadTaxAmount: Float? = when (binding.stateRadioGroup.checkedRadioButtonId) {
                binding.stateRadioButtonPeninsular.id -> when (engineCapacity) {
                    getString(R.string.engine_cc_1000_below) -> 20F
                    getString(R.string.engine_cc_1001_1200) -> 55F
                    getString(R.string.engine_cc_1201_1400) -> 70F
                    getString(R.string.engine_cc_1401_1600) -> 90F
                    getString(R.string.engine_cc_1601_above) -> 200F
                    else -> null
                }
                binding.stateRadioButtonSabahSarawak.id -> when (engineCapacity) {
                    getString(R.string.engine_cc_1000_below) -> 20F
                    getString(R.string.engine_cc_1001_1200) -> 44F
                    getString(R.string.engine_cc_1201_1400) -> 56F
                    getString(R.string.engine_cc_1401_1600) -> 72F
                    getString(R.string.engine_cc_1601_above) -> 160F
                    else -> null
                }
                else -> null
            }
            roadTaxAmount?.let {
                binding.roadTaxCardAmountValue.text = String.format("%.2f", it)
            }
        }
    }

    private fun contactPhone() {
        Intent(Intent.ACTION_DIAL, Uri.parse("tel:+60169121396")).also {
            startActivity(it)
        }
    }

    private fun contactEmail() {
        Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")).apply {
            putExtra(Intent.EXTRA_EMAIL, "chinyeowchun@gmail.com")
        }.also {
            startActivity(it)
        }
    }
}