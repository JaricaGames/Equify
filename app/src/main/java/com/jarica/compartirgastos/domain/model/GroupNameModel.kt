package com.jarica.compartirgastos.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class GroupNameModel (
    val idGroupName: Int = System.currentTimeMillis().hashCode(),
    val groupName: String
) : Parcelable
