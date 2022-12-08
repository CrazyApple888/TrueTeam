package me.idrew.main_cards

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import ru.nsu.alphacontest.main_cards.R

class CardsFragment: Fragment(R.layout.fragment_cards) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val request = NavDeepLinkRequest.Builder
//            .fromUri("alfa-cards://profile_fragment".toUri())
//            .build()
//        findNavController().navigate(
//            request = request,
//        )
    }
}