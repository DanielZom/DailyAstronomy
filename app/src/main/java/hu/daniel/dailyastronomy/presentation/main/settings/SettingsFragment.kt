package hu.daniel.dailyastronomy.presentation.main.settings

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.databinding.FragmentSettingsBinding
import hu.daniel.dailyastronomy.presentation.main.BaseFragment
import hu.daniel.dailyastronomy.util.extensions.isTablet
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingsFragment : BaseFragment() {
    private val viewModel: SettingsViewModel by sharedViewModel()
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        binding.apply {
            viewModel = this@SettingsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rearrangeLayoutBy(resources.configuration.orientation)

        configureThemeSwitching()
    }

    private fun rearrangeLayoutBy(orientation: Int) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE || requireActivity().isTablet()) {
            val horizontalLayoutParam = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                weight = 1F
            }
            themeLayout.layoutParams = horizontalLayoutParam
            versionLayout.layoutParams = horizontalLayoutParam
            settingsLayout.orientation = LinearLayout.HORIZONTAL
        } else {
            val verticalLayoutParam = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                weight = 0F
            }
            themeLayout.layoutParams = verticalLayoutParam
            versionLayout.layoutParams = verticalLayoutParam
            settingsLayout.orientation = LinearLayout.VERTICAL
        }
    }

    private fun configureThemeSwitching() {
        themeSwitch.setOnClickListener {
            viewModel.themeSwitchClicked(if (themeSwitch.isChecked) ThemeState.DARK else ThemeState.LIGHT)
        }
    }
}