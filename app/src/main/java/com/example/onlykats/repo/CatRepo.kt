package com.example.onlykats.repo

import android.util.Log
import com.example.onlykats.repo.remote.RetrofitInstance
import com.example.onlykats.util.ApiState
import kotlinx.coroutines.flow.flow

object CatRepo {

    private val catService by lazy { RetrofitInstance.catService }
    const val NO_DATA_FOUND = "No data found"

    fun getCatState(limit: Int?, page: Int?, hasBreeds: Boolean?, order: String?, fileType: String?) = flow {
        emit(ApiState.Loading)

        val queryMap = listOfNotNull(
            limit?.let { "limit" to it },
            page?.let { "page" to it },
            hasBreeds?.let { "has_breeds" to it },
            order?.let { "order" to it },
            fileType?.let{ "mime_types" to it }
        ).toMap()

        Log.e("Cat Repo", "getCatState called. map is $queryMap")

        val catResponse = catService.getCatImages(queryMap)

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