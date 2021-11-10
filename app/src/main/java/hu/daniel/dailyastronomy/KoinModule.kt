package hu.daniel.dailyastronomy

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import hu.daniel.dailyastronomy.model.PreferencesRepository
import hu.daniel.dailyastronomy.model.SolarSystemRepository
import hu.daniel.dailyastronomy.model.SolarSystemRepositoryImpl
import hu.daniel.dailyastronomy.model.gallery.GalleryRepository
import hu.daniel.dailyastronomy.model.gallery.GalleryRepositoryImpl
import hu.daniel.dailyastronomy.model.gallery.GalleryService
import hu.daniel.dailyastronomy.model.news.NewsRepository
import hu.daniel.dailyastronomy.model.news.NewsRepositoryImpl
import hu.daniel.dailyastronomy.model.news.NewsService
import hu.daniel.dailyastronomy.presentation.main.MainViewModel
import hu.daniel.dailyastronomy.presentation.main.gallery.GalleryViewModel
import hu.daniel.dailyastronomy.presentation.main.live.LiveViewModel
import hu.daniel.dailyastronomy.presentation.main.news.NewsViewModel
import hu.daniel.dailyastronomy.presentation.main.settings.SettingsViewModel
import hu.daniel.dailyastronomy.presentation.main.solarsystem.SolarSystemViewModel
import hu.daniel.dailyastronomy.presentation.splash.SplashViewModel
import hu.daniel.dailyastronomy.util.NetworkObserver
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val PREFERENCES_KEY = "DailyAstronomy"

val KoinModule = module {
    single { PreferencesRepository(provideSharedPreferences(androidContext())) }
    single { NetworkObserver(androidContext()) }
    single { provideRetrofitClient(BuildConfig.NEWS_API_BASE_URL, NewsService::class.java) }
    single { provideRetrofitClient(BuildConfig.GALLERY_API_BASE_URL, GalleryService::class.java) }
    single<NewsRepository> { NewsRepositoryImpl(get(), get()) }
    single<GalleryRepository> { GalleryRepositoryImpl(get() ,get()) }
    single<SolarSystemRepository> { SolarSystemRepositoryImpl(androidContext()) }
    viewModel { NewsViewModel(get(), androidContext()) }
    viewModel { SplashViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { SettingsViewModel(androidContext(), get()) }
    viewModel { GalleryViewModel(get(), androidContext()) }
    viewModel { LiveViewModel() }
    viewModel { SolarSystemViewModel(get()) }
}

private fun <T>provideRetrofitClient(baseUrl: String, serviceClass: Class<T>): T {
    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(buildNewsOKHTTPClient())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build().create(serviceClass)
}

private fun provideSharedPreferences(context: Context) =
    context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)

private fun buildNewsOKHTTPClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addNetworkInterceptor(StethoInterceptor())
        .build()
}