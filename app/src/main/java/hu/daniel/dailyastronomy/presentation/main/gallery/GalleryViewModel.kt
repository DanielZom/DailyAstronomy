package hu.daniel.dailyastronomy.presentation.main.gallery

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.dto.GalleryImage
import hu.daniel.dailyastronomy.model.DataLoadCallback
import hu.daniel.dailyastronomy.model.gallery.GalleryRepository

class GalleryViewModel(
        private val galleryRepository: GalleryRepository,
        private val context: Context
) : ViewModel() {

    val galleryImages = MutableLiveData<ArrayList<GalleryImage?>>()
    val showLoader = MutableLiveData(false)
    val showError = MutableLiveData(false)
    val showList = MutableLiveData(false)
    val userMessage = MutableLiveData<String>()
    val userMessageImage = MutableLiveData<Int>()
    val imageToEnlarge = MutableLiveData<GalleryImage?>()

    private var pageNumber = 1
    private var hasMoreImages = true
    private val dataLoadCallback = object : DataLoadCallback {
        override fun onStart() {
            showLoader.value = true
        }

        @Suppress("UNCHECKED_CAST")
        override fun <R> onSuccess(response: R) {
            val requestedImages = response as ArrayList<GalleryImage?>
            hasMoreImages = !requestedImages.isNullOrEmpty()
            if (hasMoreImages) {
                showList.value = true
                showError.value = false

                galleryImages.value = arrayListOf<GalleryImage?>().apply {
                    addAll(requestedImages)
                    add(null)
                }
            }
        }

        override fun onError(error: Throwable) {
            showListError(false)
            showLoader.value = false
        }

        override fun onComplete() {
            showLoader.value = false
        }
    }

    private fun showListError(isEmpty: Boolean) {
        val messageId = if (isEmpty) R.string.empty_images_list else R.string.something_went_wrong
        val imageId = if (isEmpty) R.drawable.empty_list else R.drawable.something_went_wrong
        userMessage.value = context.getString(messageId)
        userMessageImage.value = imageId

        showError.value = true
        showList.value = false
    }

    fun requestMoreImages() {
        pageNumber++
        downloadGalleryImages()
    }

    fun initImages() {
        if (galleryRepository.images.isNotEmpty()) {
            showList.value = true
            galleryImages.value = galleryRepository.images
        } else {
            downloadGalleryImages()
        }
    }

    private fun downloadGalleryImages() {
        galleryRepository.downloadGalleryImages(pageNumber, dataLoadCallback)
    }

    fun enlargeImage(image: GalleryImage) {
        imageToEnlarge.value = image
    }

    fun hideEnlargedImage() {
        imageToEnlarge.value = null
    }
}