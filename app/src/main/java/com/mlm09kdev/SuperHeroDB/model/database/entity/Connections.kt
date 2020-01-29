package com.mlm09kdev.SuperHeroDB.model.database.entity


import com.google.gson.annotations.SerializedName

data class Connections(
    @SerializedName("group-affiliation")
    val groupAffiliation: String,
    val relatives: String
)