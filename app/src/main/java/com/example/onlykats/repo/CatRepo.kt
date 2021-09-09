package com.example.onlykats.repo
import android.util.Log
import com.example.onlykats.repo.remote.RetrofitInstance
import com.example.onlykats.util.ApiState
import com.example.onlykats.util.Order
import kotlinx.coroutines.flow.flow

object CatRepo {

    private val catService by lazy { RetrofitInstance.catService }
    const val NO_DATA_FOUND = "No data found"

    fun getCatState(limit: Int, page: Int = 1, breeds: Boolean = false, order: Order = Order.RANDOM) = flow {
        emit(ApiState.Loading)

        val catResponse = catService.getCatImages(limit, page, breeds, order)

        val state = if (catResponse.isSuccessful) {
            if (catResponse.body().isNullOrEmpty()) {
                Log.e("CatRepo", "No Data")
                ApiState.Failure(NO_DATA_FOUND)
            } else {
                Log.e("CatRepo", "Successfully fetched $limit cats")
                ApiState.Success(catResponse.body()!!)
            }
        } else {
            Log.e("CatRepo", "Error fetching data")
            ApiState.Failure("Error fetching data")
        }

        emit(state)
    }
}