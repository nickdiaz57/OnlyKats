package com.example.onlykats.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlykats.model.Cat
import com.example.onlykats.repo.CatRepo
import com.example.onlykats.util.ApiState
import com.example.onlykats.util.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatViewModel : ViewModel() {

    private val _catState = MutableLiveData<ApiState<List<Cat>>>()
    val catState : LiveData<ApiState<List<Cat>>> get() = _catState

    var limit = 0
    var isNextPage = true
    var hasBreeds = false
    var page = 0
        set(value) {
            if (value > field && isNextPage) fetchCats(limit)
            field = value
        }
    var fileType = ""
        set(value) {
            field = if (value in field) {
                field.replace(value, "")
            } else {
                value + field
            }
        }

    fun fetchCats(limit: Int) {
        this.limit = limit
        viewModelScope.launch(Dispatchers.IO) {
            CatRepo.getCatState(limit, page, hasBreeds, Order.RANDOM.name, fileType).collect { catState ->
                isNextPage =
                    !(catState is ApiState.Failure && catState.errorMsg == CatRepo.NO_DATA_FOUND)
                _catState.postValue(catState)
            }
        }
    }
}