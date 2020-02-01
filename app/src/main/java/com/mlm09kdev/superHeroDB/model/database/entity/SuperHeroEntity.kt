package com.mlm09kdev.superHeroDB.model.database.entity

import androidx.annotation.Nullable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mlm09kdev.superHeroDB.model.database.entity.*

@Entity(tableName = "superHero")
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