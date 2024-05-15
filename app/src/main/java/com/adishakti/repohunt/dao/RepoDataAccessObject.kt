package com.adishakti.repohunt.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepoDataAccessObject {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(repo: RepoTable): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllItems(repoTables: List<RepoTable>)

    @Query("SELECT * FROM repoTable")
    fun getAllItem(): LiveData<List<RepoTable>>

    @Delete
    fun delete(repo: RepoTable)

    @Query("DELETE FROM repoTable")
    suspend fun deleteAllItem()

    @Query("SELECT COUNT(*) FROM repoTable")
    fun getRepoTableSize(): Int
}