package me.idrew.main_cards.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.chip.Chip
import me.idrew.main_cards.presentation.adapter.CardsAdapter
import me.idrew.main_cards.presentation.model.CardsScreenUIState
import me.idrew.main_cards.presentation.model.ErrorType
import me.idrew.main_cards.presentation.model.UIState
import me.idrew.main_cards.presentation.viewmodel.CardsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.nsu.alphacontest.main_cards.R
import ru.nsu.alphacontest.main_cards.databinding.FragmentCardsBinding

class CardsFragment: Fragment(R.layout.fragment_cards) {

    private val binding by viewBinding(FragmentCardsBinding::bind)

    private val viewModel: CardsViewModel by viewModel()

    private val adapter: CardsAdapter by lazy {
        CardsAdapter(
            onCardClickListener = {
                val request = NavDeepLinkRequest.Builder
                    .fromUri("alfa-cards://crad_detail_fragment/?cardNumber=${it.number}".toUri())
                    .build()
                findNavController().navigate(
                    request = request,
                )
            }
        )
    }

    private var requestLocationLauncher: ActivityResultLauncher<Array<String>>? = null

    private var requestCameraLauncher: ActivityResultLauncher<String>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requestLocationLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            viewModel::onLocationPermissionResult
        )
        requestCameraLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
            viewModel::onCameraPermissionResult
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChips()
        initAdapter()
        initObservers()
        initListeners()
    }

    private fun initListeners() {
        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.onCategoryChosen(checkedId)
        }
        binding.floatingButton.setOnClickListener {
            viewModel.onAddCardClicked()
        }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it.state) {
                    is UIState.Content -> bindContentState(it)
                    is UIState.Error -> bindErrorState(it)
                    is UIState.ChipInit -> initChips()
                    is UIState.Initial -> {}
                }
            }
        }

        viewModel.requestLocationPermissionEvent.observe(viewLifecycleOwner) {
            requestLocationLauncher?.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }

        viewModel.openAddCardEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_cardsFragment_to_add_card_nav_graph)
        }

        viewModel.requestCameraPermissionEvent.observe(viewLifecycleOwner) {
            requestCameraLauncher?.launch(Manifest.permission.CAMERA)
        }
    }

    private fun initChips() {
        binding.chipGroup.removeAllViews()
        viewModel.uiState.value.availableCategories.forEach {
            binding.chipGroup.addView(
                Chip(requireContext()).apply {
                    isCheckable = true
                    id = it.id
                    text = it.text
                }
            )
        }
    }

    private fun bindContentState(uiState: CardsScreenUIState) {
        adapter.items = uiState.cards
    }

    private fun showDialog(message: String) {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Доступ к геолокации")
        alertDialogBuilder
            .setMessage(message)
            .setPositiveButton("Хорошо") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", requireContext().packageName, null)
                intent.data = uri
                startActivity(intent)
            }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun bindErrorState(uiState: CardsScreenUIState) {
        binding.chipGroup.clearCheck()

        when (uiState.errorType) {
            is ErrorType.Camera -> bindLocationError(
                "Нет доступа к камере",
                "Для корректной работы приложения требуется доступ к камере.\nПожалуйста, предоставьте его на следующем экране.",
                Manifest.permission.CAMERA
            )
            is ErrorType.Location -> bindLocationError(
                "Нет доступа к локации",
                "Для корректной работы приложения требуется доступ к геолокации.\nПожалуйста, предоставьте его на следующем экране.",
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            null -> {}
        }
    }

    private fun bindLocationError(toastMessage: String, message: String, permission: String) {
        if (!shouldShowRequestPermissionRationale(permission)) {
            showDialog(message)
        } else {
            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initAdapter() {
        binding.recycler.adapter = adapter
    }
}