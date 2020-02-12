package com.mlm09kdev.superHeroDB.model.database.entity.converters

import androidx.room.TypeConverter
import java.io.Serializable

class StringListConverter: Serializable {
    @TypeConverter
    fun toList(strings: String): List<String> {
        val list = mutableListOf<String>()
        val array = strings.split(",")
        for (s in array) {
            list.add(s)
        }
        return list
    }

    @TypeConverter
    fun toString(strings: List<String>): String {
        var result = ""
        strings.forEachIndexed { index, element ->
            result += element
            if (index != (strings.size - 1)) {
                result += ","
            }
        }
        return result
    }
}