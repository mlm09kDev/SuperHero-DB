package com.mlm09kdev.superHeroDB.model.database.entity

import androidx.room.*

@Entity(tableName = "superHero", indices = [Index(value = ["id"], unique = true)])
data class SuperHeroEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @Embedded(prefix = "powerstats_")
    val powerstats: Powerstats,
    @Embedded(prefix = "biography_")
    val biography: Biography,
    @Embedded(prefix = "appearance_")
    val appearance: Appearance,
    @Embedded(prefix = "work_")
    val work: Work,
    @Embedded(prefix = "connections_")
    val connections: Connections,
    @Embedded(prefix = "image_")
    val image: Image,
    var isFavorite: Int

) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass)
            return false

        other as SuperHeroEntity
        if (id != other.id)
            return false
        if (name != other.name)
            return false
        if (powerstats != other.powerstats)
            return false
        if (biography != other.biography)
            return false
        if (appearance != other.appearance)
            return false
        if (work != other.work)
            return false
        if (connections != other.connections)
            return false
        if (image != other.image)
            return false
        if(isFavorite != other.isFavorite)
            return false

        return true
    }
}