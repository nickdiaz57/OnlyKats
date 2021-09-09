package com.example.onlykats.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.onlykats.R
import com.example.onlykats.databinding.FragmentBrowseBinding
import com.example.onlykats.model.Cat
import com.example.onlykats.util.ApiState
import com.example.onlykats.view.adapter.CatAdapter
import com.example.onlykats.viewmodel.CatViewModel

class BrowseFragment : Fragment(R.layout.fragment_browse) {

    private lateinit var binding: FragmentBrowseBinding
    private val catViewModel by activityViewModels<CatViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBrowseBinding.bind(view)

        initViews()
        setupObservers()
    }

    // with(receiver) is 1 of 5 scope functions
    private fun initViews() = with(binding) {
        rvCats.adapter = CatAdapter()
        scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == (v as NestedScrollView).getChildAt(0).measuredHeight - v.getMeasuredHeight()) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.
                catViewModel.page++
                // on below line we are making our progress bar visible.
                binding.pbLoading.isVisible = true
            }
        }
    }

    private fun setupObservers() = with(catViewModel) {
        catState.observe(viewLifecycleOwner) { state ->
            binding.pbLoading.isVisible = state is ApiState.Loading
            if (state is ApiState.Success) handleSuccess(state.data)
            if (state is ApiState.Failure) handleFailure(state.errorMsg)
        }
    }

    private fun handleSuccess(cats: List<Cat>) {
        Log.e(TAG, "ApiState.Success: $cats")
        (binding.rvCats.adapter as CatAdapter).updateList(cats)
    }

    private fun handleFailure(errorMsg: String) {
        Log.e(TAG, "ApiState.Failure: $errorMsg")
    }

    companion object {
        private const val TAG = "BrowseFragment"
    }
}

