package com.mlm09kdev.superHeroDB.model.database.entity


data class Image(
    val url: String
) {
    override fun equals(other: Any?): Boolean {

        if (javaClass != other?.javaClass)
            return false

        other as Image
        if (url != other.url)
            return false

        return true
    }
}