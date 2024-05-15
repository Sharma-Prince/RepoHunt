package com.adishakti.repohunt.dao

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "repoTable")
data class RepoTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fullName: String? = null,
    val name: String? = null,
    val description: String? = null,
    val avatarUrl: String? = null,
    val login: String? = null,
    val svnUrl: String? = null,
    val language: String? = null,
    val stargazersCount: Int = 0
)