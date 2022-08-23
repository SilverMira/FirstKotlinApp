package com.practical.calorie_calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.practical.calorie_calculator.databinding.FragmentCalorieCalculatorBinding

class CalorieCalculatorFragment : Fragment() {
    companion object {
        fun newInstance() = CalorieCalculatorFragment()
    }

    private lateinit var binding: FragmentCalorieCalculatorBinding
    private var dailyCalorie: String? = null
    set(value) {
        field = value
        if(value != null) {
            binding.totalCalorieLabel.text = "$value"
            binding.totalCalorieLabel.visibility = View.VISIBLE
        } else {
            binding.totalCalorieLabel.visibility = View.INVISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalorieCalculatorBinding.inflate(inflater, container, false)
        ArrayAdapter.createFromResource(requireContext(), R.array.age_groups, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.ageGroupSpinner.adapter = adapter
        }
        binding.ageGroupSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                calculateTotalCalorie()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.genderRadioGroup.setOnCheckedChangeListener {
            _, _ -> calculateTotalCalorie()
        }

        return binding.root
    }

    private fun calculateTotalCalorie() {
        val ageGroup = binding.ageGroupSpinner.selectedItem.toString();
        val genderIsMale = binding.genderRadioGroup.checkedRadioButtonId == R.id.maleRadioButton
        val genderIsFemale = binding.genderRadioGroup.checkedRadioButtonId == R.id.femaleRadioButton

        var dailyCalorie: String? = null

        if(genderIsFemale) {
            dailyCalorie = when(ageGroup) {
                getString(R.string.age_group_19_30) -> getString(R.string.calorie_2000_2400)
                getString(R.string.age_group_31_59) -> getString(R.string.calorie_1800_2200)
                getString(R.string.age_group_60plus) -> getString(R.string.calorie_1600_2000)
                else -> null
            }
        } else if(genderIsMale){
            dailyCalorie = when(ageGroup) {
                getString(R.string.age_group_19_30) -> getString(R.string.calorie_2400_3000)
                getString(R.string.age_group_31_59) -> getString(R.string.calorie_2200_3000)
                getString(R.string.age_group_60plus) -> getString(R.string.calorie_2000_2600)
                else -> null
            }
        }

        this.dailyCalorie = dailyCalorie
    }
}