package ru.nsu.alphacontest.registration.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.nsu.alphacontest.design.dialogs.InternalServerErrorDialog
import ru.nsu.alphacontest.design.dialogs.LoadingDialog
import ru.nsu.alphacontest.design.dialogs.NoConnectivityDialog
import ru.nsu.alphacontest.design.dialogs.utils.dismissIfVisible
import ru.nsu.alphacontest.registration.R
import ru.nsu.alphacontest.registration.databinding.FragmentRegistrationBinding
import ru.nsu.alphacontest.registration.presentation.ContentState
import ru.nsu.alphacontest.registration.presentation.ErrorType
import ru.nsu.alphacontest.registration.presentation.RegistrationUiState
import ru.nsu.alphacontest.registration.presentation.RegistrationViewModel
import ru.nsu.alphacontest.utils.ErrorState
import ru.nsu.alphacontest.utils.setError
import ru.nsu.alphacontest.utils.setGlobalErrorMessage

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val binding by viewBinding(FragmentRegistrationBinding::bind)

    private val viewModel by viewModel<RegistrationViewModel>()

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
        with(binding) {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.isRegistrationButtonAvailable.collect {
                    registrationButton.isEnabled = it
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.uiState.collect {
                    obtainUiState(it)
                }
            }

            registrationButton.setOnClickListener {
                viewModel.onRegistrationClicked()
            }

            nameEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onNameTextChanged(text.toString())
                viewModel.clearError()
            }

            emailEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onEmailTextChanged(text.toString())
                viewModel.clearError()
            }

            passwordEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onPasswordTextChanged(text.toString())
                viewModel.clearError()
            }

            repeatPasswordEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onRepeatPasswordTextChanged(text.toString())
                viewModel.clearError()
            }
        }
    }

    private fun obtainUiState(state: RegistrationUiState) {

        when (state.contentState) {
            is ContentState.Error -> {
                loadingDialog.dismiss()
                when (state.contentState.type) {
                    ErrorType.InternalServerError -> {
                        showInternalServerErrorDialog()
                    }
                    is ErrorType.Validation -> {
                        setGlobalErrorMessage(
                            textInputLayouts = listOf(
                                binding.emailLayout,
                                binding.nameLayout,
                                binding.passwordLayout,
                                binding.repeatPasswordLayout,
                            ),
                            errorMessage = state.contentState.type.message
                        )
                    }
                    ErrorType.NoConnectivity -> {
                        showConnectivityErrorDialog()
                    }
                }
            }
            ContentState.Input -> {
                loadingDialog.dismissIfVisible()
                if (state.localPasswordsError !is ErrorState.Error && state.localEmailError !is ErrorState.Error) {
                    with(binding) {
                        nameLayout.error = null
                        emailLayout.error = null
                        passwordLayout.error = null
                        repeatPasswordLayout.error = null
                    }
                } else if(state.localPasswordsError is ErrorState.Error)  {
                    setError(binding.passwordLayout, binding.repeatPasswordLayout, state.localPasswordsError)
                } else if(state.localEmailError is ErrorState.Error) {
                    setError(binding.emailLayout, state.localEmailError)
                }
            }
            ContentState.Loading -> {
                showLoadingDialog()
            }
            ContentState.Success -> {
                loadingDialog.dismissIfVisible()
                findNavController().navigate(
                    R.id.action_registrationFragment_to_cardsFragment
                )
            }
        }
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