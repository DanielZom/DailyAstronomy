package hu.daniel.dailyastronomy.presentation.main.news

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.dto.Article
import hu.daniel.dailyastronomy.dto.HttpError
import hu.daniel.dailyastronomy.dto.ListLoaderPosition
import hu.daniel.dailyastronomy.model.DataLoadCallback
import hu.daniel.dailyastronomy.model.news.NewsRepository
import org.koin.core.KoinComponent
import retrofit2.HttpException

class NewsViewModel(
        private val newsRepository: NewsRepository,
        private val context: Context
) : ViewModel(), KoinComponent {

    val isNewsDetailOpened: Boolean
        get() = selectedArticle.value != null
    val showLoader = MutableLiveData<Pair<ListLoaderPosition, Boolean>>()
    val showError = MutableLiveData(false)
    val showList = MutableLiveData(false)
    val news = MutableLiveData<ArrayList<Article>>()
    val userMessage = MutableLiveData<String>()
    val userMessageImage = MutableLiveData<Int>()
    val resetListPosition = MutableLiveData<Boolean>()
    val newsLimitReached = MutableLiveData(false)
    val selectedArticle = MutableLiveData<Article?>()

    private var pageNumber = 1
    private var hasMoreNews = true
    private var listLoaderPosition = ListLoaderPosition.TOP
    private val isNewsReseted: Boolean
        get() = pageNumber == 1
    private val newsObserver by lazy {
        object : Observer<ArrayList<Article>> {
            override fun onChanged(news: ArrayList<Article>?) {
                if (!news.isNullOrEmpty()) {
                    showList.value = true
                    showError.value = false
                    this@NewsViewModel.news.value = news
                } else {
                    showListError(true)
                }
                showLoader.value = Pair(ListLoaderPosition.TOP, false)
                newsRepository.initialNews.removeObserver(this)
            }
        }
    }

    fun screenLoaded() {
        showLoader.value = Pair(ListLoaderPosition.TOP, true)
        newsRepository.initialNews.observeForever(newsObserver)
    }

    fun resetNews(listLoaderPosition: ListLoaderPosition, startDate: String? = null, endDate: String? = null) {
        this.listLoaderPosition = listLoaderPosition
        pageNumber = 1
        downloadNews(pageNumber, startDate, endDate)
    }

    fun requestMoreNews(listLoaderPosition: ListLoaderPosition, startDate: String? = null, endDate: String? = null) {
        this.listLoaderPosition = listLoaderPosition
        if (!hasMoreNews) return
        pageNumber++
        downloadNews(pageNumber, startDate, endDate)
    }

    private fun downloadNews(pageNumber: Int, startDate: String? = null, endDate: String? = null) {
        newsRepository.downloadNews(pageNumber, startDate, endDate, dataLoadCallback)
    }

    private val dataLoadCallback = object : DataLoadCallback {
        override fun onStart() {
            showLoader.value = Pair(listLoaderPosition, true)
        }

        @Suppress("UNCHECKED_CAST")
        override fun <R> onSuccess(response: R) {
            val requestedNews = response as ArrayList<Article>
            if (!requestedNews.isNullOrEmpty()) {
                showList.value = true
                showError.value = false

                if (isNewsReseted) {
                    news.value = arrayListOf()
                    resetListPosition.value = true
                }

                val allNews = news.value ?: arrayListOf()
                allNews.addAll(requestedNews)
                news.value = allNews
                hasMoreNews = true
            } else {
                if (isNewsReseted) {
                    news.value = arrayListOf()
                    showListError(true)
                }
                hasMoreNews = false
            }
        }

        override fun onError(error: Throwable) {
            if (error is HttpException && newsRepository.getHttpErrorBy(error) == HttpError.NEWS_LIMIT) {
                newsLimitReached.value = true
                hasMoreNews = false
            } else {
                showListError(false)
                resetListPosition.value = true
            }
            showLoader.value = Pair(listLoaderPosition, false)
        }

        override fun onComplete() {
            showLoader.value = Pair(listLoaderPosition, false)
        }
    }

    fun articleClicked(article: Article) {
        selectedArticle.value = article
    }

    fun articleBackClicked() {
        selectedArticle.value = null
    }

    private fun showListError(isEmpty: Boolean) {
        val messageId = if (isEmpty) R.string.empty_news_list else R.string.something_went_wrong
        val imageId = if (isEmpty) R.drawable.empty_list else R.drawable.something_went_wrong
        userMessage.value = context.getString(messageId)
        userMessageImage.value = imageId

        showError.value = true
        showList.value = false
    }

    override fun onCleared() {
        newsRepository.initialNews.removeObserver(newsObserver)
        super.onCleared()
    }

    fun backClicked() {
        selectedArticle.value = null
    }
}