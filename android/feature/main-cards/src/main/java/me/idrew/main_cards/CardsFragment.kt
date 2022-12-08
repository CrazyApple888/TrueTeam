package me.idrew.main_cards

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.chip.Chip
import me.idrew.main_cards.presentation.CardsScreenUIState
import me.idrew.main_cards.presentation.UIState
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

    private var requestLocationLauncher: ActivityResultLauncher<Array<String>>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requestLocationLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            viewModel::onPermissionResult
        )
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
                    is UIState.Content -> bindContentState(it)
                    is UIState.Error -> bindErrorState(it)
                    is UIState.ChipInit -> initChips(it)
                    is UIState.Initial -> {}
                }
            }
        }

        viewModel.requestPermissionEvent.observe(viewLifecycleOwner) {
            if (!it) return@observe

            requestLocationLauncher?.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
    }

    private fun initChips(uiState: CardsScreenUIState) {
        uiState.availableCategories.forEach {
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

    private fun showDialog() {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Доступ к геолокации")
        alertDialogBuilder
            .setMessage(
                "Для корректной работы приложения требуется доступ к геолокации.\nПожалуйста, разрешите его на следующем экране."
            )
            .setCancelable(true)
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
        if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            showDialog()
        } else {
            Toast.makeText(requireContext(), "Нет доступа к локации", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initAdapter() {
        binding.recycler.adapter = adapter
    }
}