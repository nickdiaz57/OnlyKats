package com.example.onlykats.repo.remote

import com.example.onlykats.model.Cat
import com.example.onlykats.util.Order
import retrofit2.Response
import retrofit2.http.*

interface CatService {

    @Headers("x-api-key: 53ee5abe-4056-4fcc-b486-3c2af788d781")
    @GET("v1/images/search")

    suspend fun getCatImages(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("has_breeds") has_breeds: Boolean,
        @Query("order") order: Order
    ):Response<List<Cat>>
}