package com.example.plantmate.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.plantmate.data.local.entity.EncyclopediaEntity

@Dao
interface EncyclopediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEncyclopedia(encyclopedia: EncyclopediaEntity)

    @Query("SELECT * FROM encyclopedia ORDER BY id DESC")
    fun getAllPlants(): Flow<List<EncyclopediaEntity>>

    @Delete
    suspend fun deleteEncyclopedia(encyclopedia: EncyclopediaEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM encyclopedia WHERE commonName = :name)")
    fun isExist(name: String): Flow<Boolean>

}
