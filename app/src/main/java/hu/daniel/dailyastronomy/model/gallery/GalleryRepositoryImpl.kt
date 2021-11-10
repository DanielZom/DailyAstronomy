package hu.daniel.dailyastronomy.model.gallery

import hu.daniel.dailyastronomy.BuildConfig
import hu.daniel.dailyastronomy.dto.GalleryImage
import hu.daniel.dailyastronomy.model.DataLoadCallback
import hu.daniel.dailyastronomy.util.NetworkObserver
import hu.daniel.dailyastronomy.util.extensions.formatToYearMonthDate
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.*

interface GalleryRepository {
    fun downloadGalleryImages(pageNumber: Int, dataLoadCallback: DataLoadCallback)
    val images: ArrayList<GalleryImage?>
}

class GalleryRepositoryImpl(
        private val service: GalleryService,
        private val networkObserver: NetworkObserver
) : GalleryRepository {

    private val actualRequestDate = Calendar.getInstance()
    private val pageSize = 20
    override val images: ArrayList<GalleryImage?> = arrayListOf()

    override fun downloadGalleryImages(pageNumber: Int, dataLoadCallback: DataLoadCallback) {
        service.getPictureOfTheDay(BuildConfig.GALLERY_API_KEY, actualRequestDate.time.formatToYearMonthDate())
                .doOnSubscribe { dataLoadCallback.onStart() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(networkObserver.createRequestObserver<GalleryImage>({
                    images.add(it)
                    actualRequestDate.set(Calendar.DAY_OF_YEAR, actualRequestDate.get(Calendar.DAY_OF_YEAR) - 1)
                    if (images.size != pageNumber * pageSize) downloadGalleryImages(pageNumber, dataLoadCallback) else dataLoadCallback.onSuccess(images)
                }, {
                    dataLoadCallback.onError(it)
                }, {
                    if (images.size == pageNumber * pageSize) dataLoadCallback.onComplete()
                }))
    }
}