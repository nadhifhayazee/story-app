package com.nadhif.hayazee.storyapp

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.nadhif.hayazee.common.util.ImageUtil
import com.nadhif.hayazee.domain.story.GetStoriesWidgetUseCase
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.common.Story
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking

class StackRemoteViewsFactory(
    private val mContext: Context,
    private val getStoriesWidgetUseCase: GetStoriesWidgetUseCase
) :
    RemoteViewsService.RemoteViewsFactory {

    private var stories = mutableListOf<Story>()

    override fun onCreate() {
        stories = getStoriesFromRemote()
    }

    override fun onDataSetChanged() {
        stories = getStoriesFromRemote()

    }

    private fun getStoriesFromRemote(): MutableList<Story> {
        return runBlocking {
            val responseState = getStoriesWidgetUseCase().last()
            if (responseState is ResponseState.Success) {
                responseState.data?.toMutableList() ?: mutableListOf()
            } else {
                mutableListOf()
            }
        }
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int {
        return stories.size
    }

    override fun getViewAt(p0: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        try {
            val bitmap = ImageUtil.getBitmapFromUrl(stories[p0].photoUrl, mContext)
            rv.setImageViewBitmap(R.id.imageView, bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val fillInIntent = Intent()
        fillInIntent.putExtra(StoryStackWidget.EXTRA_ITEM_NAME, stories[p0].name)
        fillInIntent.putExtra(StoryStackWidget.EXTRA_ITEM_DESC, stories[p0].description)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? {
        return null

    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }
}