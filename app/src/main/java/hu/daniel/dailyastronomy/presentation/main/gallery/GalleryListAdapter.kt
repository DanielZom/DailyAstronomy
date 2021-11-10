package hu.daniel.dailyastronomy.presentation.main.gallery

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.databinding.ComponentGalleryListItemBinding
import hu.daniel.dailyastronomy.databinding.ComponentGalleryPagingListItemBinding
import hu.daniel.dailyastronomy.dto.GalleryImage
import hu.daniel.dailyastronomy.util.onDownload

class GalleryListAdapter(private val viewModel: GalleryViewModel) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val galleryImages: MutableList<GalleryImage?> = arrayListOf()

    private val imageEnlargementHandler = Handler(Looper.getMainLooper())
    private val IMAGE_ENLARGE_DELAY = 200L
    private val IMAGE_LIST_ITEM = 1
    private val PAGING_BUTTON_LIST_ITEM = 2

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == IMAGE_LIST_ITEM) {
            val item = galleryImages[position]!!
            val parentView = holder.itemView as CardView

            (holder as GalleryViewHolder).binding.image.apply {
                load(item.url) {
                    onDownload(
                            { parentView.setCardBackgroundColor(0) },
                            { parentView.setCardBackgroundColor(ContextCompat.getColor(parentView.context, R.color.secondaryColor)) }
                    )
                }

            }
            parentView.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> startImageEnlargement(item)
                    MotionEvent.ACTION_UP -> {
                        cancelImageEnlargement()
                        viewModel.hideEnlargedImage()
                    }
                }
                false
            }
        } else {
            (holder as GalleryPagingViewHolder).binding.vm = viewModel
        }
    }

    private fun startImageEnlargement(image: GalleryImage) {
        imageEnlargementHandler.postDelayed({ viewModel.enlargeImage(image) }, IMAGE_ENLARGE_DELAY)
    }

    fun cancelImageEnlargement() {
        imageEnlargementHandler.removeCallbacksAndMessages(null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == IMAGE_LIST_ITEM) {
            GalleryViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.component_gallery_list_item, parent, false))
        } else {
            GalleryPagingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.component_gallery_paging_list_item, parent, false))
        }
    }

    class GalleryViewHolder(val binding: ComponentGalleryListItemBinding) :
            RecyclerView.ViewHolder(binding.root)

    class GalleryPagingViewHolder(val binding: ComponentGalleryPagingListItemBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = galleryImages.size

    override fun getItemViewType(position: Int) = if (galleryImages[position] != null) IMAGE_LIST_ITEM else PAGING_BUTTON_LIST_ITEM
}