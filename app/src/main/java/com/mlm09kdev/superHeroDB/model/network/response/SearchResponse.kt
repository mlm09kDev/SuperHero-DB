package com.mlm09kdev.superHeroDB.model.network.response


import com.google.gson.annotations.SerializedName
import com.mlm09kdev.superHeroDB.model.database.entity.SuperHeroEntity

data class SearchResponse(
    val response: String,
    val results: List<SuperHeroEntity>,
    @SerializedName("results-for")
    val resultsFor: String
)