package com.nadhif.hayazee.home.story_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nadhif.hayazee.common.extension.loadImage
import com.nadhif.hayazee.common.util.DateUtil
import com.nadhif.hayazee.home.databinding.StoryItemLayoutBinding
import com.nadhif.hayazee.model.common.Story

class StoryPagingAdapter : PagingDataAdapter<Story, StoryPagingAdapter.ViewHolder>(diffUtil) {

    var storySelectedListener: StorySelectedListener? = null

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

        }
    }


    inner class ViewHolder(private val binding: StoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(data: Story?, position: Int) {
            binding.apply {
                tvUserName.text = data?.name
                tvDescription.text = data?.description
                tvCreatedDate.text = DateUtil.convertDateBasedOnLocale(
                    data?.createdAt ?: "",
                    DateUtil.TIME_FORMAT_WITH_TZ,
                    DateUtil.EEEE_dd_MMM_yyyy_HH_mm,
                    binding.root.context
                )
                ivStoryPhoto.apply {
                    transitionName = data?.photoUrl
                    loadImage(data?.photoUrl)
                }


                root.setOnClickListener {
                    data?.let { it1 -> storySelectedListener?.onStorySelected(it1, ivStoryPhoto) }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position), position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            StoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    interface StorySelectedListener {
        fun onStorySelected(story: Story, imageView: ImageView)
    }

}