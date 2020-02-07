package com.mlm09kdev.superHeroDB.model.database.entity


data class Work(
    val base: String,
    val occupation: String
){
    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass)
            return false

        other as Work
        if(base != other.base)
            return false

        return true
    }
}