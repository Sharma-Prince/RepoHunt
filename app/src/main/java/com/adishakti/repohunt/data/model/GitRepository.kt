package com.adishakti.repohunt.data.model

import android.os.Parcelable
import com.adishakti.repohunt.dao.RepoTable
import kotlinx.parcelize.Parcelize


@Parcelize
data class GitRepository(
    val incomplete_results: Boolean? = null,
    val items: List<RepoItem>? = null,
    val total_count: Int? = null,
) : Parcelable


@Parcelize
data class RepoItem(
    val full_name: String? = null,
    val name: String? = null,
    val description: String? = null,
    val owner: RepoOwner? = null,
    val svn_url: String? = null,
    val language: String? = null,
    val stargazers_count: Int = 0
) : Parcelable

@Parcelize
data class RepoOwner(
    val avatar_url: String? = null,
    val login: String? = null,
) : Parcelable


fun RepoItem.toCombinedRepo(): RepoTable {
    return RepoTable(
        fullName = this.full_name,
        name = this.name,
        description = this.description,
        avatarUrl = this.owner?.avatar_url,
        login = this.owner?.login,
        svnUrl = this.svn_url,
        language = this.language,
        stargazersCount = this.stargazers_count
    )
}

fun RepoTable.toRepoItem(): RepoItem {
    return RepoItem(
        full_name = this.fullName,
        name = this.name,
        description = this.description,
        owner = RepoOwner(
            avatar_url = this.avatarUrl,
            login = this.login
        ),
        svn_url = this.svnUrl,
        language = this.language,
        stargazers_count = this.stargazersCount
    )
}