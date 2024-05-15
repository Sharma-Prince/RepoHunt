package com.adishakti.repohunt.data.repository


import androidx.lifecycle.LiveData
import com.adishakti.repohunt.dao.RepoTable


interface RepoDaoRepository {

    fun removeItemFromRepo(repoTable: RepoTable)

    fun getRepoTableSize(): Int

    fun getAllItem(): LiveData<List<RepoTable>>

    suspend fun deleteAllItems()

    suspend fun upsetRepo(repoTable: RepoTable): Long

    suspend fun insertAllItems(repoTables: List<RepoTable>)
}