package com.nadhif.hayazee.domain.story

import com.nadhif.hayazee.common.util.CameraUtil
import com.nadhif.hayazee.data.home.StoryRepository
import com.nadhif.hayazee.domain.BaseUseCase
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.response.RegisterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class PostStoryUseCase @Inject constructor(
    private val storyRepository: StoryRepository
) : BaseUseCase() {

    operator fun invoke(
        file: File,
        description: String
    ): Flow<ResponseState<RegisterResponse>> {
        return flow {
            emit(ResponseState.Loading())
            try {
                val compressedFile = CameraUtil.reduceFileImage(file)
                val requestImageFile = compressedFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val requestDescription = description.toRequestBody("text/plain".toMediaType())
                val imageMultipart: MultipartBody.Part =
                    MultipartBody.Part.createFormData("photo", file.name, requestImageFile)
                val response = storyRepository.postStory(imageMultipart, requestDescription)

                validateResponse(response,
                    onSuccess = {
                        emit(ResponseState.Success(it))
                    },
                    onError = {
                        emit(ResponseState.Error(it))
                    })

            } catch (e: Exception) {
                emit(ResponseState.Error(e.localizedMessage))
            }
        }
    }
}