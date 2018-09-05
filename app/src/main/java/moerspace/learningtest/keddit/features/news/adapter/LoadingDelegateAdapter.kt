package moerspace.learningtest.keddit.features.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import moerspace.learningtest.keddit.R
import moerspace.learningtest.keddit.commons.adapter.ViewType
import moerspace.learningtest.keddit.commons.adapter.ViewTypeDelegateAdapter
import moerspace.learningtest.keddit.commons.extentions.inflate

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = LoadingItemViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class LoadingItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.news_item_loading))
}