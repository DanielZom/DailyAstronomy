package hu.daniel.dailyastronomy.presentation.main.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import hu.daniel.dailyastronomy.R
import hu.daniel.dailyastronomy.databinding.FragmentLiveBinding
import hu.daniel.dailyastronomy.presentation.main.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LiveFragment: BaseFragment() {

    private lateinit var binding: FragmentLiveBinding
    private val viewModel: LiveViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_live, container, false)
        binding.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.darkenStatusbar()
    }

    override fun onDetach() {
        viewModel.resetStatusbar()
        super.onDetach()
    }
}