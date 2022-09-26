package com.nadhif.hayazee.model.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userId: String?,
    val name: String?,
    val token: String?
) : Parcelable
