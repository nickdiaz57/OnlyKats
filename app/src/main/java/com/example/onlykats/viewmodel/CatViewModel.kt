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

    private val _catAmount: MutableLiveData<Int> = MutableLiveData<Int>(5)
    val catAmount : LiveData<Int> get() = _catAmount

    fun fetchCats(limit: Int) {
        Log.e("ViewModel", "fetchCats called")
        viewModelScope.launch(Dispatchers.IO) {
            CatRepo.getCatState(limit).collect { catState -> _catState.postValue(catState) }
        }
    }

    fun setCatAmount(amount: Int) {
        Log.e("ViewModel", "setCatAmount called")
        _catAmount.postValue(amount)
        Log.e("ViewModel", "${catAmount.value}")
    }
}