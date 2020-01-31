package com.mlm09kdev.superHeroDB.model.database.entity


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "biography")
data class Biography(
   // @Embedded(prefix ="aliases_" )
   // val aliases: List<String>,
    val alignment: String,
    @SerializedName("alter-egos")
    val alterEgos: String,
    @SerializedName("first-appearance")
    val firstAppearance: String,
    @SerializedName("full-name")
    val fullName: String,
    @SerializedName("place-of-birth")
    val placeOfBirth: String,
    val publisher: String
)