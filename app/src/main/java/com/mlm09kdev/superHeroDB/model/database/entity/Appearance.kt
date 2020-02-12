package com.mlm09kdev.superHeroDB.model.database.entity


import com.google.gson.annotations.SerializedName

data class Appearance(
    @SerializedName("eye-color")
    val eyeColor: String,
    val gender: String,
    @SerializedName("hair-color")
    val hairColor: String,
    val race: String,
    val height: List<String>,
    val weight: List<String>
)
{
    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass)
            return false

        other as Appearance
        if(eyeColor != other.eyeColor)
            return false
        if(gender != other.gender)
            return false
        if(hairColor != other.hairColor)
            return false
        if(height != other.height)
            return false
        if(weight != other.weight)
            return false

        return true
    }
}