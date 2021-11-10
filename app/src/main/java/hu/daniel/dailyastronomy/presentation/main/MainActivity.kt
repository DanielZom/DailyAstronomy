package hu.daniel.dailyastronomy.presentation.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.databinding.ActivityMainBinding
import hu.daniel.dailyastronomy.presentation.BaseActivity
import hu.daniel.dailyastronomy.presentation.main.gallery.GalleryFragment
import hu.daniel.dailyastronomy.presentation.main.live.LiveFragment
import hu.daniel.dailyastronomy.presentation.main.live.LiveViewModel
import hu.daniel.dailyastronomy.presentation.main.news.NewsFragment
import hu.daniel.dailyastronomy.presentation.main.news.NewsViewModel
import hu.daniel.dailyastronomy.presentation.main.newsdetail.NewsDetailFragment
import hu.daniel.dailyastronomy.presentation.main.settings.SettingsFragment
import hu.daniel.dailyastronomy.presentation.main.settings.SettingsViewModel
import hu.daniel.dailyastronomy.presentation.main.solarsystem.SolarSystemFragment
import hu.daniel.dailyastronomy.util.extensions.loadTo
import hu.daniel.dailyastronomy.util.visibleIf
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {

    private val settingsViewModel: SettingsViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()
    private val newsViewModel: NewsViewModel by viewModel()
    private val liveViewModel: LiveViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
        binding.apply {
            vm = mainViewModel
            lifecycleOwner = this@MainActivity
        }

        mainViewModel.hasNetwork.observe(this) { hasNetwork -> networkNotification.visibleIf(!hasNetwork) }
        settingsViewModel.themeChanged.observe(this) { isThemeChanged -> if (isThemeChanged) setSelectedTab(R.id.settings) }
        newsViewModel.selectedArticle.observe(this) { article -> newsDetailsContainer.visibleIf(article != null) }
        liveViewModel.isStatusbarDark.observe(this) { dark -> configureStatusbar(menuContainer, if (dark) R.color.mainColor else android.R.color.transparent) }
        configureStatusbar(menuContainer, android.R.color.transparent)
        configureNavigation()
        loadNewsDetailFragment()
    }

    private fun configureNavigation() {
        bottomNavigationBar.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.news -> {
                    mainViewModel.actualScreenId = R.id.news
                    NewsFragment().loadTo(supportFragmentManager)
                    loadNewsDetailFragment()
                    true
                }
                R.id.solarSystem -> {
                    mainViewModel.actualScreenId = R.id.solarSystem
                    SolarSystemFragment().loadTo(supportFragmentManager)
                    true
                }
                R.id.live -> {
                    mainViewModel.actualScreenId = R.id.live
                    LiveFragment().loadTo(supportFragmentManager)
                    true
                }
                R.id.gallery -> {
                    mainViewModel.actualScreenId = R.id.gallery
                    GalleryFragment().loadTo(supportFragmentManager)
                    true
                }
                R.id.settings -> {
                    mainViewModel.actualScreenId = R.id.settings
                    SettingsFragment().loadTo(supportFragmentManager)
                    true
                }
                else -> false
            }
        }
        if (mainViewModel.actualScreenId == null) setSelectedTab(R.id.news)
    }

    private fun setSelectedTab(tabId: Int) {
        bottomNavigationBar.selectedItemId = tabId
    }

    private fun loadNewsDetailFragment() {
        NewsDetailFragment().loadTo(supportFragmentManager, R.id.newsDetailsContainer)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setSelectedTab(mainViewModel.actualScreenId ?: R.id.news)
    }

    override fun onBackPressed() {
        if (newsViewModel.isNewsDetailOpened) {
            newsViewModel.backClicked()
        } else {
            super.onBackPressed()
        }
    }
}