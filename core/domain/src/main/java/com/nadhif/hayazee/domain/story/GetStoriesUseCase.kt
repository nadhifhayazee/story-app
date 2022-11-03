package com.nadhif.hayazee.domain.story

import com.nadhif.hayazee.data.home.StoryRepository
import com.nadhif.hayazee.domain.BaseUseCase
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.common.Story
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(
    private val storyRepository: StoryRepository
) : BaseUseCase() {

    operator fun invoke(size: Int?, page: Int?, location: Int? = 0): Flow<ResponseState<List<Story>>> {

        return flow {
            try {
                emit(ResponseState.Loading())
                val response = storyRepository.getStories(size, page, location)
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