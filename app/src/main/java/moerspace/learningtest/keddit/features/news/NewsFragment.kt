package moerspace.learningtest.keddit.features.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.news_fragment.*
import moerspace.learningtest.keddit.R
import moerspace.learningtest.keddit.commons.RedditNewsItem
import moerspace.learningtest.keddit.commons.extentions.inflate
import moerspace.learningtest.keddit.features.news.adapter.NewsAdapter

class NewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.news_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsList.setHasFixedSize(true)
        newsList.layoutManager = LinearLayoutManager(context)

        initAdapter()

        if (savedInstanceState == null) {
            val news = (1..10).map { RedditNewsItem(
                    "author$it",
                    "Title $it",
                    it,
                    1457207701L - it * 200,
                    "https://picsum.photos/200/200?image=$it",
                    "url"
            ) }
            (newsList.adapter as NewsAdapter).addNews(news)
        }
    }

    private fun initAdapter() {
        if (newsList.adapter == null) {
            newsList.adapter = NewsAdapter()
        }
    }
}