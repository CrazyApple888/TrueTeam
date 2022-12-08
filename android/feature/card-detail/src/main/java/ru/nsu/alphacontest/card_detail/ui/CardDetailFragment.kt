package ru.nsu.alphacontest.card_detail.ui

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.nsu.alphacontest.card_detail.R
import ru.nsu.alphacontest.card_detail.databinding.FragmentCardDetailBinding
import ru.nsu.alphacontest.card_detail.presentation.CardDetailUiState
import ru.nsu.alphacontest.card_detail.presentation.CardDetailViewModel
import ru.nsu.alphacontest.card_detail.presentation.ContentState
import ru.nsu.alphacontest.card_detail.presentation.ErrorType
import ru.nsu.alphacontest.design.dialogs.InternalServerErrorDialog
import ru.nsu.alphacontest.design.dialogs.LoadingDialog
import ru.nsu.alphacontest.design.dialogs.NoConnectivityDialog

class CardDetailFragment : Fragment(R.layout.fragment_card_detail) {
    private val binding by viewBinding(FragmentCardDetailBinding::bind)

    private val viewModel by viewModel<CardDetailViewModel>(
        parameters = {
            parametersOf(
                arguments?.getString("cardNumber")
            )
        }
    )

    private lateinit var loadingDialog: DialogFragment

    private lateinit var connectivityErrorFragment: DialogFragment

    private lateinit var internalServerErrorDialog: DialogFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog()
        connectivityErrorFragment = NoConnectivityDialog()
        internalServerErrorDialog = InternalServerErrorDialog()
        setupListeners()
    }

    private fun setupListeners() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                obtainUiState(it)
            }
        }

        binding.exitButton.setOnClickListener {
            viewModel.onDeleteClicked()
        }
    }

    private fun obtainUiState(state: CardDetailUiState) {

        when (state.contentState) {
            is ContentState.Error -> {
                loadingDialog.dismiss()
                when (state.contentState.type) {
                    ErrorType.InternalServerError -> {
                        showInternalServerErrorDialog()
                    }
                    ErrorType.NoConnectivity -> {
                        showConnectivityErrorDialog()
                    }
                }
            }
            ContentState.Loading -> {
                showLoadingDialog()
            }
            ContentState.Content -> {
                loadingDialog.dismiss()
                with(binding) {
                    if(state.codeImage == null) {
                        numberEditText.setText(state.code)
                        numberLayout.isVisible = true
                    } else {
                        barcode.setImageBitmap(state.codeImage)
                        barcode.isVisible = true
                    }
                    nameEditText.setText(state.card?.name)
                    categoryEditText.setText(state.card?.category?.stringValue)
                }
            }
            ContentState.Exit -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun showLoadingDialog() {
        if (!loadingDialog.isVisible)
            loadingDialog.show(childFragmentManager, "Loading dialog")
    }

    private fun showConnectivityErrorDialog() {
        connectivityErrorFragment.show(childFragmentManager, "Connectivity error dialog")
    }

    private fun showInternalServerErrorDialog() {
        internalServerErrorDialog.show(childFragmentManager, "Internal server error dialog")
    }
}