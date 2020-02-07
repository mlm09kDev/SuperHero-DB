package com.mlm09kdev.superHeroDB.model.database.entity


data class Powerstats(
    val combat: String,
    val durability: String,
    val intelligence: String,
    val power: String,
    val speed: String,
    val strength: String
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