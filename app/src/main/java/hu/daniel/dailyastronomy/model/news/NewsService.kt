package hu.daniel.dailyastronomy.model.news

import hu.daniel.dailyastronomy.dto.NewsResponseData
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("everything")
    fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("q") keywords: String,
        @Query("sortBy") sortBy: String,
        @Query("language") language: String,
        @Query("from") from: String?,
        @Query("to") to: String?,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
    ): Observable<NewsResponseData>
}