package com.nadhif.hayazee.storyapp

import android.content.Intent
import android.widget.RemoteViewsService
import com.nadhif.hayazee.domain.story.GetStoriesUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StackWidgetService :
    RemoteViewsService() {

    @Inject
    lateinit var getStoriesUseCase: GetStoriesUseCase

    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory {
        return StackRemoteViewsFactory(
            applicationContext, getStoriesUseCase
        )
    }
}