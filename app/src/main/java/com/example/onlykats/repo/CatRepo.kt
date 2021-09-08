package com.example.onlykats.repo
import com.example.onlykats.repo.remote.RetrofitInstance
import com.example.onlykats.util.ApiState
import com.example.onlykats.util.Order
import kotlinx.coroutines.flow.flow

object CatRepo {

    private val catService by lazy { RetrofitInstance.catService }

    fun getCatState(limit: Int, page: Int = 1, order: Order = Order.DESC) = flow {
        emit(ApiState.Loading)

        val catResponse = catService.getCatImages(limit, page, order)

        val state = if (catResponse.isSuccessful) {
            if (catResponse.body().isNullOrEmpty()) {
                ApiState.Failure("No data found")
            } else {
                ApiState.Success(catResponse.body()!!)
            }
        } else {
            ApiState.Failure("Error fetching data")
        }

        emit(state)
    }
}