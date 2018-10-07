package moerspace.learningtest.keddit

import io.reactivex.observers.TestObserver
import moerspace.learningtest.keddit.api.*
import moerspace.learningtest.keddit.commons.RedditNews
import moerspace.learningtest.keddit.features.news.NewsManager
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import retrofit2.Call
import retrofit2.Response
import java.util.*

class NewsManagerTest {
    private var testObserver = TestObserver<RedditNews>()
    private var apiMock = mock<NewsAPI>()
    private var callMock = mock<Call<RedditNewsResponse>>()

    @Before
    fun setUp() {
        testObserver = TestObserver()
        apiMock = mock()
        callMock = mock()
        `when`(apiMock.getNews(anyString(), anyString())).thenReturn(callMock)
    }

    @Test
    fun success_basic() {
        // prepare
        val redditNewsResponse = RedditNewsResponse(RedditDataResponse(listOf(), null, null))
        val response = Response.success(redditNewsResponse)

        `when`(callMock.execute()).thenReturn(response)

        // call
        val newsManager = NewsManager(apiMock)
        newsManager.getNews("").subscribe(testObserver)

        // assert
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        testObserver.assertComplete()
    }

    @Test
    fun success_checkOneNews() {
        // prepare
        val newsData = RedditNewsDataResponse(
            "author",
            "title",
            10,
            Date().time,
            "thumbnail",
            "url"
        )
        val newsResponse = RedditChildrenResponse(newsData)
        val redditNewsResponse =
            RedditNewsResponse(RedditDataResponse(listOf(newsResponse), null, null))
        val response = Response.success(redditNewsResponse)

        `when`(callMock.execute()).thenReturn(response)

        // call
        val newsManager = NewsManager(apiMock)
        newsManager.getNews("").subscribe(testObserver)

        // assert
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        testObserver.assertComplete()
        assert(testObserver.values()[0].news[0].author == newsData.author)
        assert(testObserver.values()[0].news[0].title == newsData.title)
    }

    @Test
    fun error() {
        // prepare
        val responseError = Response.error<RedditNewsResponse>(
            500,
            ResponseBody.create(MediaType.parse("application/json"), "")
        )

        `when`(callMock.execute()).thenReturn(responseError)

        // call
        val newsManager = NewsManager(apiMock)
        newsManager.getNews("").subscribe(testObserver)

        // assert
        assert(testObserver.errorCount() == 1)
    }

    private inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)
}