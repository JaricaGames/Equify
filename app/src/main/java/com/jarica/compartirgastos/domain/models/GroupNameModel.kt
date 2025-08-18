package com.jarica.compartirgastos.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class GroupNameModel (
    val idGroupName: Int = System.currentTimeMillis().hashCode(),
    var groupName: String
) : Parcelable
