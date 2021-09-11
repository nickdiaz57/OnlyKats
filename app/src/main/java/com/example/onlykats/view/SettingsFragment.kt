package com.example.onlykats.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

        breedToggle.isChecked = catViewModel.hasBreeds
        breedToggle.setOnCheckedChangeListener { _, isChecked ->
            catViewModel.hasBreeds = isChecked
            Log.e("Settings Fragment", "breeds is set to ${catViewModel.hasBreeds}")
        }

        applyButton.setOnClickListener{
            catViewModel.fetchCats(catAmountSlider.value.toInt())
        }

        pngCheckbox.isChecked = catViewModel.fileType.contains("png,")
        pngCheckbox.setOnCheckedChangeListener{ _, _ ->
            catViewModel.fileType = "png,"
        }

        jpgCheckbox.isChecked = catViewModel.fileType.contains("jpg,")
        jpgCheckbox.setOnCheckedChangeListener{ _, _ ->
            catViewModel.fileType = "jpg,"
        }

        gifCheckbox.isChecked = catViewModel.fileType.contains("gif,")
        gifCheckbox.setOnCheckedChangeListener{ _, _ ->
            catViewModel.fileType = "gif,"
        }
    }

    private fun toggleApply(dataChanged: Boolean) {
        binding.applyButton.isVisible = dataChanged
    }
}