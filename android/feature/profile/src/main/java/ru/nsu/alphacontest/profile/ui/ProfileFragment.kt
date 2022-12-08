package ru.nsu.alphacontest.profile.ui

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.nsu.alphacontest.design.dialogs.InternalServerErrorDialog
import ru.nsu.alphacontest.design.dialogs.LoadingDialog
import ru.nsu.alphacontest.design.dialogs.NoConnectivityDialog
import ru.nsu.alphacontest.profile.R
import ru.nsu.alphacontest.profile.databinding.FragmentProfileBinding
import ru.nsu.alphacontest.profile.presentation.ContentState
import ru.nsu.alphacontest.profile.presentation.ErrorType
import ru.nsu.alphacontest.profile.presentation.ProfileUiState
import ru.nsu.alphacontest.profile.presentation.ProfileViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel by viewModel<ProfileViewModel>()

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
            viewModel.onExitClicked()
        }
    }

    private fun obtainUiState(state: ProfileUiState) {

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
                binding.emailEditText.setText(state.email)
                binding.nameEditText.setText(state.name)
            }
            ContentState.Exit -> {
                val request = NavDeepLinkRequest.Builder
                    .fromUri("alfa-cards://splash_fragment".toUri())
                    .build()
                findNavController().navigate(
                    request = request,
                    navOptions = NavOptions.Builder().setPopUpTo(R.id.profileFragment, true).build()
                )
            }
        }
    }

    private fun showLoadingDialog() {
        if (!loadingDialog.isVisible)
            loadingDialog.show(childFragmentManager, "Loading dialog")
    }

    private fun showConnectivityErrorDialog() {
        connectivityErrorFragment.show(childFragmentManager, "Connectivity error dialog")
//        loadingDialog.dialog?.setOnDismissListener {
//            findNavController().popBackStack()
//        }
    }

    private fun showInternalServerErrorDialog() {
        internalServerErrorDialog.show(childFragmentManager, "Internal server error dialog")
//        internalServerErrorDialog.dialog?.setOnDismissListener {
//            findNavController().popBackStack()
//        }
    }
}