package com.jarica.compartirgastos.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID


@Parcelize
data class GroupModel (
    val idGroupName:String = UUID.randomUUID().toString(),
    var groupName: String
) : Parcelable
