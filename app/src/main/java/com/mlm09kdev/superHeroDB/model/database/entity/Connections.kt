package com.mlm09kdev.superHeroDB.model.database.entity


import com.google.gson.annotations.SerializedName

data class Connections(
    @SerializedName("group-affiliation")
    val groupAffiliation: String,
    val relatives: String
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass)
            return false

        other as Connections
        if (groupAffiliation != other.groupAffiliation)
            return false
        if (relatives != other.relatives)
            return false

        return true
    }
}