package moerspace.learningtest.keddit.features.news

import io.reactivex.Single
import moerspace.learningtest.keddit.api.RestAPI
import moerspace.learningtest.keddit.commons.RedditNewsItem

class NewsManager(private val api: RestAPI = RestAPI()) {

    fun getNews(limit: String = "10"): Single<List<RedditNewsItem>> {
        return Single.create { emitter ->
            val callResponse = api.getNews("", limit)
            val response = callResponse.execute()
            val redditNewsResponse = response.body()
            if (response.isSuccessful && redditNewsResponse != null) {
                val news = redditNewsResponse.data.children.map {
                    val item = it.data
                    RedditNewsItem(
                        item.author,
                        item.title,
                        item.num_comments,
                        item.created,
                        item.thumbnail,
                        item.url
                    )
                }
                emitter.onSuccess(news)
            } else {
                emitter.onError(Throwable(response.message()))
            }
        }
    }
}