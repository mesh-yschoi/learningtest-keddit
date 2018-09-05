package moerspace.learningtest.keddit.features.news

import io.reactivex.Single
import moerspace.learningtest.keddit.commons.RedditNewsItem

class NewsManager {

    fun getNews(): Single<List<RedditNewsItem>> {
        return Single.create { emitter ->
            val news = (1..10).map { RedditNewsItem(
                    "author$it",
                    "Title $it",
                    it,
                    1457207701L - it * 200,
                    "https://picsum.photos/200/200?image=$it",
                    "url"
            ) }

            emitter.onSuccess(news)
        }
    }
}