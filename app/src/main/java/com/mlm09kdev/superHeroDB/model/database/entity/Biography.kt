package com.mlm09kdev.superHeroDB.model.database.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.mlm09kdev.superHeroDB.model.database.entity.converters.StringListConverter

@Entity(tableName = "biography")
data class Biography(
    val aliases: List<String>,
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
{
    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass)
            return false

        other as Biography
        if(alignment != other.alignment)
            return false
        if(alterEgos != other.alterEgos)
            return false
        if(firstAppearance != other.firstAppearance)
            return false
        if(fullName != other.fullName)
            return false
        if(placeOfBirth != other.placeOfBirth)
            return false
        if(publisher != other.publisher)
            return false
       if(aliases != other.aliases)
            return false

        return true
    }
}