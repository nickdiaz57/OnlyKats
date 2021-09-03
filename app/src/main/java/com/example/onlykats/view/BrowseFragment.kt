package com.example.onlykats.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.onlykats.R
import com.example.onlykats.databinding.FragmentBrowseBinding

class BrowseFragment : Fragment(R.layout.fragment_browse){

    private lateinit var binding: FragmentBrowseBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBrowseBinding.bind(view)

        binding.button2.setOnClickListener {Navigation.findNavController(view).navigate(R.id.action_browseFragment_to_detailFragment)}
        binding.button.setOnClickListener {Navigation.findNavController(view).navigate(R.id.action_browseFragment_to_settingsFragment)}
    }
}