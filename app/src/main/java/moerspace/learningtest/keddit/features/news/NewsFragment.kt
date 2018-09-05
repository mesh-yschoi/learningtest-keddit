package moerspace.learningtest.keddit.features.news

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.news_fragment.*
import moerspace.learningtest.keddit.R
import moerspace.learningtest.keddit.commons.RxBaseFragment
import moerspace.learningtest.keddit.commons.extentions.inflate
import moerspace.learningtest.keddit.features.news.adapter.NewsAdapter

class NewsFragment : RxBaseFragment() {
    private val newsManager by lazy { NewsManager() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.news_fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsList.setHasFixedSize(true)
        newsList.layoutManager = LinearLayoutManager(context)

        initAdapter()

        if (savedInstanceState == null) {
            requestNews()
        }
    }

    private fun requestNews() {
        val disposable = newsManager.getNews()
                .subscribeOn(Schedulers.io())
                .doOnSuccess {
                    (newsList.adapter as NewsAdapter).addNews(it)
                }
                .doOnError { t ->
                    Snackbar.make(newsList, t.message ?: "", Snackbar.LENGTH_LONG).show()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

        disposables.add(disposable)
    }

    private fun initAdapter() {
        if (newsList.adapter == null) {
            newsList.adapter = NewsAdapter()
        }
    }
}