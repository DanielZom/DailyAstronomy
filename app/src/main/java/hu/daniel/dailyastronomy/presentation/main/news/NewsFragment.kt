package hu.daniel.dailyastronomy.presentation.main.news

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.databinding.FragmentNewsBinding
import hu.daniel.dailyastronomy.dto.ListLoaderPosition
import hu.daniel.dailyastronomy.presentation.main.BaseFragment
import hu.daniel.dailyastronomy.util.extensions.isTablet
import hu.daniel.dailyastronomy.util.extensions.onScrollReachedBottom
import hu.daniel.dailyastronomy.util.visibleIf
import kotlinx.android.synthetic.main.component_news_search.*
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*


class NewsFragment : BaseFragment() {

    private val viewModel: NewsViewModel by sharedViewModel()

    private val newsListAdapter by lazy { NewsListAdapter(viewModel) }
    private var newsListLayoutManager: RecyclerView.LayoutManager? = null
    private var isNewsLimitDialogShown = false

    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            layoutManager = newsListLayoutManager ?: getLayoutAdapterBy(requireContext().resources.configuration)
            vm = viewModel
        }

        startObservingData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureSwipeRefresh()

        setupNewsListAdapter()

        endDateInputField.maxDate = Calendar.getInstance()
        startDateInputField.maxDate = Calendar.getInstance()
        startDateInputField.setOnSearchListener { date -> endDateInputField.minDate = date }
        endDateInputField.setOnSearchListener { date -> startDateInputField.maxDate = date }
        startDateInputField.setOnTextDeleteListener { endDateInputField.minDate = null }
        endDateInputField.setOnTextDeleteListener {
            startDateInputField.maxDate = Calendar.getInstance()
        }

        searchIcon.setOnClickListener {
            viewModel.resetNews(
                    ListLoaderPosition.TOP,
                    startDateInputField.lastSearchedFormattedDate,
                    endDateInputField.lastSearchedFormattedDate
            )
        }
        newsList.onScrollReachedBottom {
            viewModel.requestMoreNews(
                    ListLoaderPosition.BOTTOM,
                    startDateInputField.lastSearchedFormattedDate,
                    endDateInputField.lastSearchedFormattedDate
            )
        }

        viewModel.screenLoaded()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        newsListLayoutManager = getLayoutAdapterBy(newConfig)
    }

    private fun startObservingData() {
        viewModel.showLoader.observe(viewLifecycleOwner) { loader ->
            when (loader.first) {
                ListLoaderPosition.TOP -> swipeRefresh.isRefreshing = loader.second
                ListLoaderPosition.BOTTOM -> bottomRefresh.visibleIf(loader.second)
            }
        }
        viewModel.showError.observe(viewLifecycleOwner) { showError ->
            if (showError) newsList.scrollToPosition(0)
        }
        viewModel.resetListPosition.observe(viewLifecycleOwner) { resetListPosition ->
            if (resetListPosition) newsList.scrollToPosition(0)
        }

        viewModel.newsLimitReached.observe(viewLifecycleOwner) { limitReached ->
            if (limitReached && !isNewsLimitDialogShown) showNewsLimitDialog()
        }
    }

    private fun showNewsLimitDialog() {
        isNewsLimitDialogShown = true
        AlertDialog.Builder(requireContext())
                .setCancelable(false)
                .setMessage(R.string.news_limit_reached)
                .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
                .setOnDismissListener { isNewsLimitDialogShown = false }
                .show()
    }

    private fun setupNewsListAdapter() {
        newsList.adapter = newsListAdapter
        viewModel.news.observe(viewLifecycleOwner) { news ->
            if (!news.isNullOrEmpty()) {
                newsListAdapter.news.clear()
                newsListAdapter.news.addAll(news)
                newsListAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun getLayoutAdapterBy(configuration: Configuration): RecyclerView.LayoutManager {
        return if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE || requireActivity().isTablet()) {
            GridLayoutManager(requireContext(), 2)
        } else {
            LinearLayoutManager(requireContext())
        }
    }

    private fun configureSwipeRefresh() {
        swipeRefresh.setProgressBackgroundColorSchemeResource(R.color.mainColor)
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.purple))
        swipeRefresh.setOnRefreshListener {
            viewModel.resetNews(
                    ListLoaderPosition.TOP,
                    startDateInputField.lastSearchedFormattedDate,
                    endDateInputField.lastSearchedFormattedDate
            )
        }
    }
}