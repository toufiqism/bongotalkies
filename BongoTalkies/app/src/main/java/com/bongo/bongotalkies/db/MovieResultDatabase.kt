package com.bongo.bongotalkies.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bongo.bongotalkies.models.Converters
import com.bongo.bongotalkies.models.Result

@Database(entities = [Result::class], version = 1)
@TypeConverters(Converters::class)
abstract class MovieResultDatabase : RoomDatabase() {
    abstract fun getMovieResultDao(): MovieResultDAO

    companion object {
        @Volatile
        private var instance: MovieResultDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MovieResultDatabase::class.java,
            "movie_result_db.db"
        ).build()
    }
}