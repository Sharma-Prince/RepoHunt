package com.adishakti.repohunt.dao

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [RepoTable::class],
    version = 1
)
abstract class RepoDataBase : RoomDatabase() {
    abstract fun getRepoDao(): RepoDataAccessObject
}
