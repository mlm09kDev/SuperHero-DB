package com.mlm09kdev.superHeroDB.model.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "superHero", indices = [Index(value = ["id"], unique = true)])
data class SuperHeroEntity(

    @PrimaryKey(autoGenerate = false)
    val id: String,
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
    val image: Image
)