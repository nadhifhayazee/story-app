package com.nadhif.hayazee.home.story_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.nadhif.hayazee.baseview.viewmodel.BaseVmFactory
import com.nadhif.hayazee.domain.auth.LogoutUseCase
import com.nadhif.hayazee.domain.home.GetStoriesUseCase
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.common.Story
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel(
    private val getStoriesUseCase: GetStoriesUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _storiesState =
        MutableStateFlow<ResponseState<PagingData<Story>>>(ResponseState.Loading())
    val storiesState get() = _storiesState.asSharedFlow()


    fun getStories() {
        viewModelScope.launch {
            _storiesState.value = ResponseState.Loading()
            getStoriesUseCase().collectLatest {
                _storiesState.value = ResponseState.Success(it)
            }
        }
    }

    fun logout() {
        logoutUseCase()
    }

    class Factory @Inject constructor(
        getStoriesUseCase: GetStoriesUseCase,
        logoutUseCase: LogoutUseCase
    ) : BaseVmFactory(
        HomeViewModel(getStoriesUseCase, logoutUseCase)
    )
}