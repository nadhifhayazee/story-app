package com.nadhif.hayazee.domain.story

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nadhif.hayazee.data.home.StoryRepository
import com.nadhif.hayazee.domain.BaseUseCase
import com.nadhif.hayazee.model.common.Story
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoriesPagingUseCase @Inject constructor(
    private val storyRepository: StoryRepository
) : BaseUseCase() {

    private val maxPage = 5
    private val size = 20


    operator fun invoke(): Flow<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = size,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                BasePagingSource<Story>(
                    data = { page ->
                        val response = storyRepository.getStories(size, page, 1)
                        var result = listOf<Story>()
                        validateResponse(response,
                            onSuccess = {
                                result = it.listStory ?: listOf()
                            },
                            onError = {
                            }
                        )

                        result.toList()
                    },
                    maxPage
                )
            }
        ).flow
    }

}