package my.edu.tarc.set4ChinYeowChun

import android.content.Intent
import android.inputmethodservice.InputMethodService
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
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
        val stateSelected = when (binding.stateRadioGroup.checkedRadioButtonId) {
            binding.stateRadioButtonSabahSarawak.id -> true
            binding.stateRadioButtonPeninsular.id -> true
            else -> false
        }
        if (registrationNumber.isNullOrEmpty()) {
            binding.carRegistrationTextInputLayout.error = "Car Registration Number is required"
            passes = false
        }
        if (!stateSelected) {
            binding.stateErrorTextView.text = getString(R.string.state_required_error)
            binding.stateErrorTextView.visibility = View.VISIBLE
            passes = false
        }

        return passes
    }

    private fun clearValidation() {
        binding.carRegistrationTextInputLayout.error = null
        binding.stateErrorTextView.text = ""
        binding.stateErrorTextView.visibility = View.INVISIBLE
    }

    private fun calculateRoadTax() {
        clearValidation()
        val imeManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imeManager.hideSoftInputFromWindow(
            binding.carRegistrationTextInputLayout.editText?.windowToken,
            0
        )
        if (validateInput()) {
            val carRegistration = binding.carRegistrationTextInputLayout.editText!!.text.toString()
            val engineCapacity = binding.engineCapacitySpinner.selectedItem.toString()
            val state = when(binding.stateRadioGroup.checkedRadioButtonId) {
                binding.stateRadioButtonPeninsular.id -> binding.stateRadioButtonPeninsular.text
                binding.stateRadioButtonSabahSarawak.id -> binding.stateRadioButtonSabahSarawak.text
                else -> null
            }
            val roadTaxAmount: Float? = when (state) {
                getString(R.string.state_peninsular) -> when (engineCapacity) {
                    getString(R.string.engine_cc_1000_below) -> 20F
                    getString(R.string.engine_cc_1001_1200) -> 55F
                    getString(R.string.engine_cc_1201_1400) -> 70F
                    getString(R.string.engine_cc_1401_1600) -> 90F
                    getString(R.string.engine_cc_1601_above) -> 200F
                    else -> null
                }
                getString(R.string.state_sabah_sarawak) -> when (engineCapacity) {
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
                binding.roadTaxCardRegistrationValue.text = carRegistration
                binding.roadTaxCardEngineValue.text = engineCapacity
                binding.roadTaxCardStateValue.text = state
            }
        }
    }

    private fun contactPhone() {
        Intent(Intent.ACTION_DIAL, Uri.parse("tel:+60169121396")).also {
            startActivity(it)
        }
    }

    private fun contactEmail() {
        Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:chinyeowchun@gmail.com")).apply {
            putExtra(Intent.EXTRA_EMAIL, "chinyeowchun@gmail.com")
        }.also {
            startActivity(it)
        }
    }
}