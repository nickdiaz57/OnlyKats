package com.example.onlykats.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cat(
    val breeds: List<Breed>,
    val categories: List<Category>?,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)