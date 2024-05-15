package com.adishakti.repohunt.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class RepositoryDetails(
    val contributions: List<Contributor>? = emptyList()
) : Parcelable

@Parcelize
data class Contributor(
    var login: String? = null,
    val avatar_url: String? = null
) : Parcelable