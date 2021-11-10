package hu.daniel.dailyastronomy.model.gallery

import hu.daniel.dailyastronomy.dto.GalleryImage
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GalleryService {
    @GET("apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Observable<GalleryImage>
}