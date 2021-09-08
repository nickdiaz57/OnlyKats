package com.example.onlykats.view

import android.os.Bundle
import android.view.View
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

        binding.changeButton.setOnClickListener{
            catViewModel.setCatAmount(10)
//            Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_browseFragment)
        }
    }
}