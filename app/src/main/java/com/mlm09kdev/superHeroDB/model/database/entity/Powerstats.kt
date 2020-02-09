package com.mlm09kdev.superHeroDB.model.database.entity


data class Powerstats(
    val power: String,
    val intelligence: String,
    val combat: String,
    val speed: String,
    val strength: String,
    val durability: String
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass)
            return false

        other as Powerstats
        if (combat != other.combat)
            return false
        if (durability != other.durability)
            return false
        if (intelligence != other.intelligence)
            return false
        if (power != other.power)
            return false
        if (speed != other.speed)
            return false
        if (strength != other.strength)
            return false

        return true
    }
}