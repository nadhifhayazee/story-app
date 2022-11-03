package com.nadhif.hayazee.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhif.hayazee.baseview.viewmodel.BaseVmFactory
import com.nadhif.hayazee.domain.story.GetStoriesUseCase
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.common.Story
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoryMapsViewModel(private val getStoriesUseCase: GetStoriesUseCase) : ViewModel() {

    private val _state = MutableStateFlow<ResponseState<List<Story>>>(ResponseState.Loading())

    val state get() = _state.asStateFlow()

    fun getStories() {
        viewModelScope.launch {
            getStoriesUseCase(null, null, 1).collectLatest {
                _state.value = it
            }
        }
    }


    class Factory @Inject constructor(
        getStoriesUseCase: GetStoriesUseCase
    ) : BaseVmFactory(StoryMapsViewModel(getStoriesUseCase))
}