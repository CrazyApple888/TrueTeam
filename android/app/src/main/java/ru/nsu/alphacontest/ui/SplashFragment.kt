package ru.nsu.alphacontest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.nsu.alphacontest.R
import ru.nsu.alphacontest.presentation.SplashViewModel

class SplashFragment : Fragment(R.layout.fragment_sphash) {

    private val viewModel: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        viewModel.isUserAuthorized.observe(viewLifecycleOwner) { authorized ->
            findNavController().navigate(
                if (authorized) R.id.action_splashFragment_to_cardsFragment
                else R.id.action_splashFragment_to_loginFragment
            )
        }
    }
}