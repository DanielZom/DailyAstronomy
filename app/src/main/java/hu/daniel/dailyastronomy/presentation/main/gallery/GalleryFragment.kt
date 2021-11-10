package hu.daniel.dailyastronomy.presentation.main.gallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import coil.api.load
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.databinding.FragmentGalleryBinding
import hu.daniel.dailyastronomy.presentation.main.BaseFragment
import hu.daniel.dailyastronomy.util.blurBitmap
import hu.daniel.dailyastronomy.util.extensions.isVisible
import hu.daniel.dailyastronomy.util.extensions.takeScreenshot
import hu.daniel.dailyastronomy.util.onDownload
import hu.daniel.dailyastronomy.util.visibleIf
import kotlinx.android.synthetic.main.fragment_gallery.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class GalleryFragment : BaseFragment() {

    private lateinit var binding: FragmentGalleryBinding
    private val viewModel: GalleryViewModel by viewModel()

    private val galleryListAdapter by lazy { GalleryListAdapter(viewModel) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)
        binding.apply {
            vm = this@GalleryFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        startObservingData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGalleryListAdapter()
        viewModel.initImages()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupGalleryListAdapter() {
        galleryList.apply {
            adapter = galleryListAdapter
            layoutManager = FlexboxLayoutManager(this@GalleryFragment.activity).apply {
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.FLEX_START
                stopScroll()
            }
            setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_UP -> {
                        galleryListAdapter.cancelImageEnlargement()
                        viewModel.hideEnlargedImage()
                    }
                    MotionEvent.ACTION_MOVE -> galleryListAdapter.cancelImageEnlargement()
                }
                enlargedImage.isVisible()
            }
        }

        viewModel.galleryImages.observe(viewLifecycleOwner) { images ->
            if (!images.isNullOrEmpty()) {
                galleryListAdapter.galleryImages.clear()
                galleryListAdapter.galleryImages.addAll(images)
                galleryListAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun startObservingData() {
        viewModel.showLoader.observe(viewLifecycleOwner) { showLoader -> bottomRefresh.visibleIf(showLoader) }
        viewModel.imageToEnlarge.observe(viewLifecycleOwner) { image ->
            if (image != null) {
                enlargedImageBackground.visibleIf(true)
                enlargedImage.visibleIf(true)
                parentLayout.takeScreenshot(requireActivity().window) { bitmap ->
                    enlargedImageBackground.blurBitmap(bitmap) {
                        enlargedImage.load(image.url) {
                            onDownload({ imageEnlargementLoader.visibleIf(true) }, { imageEnlargementLoader.visibleIf(false) })
                        }
                    }
                }
            } else {
                if (enlargedImage.isVisible()) {
                    enlargedImage.visibleIf(false)
                    imageEnlargementLoader.visibleIf(false)
                    enlargedImageBackground.visibleIf(false)
                    enlargedImage.setImageResource(0)
                    enlargedImageBackground.setImageResource(0)
                }
            }
        }
    }
}