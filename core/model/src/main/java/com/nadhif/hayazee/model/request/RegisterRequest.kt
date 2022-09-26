package com.nadhif.hayazee.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterRequest(
    val name: String,
    override val email: String, override val password: String
) : Parcelable, AuthRequest
