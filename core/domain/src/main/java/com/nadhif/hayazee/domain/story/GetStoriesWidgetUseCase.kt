package com.nadhif.hayazee.domain.story

import com.nadhif.hayazee.data.home.StoryRepository
import com.nadhif.hayazee.domain.BaseUseCase
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.common.Story
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStoriesWidgetUseCase @Inject constructor(
    private val storyRepository: StoryRepository
) : BaseUseCase() {

    operator fun invoke(): Flow<ResponseState<List<Story>>> {

        return flow {
            try {
                emit(ResponseState.Loading())
                val response = storyRepository.getStories(10, 1, null)
                validateResponse(response,
                    onSuccess = {
                        if (it.listStory.isNullOrEmpty()) {
                            emit(ResponseState.Success(listOf()))
                        } else {
                            emit(ResponseState.Success(it.listStory))
                        }
                    }, onError = {
                        emit(ResponseState.Error(it))
                    })
            } catch (e: Exception) {
                emit(ResponseState.Error(e.localizedMessage))
            }
        }
    }
}