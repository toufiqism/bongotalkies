package com.bongo.bongotalkies.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "movie")
data class Result(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val adult: Boolean,
    val backdrop_path: String,
    @TypeConverters(Converters::class)
    val genre_ids: Array<Int>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)