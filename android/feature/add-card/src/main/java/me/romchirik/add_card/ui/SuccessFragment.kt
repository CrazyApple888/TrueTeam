package me.romchirik.add_card.ui

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import me.romchirik.add_card.R
import me.romchirik.add_card.databinding.FragmentSuccessBinding
import me.romchirik.add_card.presentation.SuccessFragmentArgs


class SuccessFragment : Fragment(R.layout.fragment_success) {

    private val binding by viewBinding(FragmentSuccessBinding::bind)

    private val args by lazy { arguments?.getSerializable(ARGS_KEY) as? SuccessFragmentArgs }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args?.let {
            binding.cardName.text = it.name
            binding.cardNumber.text = it.number
        } ?: returnToMainScreen()

        binding.submitButton.setOnClickListener {
            returnToMainScreen()
        }

        activity?.onBackPressedDispatcher?.addCallback {
            returnToMainScreen()
        }
    }

    private fun returnToMainScreen() {
        val request =
            NavDeepLinkRequest.Builder.fromUri("alfa-cards://main_cards_fragment".toUri()).build()
        findNavController().navigate(
            request = request,
        )
    }

    companion object {

        const val ARGS_KEY = "ARGS_KEY"
    }
}