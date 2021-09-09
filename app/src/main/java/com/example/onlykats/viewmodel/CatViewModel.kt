package com.example.onlykats.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlykats.model.Cat
import com.example.onlykats.repo.CatRepo
import com.example.onlykats.util.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatViewModel : ViewModel() {

    private val _catState = MutableLiveData<ApiState<List<Cat>>>()
    val catState : LiveData<ApiState<List<Cat>>> get() = _catState

    var limit = 0
    var page = 0
        set(value) {
            if (value > field && isNextPage) fetchCats(limit)
            field = value
        }
    var isNextPage = true
    var breeds = false

    fun fetchCats(limit: Int) {
        Log.e("ViewModel", "fetchCats called")
        this.limit = limit
        viewModelScope.launch(Dispatchers.IO) {
            CatRepo.getCatState(limit, page, breeds).collect { catState ->
                isNextPage =
                    !(catState is ApiState.Failure && catState.errorMsg == CatRepo.NO_DATA_FOUND)
                _catState.postValue(catState)
            }
        }
    }
}