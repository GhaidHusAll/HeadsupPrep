package com.example.headsupprep.roomModel
import androidx.room.*

@Dao
interface CelebritiesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCele(cele: CelebritiesRoom)

    @Query("Select * from celebrities")
    fun getCele(): List<CelebritiesRoom>

    @Update
    suspend fun updateCele(cele:CelebritiesRoom)

    @Delete
    suspend fun deleteCele(cele:CelebritiesRoom)
}