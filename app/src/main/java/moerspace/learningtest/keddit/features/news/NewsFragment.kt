package moerspace.learningtest.keddit.features.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.news_fragment.*
import moerspace.learningtest.keddit.R
import moerspace.learningtest.keddit.commons.InfiniteScrollListener
import moerspace.learningtest.keddit.commons.RedditNews
import moerspace.learningtest.keddit.commons.RxBaseFragment
import moerspace.learningtest.keddit.commons.extentions.inflate
import moerspace.learningtest.keddit.features.news.adapter.NewsAdapter

class NewsFragment : RxBaseFragment() {
    private var redditNews: RedditNews? = null
    private val newsManager by lazy { NewsManager() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = container?.inflate(R.layout.news_fragment)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        newsList.apply {
            setHasFixedSize(true)
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({ requestNews() }, linearLayoutManager))
        }

        initAdapter()

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_REDDIT_NEWS)) {
            redditNews = savedInstanceState.getParcelable(KEY_REDDIT_NEWS)
            redditNews?.news?.let { (newsList.adapter as NewsAdapter).clearAndAddNews(it) }
        } else {
            requestNews()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val redditNewsItems = (newsList.adapter as NewsAdapter).getNews()
        if (redditNews != null && redditNewsItems.isNotEmpty()) {
            outState.putParcelable(KEY_REDDIT_NEWS, redditNews?.copy(news = redditNewsItems))
        }
    }

    private fun requestNews() {
        /**
         * first time will send empty string for after parameter.
         * Next time we will have redditNews set with the next page to
         * navigate with the after param.
         */
        val disposable = newsManager.getNews(redditNews?.after ?: "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { retrievedNews ->
                    redditNews = retrievedNews
                    (newsList.adapter as NewsAdapter).addNews(retrievedNews.news)
                },
                onError = { t ->
                    Snackbar.make(newsList, t.message ?: "", Snackbar.LENGTH_LONG).show()
                }
            )

        disposables.add(disposable)
    }

    private fun initAdapter() {
        if (newsList.adapter == null) {
            newsList.adapter = NewsAdapter()
        }
    }

    companion object {
        private const val KEY_REDDIT_NEWS = "redditNews"
    }
}