package com.nadhif.hayazee.model.response

import android.os.Parcelable
import com.nadhif.hayazee.model.BaseServiceResponse
import com.nadhif.hayazee.model.common.Story
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetStoryResponse(
    override val error: Boolean?,
    override val message: String?,
    val listStory: List<Story>?
) : Parcelable, BaseServiceResponse

@Parcelize
data class AddNewStoryResponse(override val error: Boolean?, override val message: String?) :
    Parcelable, BaseServiceResponse
