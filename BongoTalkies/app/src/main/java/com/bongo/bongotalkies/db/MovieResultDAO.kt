package com.bongo.bongotalkies.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bongo.bongotalkies.models.Movie
import com.bongo.bongotalkies.models.Result

@Dao
interface MovieResultDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateMovie(result: Result): Long

    @Query("Select * from movie")
    fun getAllSavedMovies(): LiveData<List<Result>>

    @Delete
    suspend fun delete(result: Result)
}