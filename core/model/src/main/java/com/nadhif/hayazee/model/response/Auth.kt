package com.nadhif.hayazee.model.response

import android.os.Parcelable
import com.nadhif.hayazee.model.BaseServiceResponse
import com.nadhif.hayazee.model.common.User
import kotlinx.parcelize.Parcelize

@Parcelize
class LoginResponse(
    val loginResult: User?, override val error: Boolean?, override val message: String?
) : Parcelable, BaseServiceResponse


@Parcelize
class RegisterResponse(
    override val error: Boolean?, override val message: String?
) : Parcelable, BaseServiceResponse

