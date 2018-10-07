package moerspace.learningtest.keddit

import io.reactivex.observers.TestObserver
import moerspace.learningtest.keddit.api.*
import moerspace.learningtest.keddit.commons.RedditNews
import moerspace.learningtest.keddit.features.news.NewsManager
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.spekframework.spek2.Spek
import retrofit2.Call
import retrofit2.Response
import java.util.*

inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)

/**
 * Spek Framework 플러그인 설치해야됨.
 */
object NewsManagerSpekTest: Spek({
    beforeGroup {
        println("beforeGroup")
    }

    group("a NewsManager") {
        lateinit var testObserver: TestObserver<RedditNews>
        lateinit var apiMock: NewsAPI
        lateinit var callMock: Call<RedditNewsResponse>

        beforeEachTest {
            println("beforeEachTest")
            testObserver = TestObserver()
            apiMock = mock()
            callMock = mock()
            `when`(apiMock.getNews(anyString(), anyString())).thenReturn(callMock)
        }

        test("success basic") {
            println("test basic")

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

        test("success checkOneNews") {
            println("test checkOneNews")

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

        afterEachTest {
            println("afterEachTest")
        }
    }

    afterGroup {
        println("afterGroup")
    }
})