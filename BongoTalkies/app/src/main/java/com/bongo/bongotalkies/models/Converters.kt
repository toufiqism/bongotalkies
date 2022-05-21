package com.bongo.bongotalkies.models

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {

    @TypeConverter
    fun listToJson(value: Array<Int>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): Array<Int> {
        return Gson().fromJson(value, Array<Int>::class.java)
    }
}