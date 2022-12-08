package ru.nsu.alphacontest.login.ui

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.nsu.alphacontest.design.dialogs.InternalServerErrorDialog
import ru.nsu.alphacontest.design.dialogs.LoadingDialog
import ru.nsu.alphacontest.design.dialogs.NoConnectivityDialog
import ru.nsu.alphacontest.design.dialogs.utils.dismissIfVisible
import ru.nsu.alphacontest.login.R
import ru.nsu.alphacontest.login.databinding.FragmentLoginBinding
import ru.nsu.alphacontest.login.presentation.ContentState
import ru.nsu.alphacontest.login.presentation.ErrorType
import ru.nsu.alphacontest.login.presentation.LoginUiState
import ru.nsu.alphacontest.login.presentation.LoginViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    private val viewModel by viewModel<LoginViewModel>()

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
            viewModel.isLoginButtonAvailable.collect {
                binding.loginButton.isEnabled = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                obtainUiState(it)
            }
        }

        binding.emailEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text ?: "")
            clearErrors()
        }

        binding.passwordEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChanged(text ?: "")
            clearErrors()
        }

        binding.loginButton.setOnClickListener {
            viewModel.onLoginClicked()
        }

        binding.registrationButton.setOnClickListener {
            val request = NavDeepLinkRequest.Builder
                .fromUri("alfa-cards://registration_fragment".toUri())
                .build()
            findNavController().navigate(
                request = request,
            )
        }
    }

    private fun obtainUiState(state: LoginUiState) {

        when (state.contentState) {
            is ContentState.Error -> {
                loadingDialog.dismiss()
                when (state.contentState.type) {
                    ErrorType.InternalServerError -> {
                        showInternalServerErrorDialog()
                    }
                    ErrorType.InvalidLoginOrPassword -> {
                        binding.emailLayout.error = " "
                        binding.passwordLayout.error = state.contentState.message
                    }
                    ErrorType.NoConnectivity -> {
                        showConnectivityErrorDialog()
                    }
                }
            }
            ContentState.Input -> {
                loadingDialog.dismissIfVisible()
                with(binding) {
                    emailLayout.error = null
                    passwordLayout.error = null
                }
            }
            ContentState.Loading -> {
                showLoadingDialog()
            }
            ContentState.Success -> {
                loadingDialog.dismissIfVisible()
                val request = NavDeepLinkRequest.Builder
                    .fromUri("alfa-cards://main_cards_fragment".toUri())
                    .build()
                findNavController().navigate(
                    request = request,
                    navOptions = NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
                )
            }
        }
    }

    private fun clearErrors() {
        viewModel.clearError()
    }

    private fun showLoadingDialog() {
        loadingDialog.show(childFragmentManager, "Loading dialog")
        viewModel.clearError()
    }

    private fun showConnectivityErrorDialog() {
        connectivityErrorFragment.show(childFragmentManager, "Connectivity error dialog")
        loadingDialog.dialog?.setOnDismissListener {
            viewModel.clearError()
        }
    }

    private fun showInternalServerErrorDialog() {
        internalServerErrorDialog.show(childFragmentManager, "Internal server error dialog")
        internalServerErrorDialog.dialog?.setOnDismissListener {
            viewModel.clearError()
        }
    }
}