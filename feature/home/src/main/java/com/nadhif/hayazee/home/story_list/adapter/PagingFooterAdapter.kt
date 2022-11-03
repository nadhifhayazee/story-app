package com.nadhif.hayazee.home.story_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nadhif.hayazee.home.databinding.PagerFooterLayoutBinding

class PagingFooterAdapter(private val onRetryClicked: () -> Unit = {}) :
    LoadStateAdapter<PagingFooterAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: PagerFooterLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState is LoadState.Error
                btnRetry.setOnClickListener {
                    onRetryClicked.invoke()
                }
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bindView(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding =
            PagerFooterLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
}