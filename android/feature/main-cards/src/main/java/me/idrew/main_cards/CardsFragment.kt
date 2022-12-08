package me.idrew.main_cards

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.chip.Chip
import me.idrew.main_cards.presentation.CardsScreenUIState
import me.idrew.main_cards.presentation.UIState
import me.idrew.main_cards.presentation.adapter.CardListItem
import me.idrew.main_cards.presentation.adapter.CardsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.nsu.alphacontest.main_cards.R
import ru.nsu.alphacontest.main_cards.databinding.FragmentCardsBinding

class CardsFragment: Fragment(R.layout.fragment_cards) {

    private val binding by viewBinding(FragmentCardsBinding::bind)

    private val viewModel: CardsViewModel by viewModel()

    private val adapter: CardsAdapter by lazy {
        CardsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObservers()
        initListeners()
    }

    private fun initListeners() {
        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.onCategoryChosen(checkedId)
        }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it.state) {
                    UIState.Content -> bindContentState(it)
                    UIState.Error -> bindErrorState(it)
                    UIState.Initial -> {}
                }
            }
        }
    }

    private fun bindContentState(uiState: CardsScreenUIState) {
        binding.chipGroup.removeAllViews()
        uiState.availableCategories.forEach {
            binding.chipGroup.addView(
                Chip(requireContext()).apply {
                    isCheckable = true
                    id = it.id
                    text = it.text
                }
            )
        }

        adapter.items = uiState.cards
    }

    private fun bindErrorState(uiState: CardsScreenUIState) {
    }

    private fun initAdapter() {
        binding.recycler.adapter = adapter
    }
}