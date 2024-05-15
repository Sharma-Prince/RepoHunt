package com.adishakti.repohunt.data.repository

import androidx.lifecycle.LiveData
import com.adishakti.repohunt.dao.RepoDataAccessObject
import com.adishakti.repohunt.dao.RepoTable
import javax.inject.Inject


class RepoDaoRepositoryImpl @Inject constructor(private val dao: RepoDataAccessObject) :
    RepoDaoRepository {
    override fun removeItemFromRepo(repoTable: RepoTable) {
        return dao.delete(repoTable)
    }

    override fun getRepoTableSize(): Int {
        return dao.getRepoTableSize()
    }

    override fun getAllItem(): LiveData<List<RepoTable>> {
        return dao.getAllItem()
    }

    override suspend fun deleteAllItems() {
        return dao.deleteAllItem()
    }

    override suspend fun upsetRepo(repoTable: RepoTable): Long {
        return dao.upsert(repoTable)
    }

    override suspend fun insertAllItems(repoTables: List<RepoTable>) {
        return dao.insertAllItems(repoTables)
    }
}

