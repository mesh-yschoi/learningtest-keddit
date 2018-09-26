package moerspace.learningtest.keddit.features.news

import io.reactivex.Single
import moerspace.learningtest.keddit.api.RestAPI
import moerspace.learningtest.keddit.commons.RedditNews
import moerspace.learningtest.keddit.commons.RedditNewsItem

class NewsManager(private val api: RestAPI = RestAPI()) {

    fun getNews(after: String, limit: String = "10"): Single<RedditNews> {
        return Single.create { emitter ->
            val callResponse = api.getNews(after, limit)
            val response = callResponse.execute()
            val redditNewsResponse = response.body()
            if (response.isSuccessful && redditNewsResponse != null) {
                val dataResponse = redditNewsResponse.data
                val news = dataResponse.children.map {
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
                val redditNews = RedditNews(
                    dataResponse.after ?: "",
                    dataResponse.before ?: "",
                    news)
                emitter.onSuccess(redditNews)
            } else {
                emitter.onError(Throwable(response.message()))
            }
        }
    }
}