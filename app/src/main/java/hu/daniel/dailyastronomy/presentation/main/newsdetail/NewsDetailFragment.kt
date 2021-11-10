package hu.daniel.dailyastronomy.presentation.main.newsdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.databinding.FragmentNewsDetailsBinding
import hu.daniel.dailyastronomy.presentation.main.BaseFragment
import hu.daniel.dailyastronomy.presentation.main.news.NewsViewModel
import hu.daniel.dailyastronomy.util.extensions.isTablet
import hu.daniel.dailyastronomy.util.extensions.setTopPaddingForStatusbar
import kotlinx.android.synthetic.main.fragment_news_details.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NewsDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private val viewModel: NewsViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)
        binding.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        header.setTopPaddingForStatusbar()
        if (requireActivity().isTablet())
            back.layoutParams = (back.layoutParams as ConstraintLayout.LayoutParams).apply { matchConstraintPercentWidth = 0.09F }
    }
}