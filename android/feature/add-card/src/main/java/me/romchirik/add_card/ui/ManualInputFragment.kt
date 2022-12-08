package me.romchirik.add_card.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import me.romchirik.add_card.R
import me.romchirik.add_card.databinding.FragmentManualInputBinding
import me.romchirik.add_card.domain.model.BarcodeScanResult
import me.romchirik.add_card.presentation.ManualInputViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.nsu.alphacontest.model.CardCategory

class ManualInputFragment : Fragment(R.layout.fragment_manual_input) {

    private val binding by viewBinding(FragmentManualInputBinding::bind)

    private val viewModel by viewModel<ManualInputViewModel>()

    private val args by lazy { arguments?.getSerializable(MANUAL_INPUT_ARGS) as BarcodeScanResult }

    private val selectorAdapter by lazy {
        ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, listOf(
            CardCategory.Cafe,
            CardCategory.Restaurant,
            CardCategory.Pharmacy,
            CardCategory.Grocery,
            CardCategory.Optics,
            CardCategory.Clothing,
        ).map { it.localizedValue })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.let {
            when (it) {
                is BarcodeScanResult.Success -> autofillCardData(it)
                else -> return@let
            }
        }

        binding.categoryEditText.setAdapter(selectorAdapter)
        binding.submitButton.setOnClickListener {
            viewModel.onSubmit(
                number = binding.numberEditText.text.toString(),
                name = binding.nameEditText.text.toString(),
                category = binding.categoryEditText.text.toString()
            )
        }
    }

    private fun autofillCardData(result: BarcodeScanResult.Success) {
        binding.numberEditText.setText(result.barcodeRawData.orEmpty())
        binding.numberLayout.isEnabled = false
        binding.numberEditText.isEnabled = false
        viewModel.selectBarcodeType(result.type)

        Log.i("Barcode", "Barcode autofill: ${result.type.stringValue} ${result.barcodeRawData}")
    }

    companion object {
        const val MANUAL_INPUT_ARGS = "MANUAL_INPUT_ARGS"
    }
}