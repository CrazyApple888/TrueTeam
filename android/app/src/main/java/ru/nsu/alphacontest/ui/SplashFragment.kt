package ru.nsu.alphacontest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.nsu.alphacontest.R
import ru.nsu.alphacontest.databinding.FragmentSphashBinding
import ru.nsu.alphacontest.presentation.SplashViewModel

class SplashFragment : Fragment(R.layout.fragment_sphash) {

    private val binding: FragmentSphashBinding by viewBinding(FragmentSphashBinding::bind)

    private val viewModel: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        viewModel.isUserAuthorized.observe(viewLifecycleOwner) {

        }
    }
}