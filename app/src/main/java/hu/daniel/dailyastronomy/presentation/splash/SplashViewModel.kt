package hu.daniel.dailyastronomy.presentation.splash

import androidx.lifecycle.ViewModel
import hu.daniel.dailyastronomy.model.news.NewsRepository
import org.koin.core.KoinComponent

class SplashViewModel(
    private val newsRepository: NewsRepository
) : ViewModel(), KoinComponent {

    fun downloadNews() {
        newsRepository.downloadNews(1)
    }
}