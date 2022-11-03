package com.nadhif.hayazee.story

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhif.hayazee.baseview.viewmodel.BaseVmFactory
import com.nadhif.hayazee.domain.story.PostStoryUseCase
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.response.RegisterResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class AddNewStoryViewModel(
    private val postStoryUseCase: PostStoryUseCase
) : ViewModel() {


    private val _postStoryState = MutableSharedFlow<ResponseState<RegisterResponse>>()
    val postStoryState get() = _postStoryState.asSharedFlow()


    fun postStory(
        file: File?,
        description: String,
        lat: Double?,
        lon: Double?,
    ) {
        viewModelScope.launch {
            if (file == null) _postStoryState.emit(ResponseState.Error("File not found!"))
            else {
                postStoryUseCase(file, description, lat, lon).collectLatest {
                    _postStoryState.emit(it)
                }
            }
        }
    }

    class Factory @Inject constructor(
        postStoryUseCase: PostStoryUseCase
    ) : BaseVmFactory(
        AddNewStoryViewModel(postStoryUseCase)
    )
}