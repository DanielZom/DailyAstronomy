package hu.daniel.dailyastronomy.presentation.splash

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.presentation.BaseActivity
import hu.daniel.dailyastronomy.presentation.main.MainActivity
import hu.daniel.dailyastronomy.util.extensions.startSizePulseAnimation
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity() {

    private val viewModel: SplashViewModel by viewModel()

    private val SCREEN_TIME = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.downloadNews()

        appIcon.startSizePulseAnimation(1F, 0.5F, 1000L)

        startScreenTimeout()
    }

    private fun startScreenTimeout() {
        lifecycleScope.launch {
            delay(SCREEN_TIME)
            appIcon.clearAnimation()
            navigateWithFinish(MainActivity::class.java)
        }
    }
}