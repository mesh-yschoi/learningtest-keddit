package moerspace.learningtest.keddit.commons

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import moerspace.learningtest.keddit.commons.adapter.AdapterConstants
import moerspace.learningtest.keddit.commons.adapter.ViewType

@Parcelize
data class RedditNews(
    val after: String,
    val before: String,
    val news: List<RedditNewsItem>
) : Parcelable

@Parcelize
data class RedditNewsItem(
    val author: String,
    val title: String,
    val numComments: Int,
    val created: Long,
    val thumbnail: String,
    val url: String
) : ViewType, Parcelable {

    override fun getViewType(): Int = AdapterConstants.NEWS

}