package hu.daniel.dailyastronomy.presentation.main.solarsystem

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.databinding.FragmentSolarSystemBinding
import hu.daniel.dailyastronomy.dto.SolarSystemObject
import hu.daniel.dailyastronomy.presentation.main.BaseFragment
import hu.daniel.dailyastronomy.util.extensions.getStatusbarHeight
import hu.daniel.dailyastronomy.util.extensions.screenWidth
import hu.daniel.dailyastronomy.util.extensions.startAlphaAnimation
import hu.daniel.dailyastronomy.util.extensions.startSizeAndPositionAnimation
import hu.daniel.dailyastronomy.util.noop
import kotlinx.android.synthetic.main.component_solar_system_list_item.*
import kotlinx.android.synthetic.main.component_solar_system_list_item.view.*
import kotlinx.android.synthetic.main.fragment_solar_system.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs


class SolarSystemFragment : BaseFragment() {

    private lateinit var binding: FragmentSolarSystemBinding
    private val viewModel: SolarSystemViewModel by viewModel()
    private val solarSystemListAdapter = SolarSystemListAdapter()
    private var listSmoothScrolled = false
    private var listItemToAnimate: Pair<View, SolarSystemObject>? = null
    private val isLandscape: Boolean
        get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    private val LIST_ITEM_ANIMATION_DURATION = 500L
    private val LIST_ITEM_REVERSE_ANIMATION_DURATION = 200L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_solar_system, container, false)
        binding.apply {
            viewModel = this@SolarSystemFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureList()
    }

    private fun configureList() {
        val linearSmoothScroller = createSmoothScroller()
        solarSystemList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = solarSystemListAdapter.apply { solarSystemObjects = viewModel.solarSystemObjects }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) = noop()
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (!listSmoothScrolled) {
                            val layoutManager = (solarSystemList.layoutManager as LinearLayoutManager)
                            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                            val firstVisibleItem = getSolarSystemListItemBy(firstVisibleItemPosition)
                            val lastVisibleItem = getSolarSystemListItemBy(lastVisibleItemPosition)

                            if (lastVisibleItemPosition != firstVisibleItemPosition) {
                                val screenHalf = requireActivity().screenWidth / 2
                                val firstVisibleItemCenterXPos = abs(firstVisibleItem.x) + screenHalf
                                val lastVisibleItemCenterXPos = abs(lastVisibleItem.x) + screenHalf
                                val positionToScroll = if (abs(screenHalf - firstVisibleItemCenterXPos) < abs(screenHalf - lastVisibleItemCenterXPos)) {
                                    listItemToAnimate = Pair(firstVisibleItem, solarSystemListAdapter.solarSystemObjects[firstVisibleItemPosition])
                                    firstVisibleItemPosition
                                } else {
                                    listItemToAnimate = Pair(lastVisibleItem, solarSystemListAdapter.solarSystemObjects[lastVisibleItemPosition])
                                    lastVisibleItemPosition
                                }

                                listSmoothScrolled = true
                                linearSmoothScroller.scrollTo(positionToScroll)
                            } else {
                                listSmoothScrolled = false
                                listItemToAnimate = Pair(lastVisibleItem, solarSystemListAdapter.solarSystemObjects[lastVisibleItemPosition])
                                animateSolarSystemListItem(listItemToAnimate!!)
                            }
                        } else {
                            listSmoothScrolled = false
                            animateSolarSystemListItem(listItemToAnimate!!)
                        }
                    } else {
                        if (!listSmoothScrolled && listItemToAnimate != null) {
                            animateSolarSystemListItem(listItemToAnimate!!, true)
                            listItemToAnimate = null
                        }
                    }
                }
            })
        }
        solarSystemListAdapter.setOnBindListener {
            listItemToAnimate = Pair(getSolarSystemListItemBy(0), solarSystemListAdapter.solarSystemObjects[0])
            animateSolarSystemListItem(listItemToAnimate!!)
        }
    }

    private fun LinearSmoothScroller.scrollTo(position: Int) {
        targetPosition = position
        solarSystemList.layoutManager!!.startSmoothScroll(this)
    }

    private fun createSmoothScroller() = object : LinearSmoothScroller(requireContext()) {
        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
            val smoothScrollingSpeedInMillisec = if (isLandscape) 150F else 250F
            return smoothScrollingSpeedInMillisec / displayMetrics.densityDpi
        }
    }

    private fun animateSolarSystemListItem(item: Pair<View, SolarSystemObject>, reverse: Boolean = false) {
        val parent = item.first.solarSystemItemParentLayout
        val name = item.first.name
        val info = item.first.info
        val image = item.first.objectImage
        val characteristicLayout = item.first.characteristics
        val characteristics = item.first.objectCharacteristics

        if (!reverse) {
            var imageYPosition: Float? = null
            var imageXPosition: Float? = null
            val imageSize: Float
            if (isLandscape) {
                imageSize = 1.3F
                imageXPosition = requireActivity().screenWidth * 0.15F
            } else {
                imageSize = 1.5F
                imageYPosition = name.y + name.height + resources.getStatusbarHeight() + resources.getDimensionPixelSize(R.dimen.dp_16)
            }
            image.startSizeAndPositionAnimation(imageSize, imageYPosition, imageXPosition, LIST_ITEM_ANIMATION_DURATION) {
                configureCharacteristics(characteristics, item.second.characteristics)
                info.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.second.wikiUrl))) }
                name.startAlphaAnimation(1F, LIST_ITEM_ANIMATION_DURATION)
                characteristicLayout.startAlphaAnimation(1F, LIST_ITEM_ANIMATION_DURATION)
                info.startAlphaAnimation(1F, LIST_ITEM_ANIMATION_DURATION)
            }
        } else {
            var imageYPosition: Float? = null
            var imageXPosition: Float? = null
            if (isLandscape) {
                imageXPosition = parent.width / 2F - image.width / 2F
            } else {
                imageYPosition = parent.height / 2F - image.height / 2F
            }
            image.startSizeAndPositionAnimation(1F, imageYPosition, imageXPosition, LIST_ITEM_REVERSE_ANIMATION_DURATION) {
                name.startAlphaAnimation(0F, LIST_ITEM_REVERSE_ANIMATION_DURATION)
                characteristicLayout.startAlphaAnimation(0F, LIST_ITEM_REVERSE_ANIMATION_DURATION)
                info.startAlphaAnimation(0F, LIST_ITEM_REVERSE_ANIMATION_DURATION)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun configureCharacteristics(characteristicsContainer: LinearLayout, characteristics: Map<String, String>) {
        characteristicsContainer.removeAllViews()
        characteristics.forEach {
            val characteristic = TextView(requireContext()).apply {
                text = "${it.key}: ${it.value}"
                typeface = Typeface.DEFAULT_BOLD
                setTextColor(ContextCompat.getColor(requireContext(), R.color.secondaryColor70))
                updatePadding(top = resources.getDimensionPixelSize(R.dimen.dp_8))
            }
            characteristicsContainer.addView(characteristic)
        }
    }

    private fun getSolarSystemListItemBy(position: Int) = solarSystemList.findViewWithTag<View>(solarSystemListAdapter.solarSystemObjects[position].name)
}