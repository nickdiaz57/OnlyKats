package com.example.onlykats.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.onlykats.R
import com.example.onlykats.databinding.FragmentSettingsBinding
import com.example.onlykats.viewmodel.CatViewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding
    private val catViewModel by activityViewModels<CatViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)
        initView()
    }

    private fun initView() = with(binding) {

        catAmountSlider.value = catViewModel.limit.toFloat()
        catAmountSlider.addOnChangeListener { _, value, _ ->
            toggleApply(catViewModel.limit != value.toInt())
        }

        breedToggle.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked == catViewModel.breeds) {
//                catViewModel.breeds = isChecked
//            }
            catViewModel.breeds = isChecked
        }

        applyButton.setOnClickListener{
            catViewModel.fetchCats(catAmountSlider.value.toInt())
        }
    }

    private fun toggleApply(dataChanged: Boolean) {
        binding.applyButton.isVisible = dataChanged
    }
}