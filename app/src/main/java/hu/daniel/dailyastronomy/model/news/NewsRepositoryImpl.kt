package hu.daniel.dailyastronomy.model.news

import androidx.lifecycle.MutableLiveData
import hu.daniel.dailyastronomy.BuildConfig
import hu.daniel.dailyastronomy.dto.Article
import hu.daniel.dailyastronomy.dto.HttpError
import hu.daniel.dailyastronomy.dto.NewsResponseData
import hu.daniel.dailyastronomy.model.DataLoadCallback
import hu.daniel.dailyastronomy.util.NetworkObserver
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import retrofit2.HttpException

interface NewsRepository {
    fun downloadNews(page: Int, startDate: String? = null, endDate: String? = null, dataLoadCallback: DataLoadCallback? = null)
    val initialNews: MutableLiveData<ArrayList<Article>>
    fun getHttpErrorBy(error: HttpException): HttpError
}

class NewsRepositoryImpl(
        private val service: NewsService,
        private val networkObserver: NetworkObserver
) : NewsRepository {

    override val initialNews = MutableLiveData<ArrayList<Article>>()

    private val pageSize = 20

    override fun downloadNews(page: Int, startDate: String?, endDate: String?, dataLoadCallback: DataLoadCallback?) {
        service.getNews(BuildConfig.NEWS_API_KEY, BuildConfig.NEWS_API_QUERY, "publishedAt", "en", startDate, endDate, pageSize, page)
                .doOnSubscribe { dataLoadCallback?.onStart() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(networkObserver.createRequestObserver<NewsResponseData>({ response ->
                    initNewsIfNeeded(response.articles ?: arrayListOf())
                    if (response.status == "ok") {
                        if (initialNews.value == null) initialNews.value = response.articles
                        dataLoadCallback?.onSuccess(response.articles)
                    } else {
                        dataLoadCallback?.onError(RuntimeException())
                    }
                }, { error ->
                    initNewsIfNeeded(arrayListOf())
                    dataLoadCallback?.onError(error)
                }, {
                    initNewsIfNeeded(arrayListOf())
                    dataLoadCallback?.onComplete()
                }))
    }

    override fun getHttpErrorBy(error: HttpException): HttpError {
        return when {
            error.code() == 426 -> HttpError.NEWS_LIMIT
            else -> HttpError.OTHER
        }
    }

    private fun initNewsIfNeeded(news: ArrayList<Article>) {
        if (initialNews.value == null) initialNews.value = news
    }
}