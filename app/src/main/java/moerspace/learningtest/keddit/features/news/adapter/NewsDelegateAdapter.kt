package moerspace.learningtest.keddit.features.news.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.news_item.view.*
import moerspace.learningtest.keddit.R
import moerspace.learningtest.keddit.commons.RedditNewsItem
import moerspace.learningtest.keddit.commons.adapter.ViewType
import moerspace.learningtest.keddit.commons.adapter.ViewTypeDelegateAdapter
import moerspace.learningtest.keddit.commons.extentions.getFriendlyTime
import moerspace.learningtest.keddit.commons.extentions.inflate
import moerspace.learningtest.keddit.commons.extentions.loadImg

class NewsDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = NewsItemViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as NewsItemViewHolder
        holder.bind(item as RedditNewsItem)
    }

    private class NewsItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.news_item)) {

        fun bind(item: RedditNewsItem) = with(itemView) {
            imgThumbnail.loadImg(item.thumbnail)
            description.text = item.title
            author.text = item.author
            comments.text = "${item.numComments} comments"
            time.text = item.created.getFriendlyTime()
        }
    }
}