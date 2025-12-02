package com.example.twitchstreamer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.twitchstreamer.data.local.entity.ViewerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ViewerDao {

    @Query("SELECT * FROM viewers ORDER BY title, first, last")
    fun observeViewers(): Flow<List<ViewerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertViewers(viewers: List<ViewerEntity>)

    @Query("DELETE FROM viewers")
    suspend fun deleteAll()

    @Transaction
    suspend fun replaceAll(viewers: List<ViewerEntity>) {
        deleteAll()
        insertViewers(viewers)
    }
}
