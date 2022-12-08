package me.romchirik.add_card.ui

import android.os.Bundle
import android.util.Size
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.common.util.concurrent.ListenableFuture
import me.romchirik.add_card.R
import me.romchirik.add_card.databinding.FragmentBarcodeCameraBinding
import me.romchirik.add_card.domain.analyzer.BarcodeAnalyzer
import me.romchirik.add_card.domain.model.BarcodeScanResult
import me.romchirik.add_card.presentation.BarcodeCameraUiState
import me.romchirik.add_card.presentation.BarcodeCameraViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.Executors


class BarcodeCameraFragment : Fragment(R.layout.fragment_barcode_camera) {

    private val binding by viewBinding(FragmentBarcodeCameraBinding::bind)

    private val viewModel by viewModel<BarcodeCameraViewModel>()

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            this.view?.let {
                val cameraProvider = cameraProviderFuture.get()
                bindPreview(cameraProvider)
            }
        }, ContextCompat.getMainExecutor(requireContext()))

        viewModel.uiState.observe(viewLifecycleOwner, ::obtainUiState)
        binding.toManualInput.setOnClickListener {
            viewModel.onManualInputRequested()
        }
    }

    private fun obtainUiState(uiState: BarcodeCameraUiState) {
        when (uiState) {
            is BarcodeCameraUiState.ScanResultReceived -> {
                setResultAndLeave(uiState.scanResult)
            }
            BarcodeCameraUiState.Scanning -> return
        }
    }


    private fun setResultAndLeave(result: BarcodeScanResult) {
        findNavController().navigate(
            R.id.action_barcodeCameraFragment_to_manualInputFragment,
            bundleOf(
                ManualInputFragment.MANUAL_INPUT_ARGS to result
            )
        )
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview: Preview = Preview.Builder().build()

        val cameraSelector: CameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        preview.setSurfaceProvider(binding.previewView.surfaceProvider)

        val imageAnalysis =
            ImageAnalysis.Builder().setTargetResolution(Size(TARGET_RES_WIDTH, TARGET_RES_HEIGHT))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build()

        imageAnalysis.setAnalyzer(
            Executors.newSingleThreadExecutor(), BarcodeAnalyzer(
                viewModel::onRecognitionSuccess,
                viewModel::onRecognitionFailure
            )
        )

        cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, preview, imageAnalysis)
    }

    companion object {
        private const val TARGET_RES_HEIGHT = 720
        private const val TARGET_RES_WIDTH = 1280

        const val REQUEST_KEY = "BARCODE_REQUEST"
        const val RESULT_KEY = "BARCODE_RESULT"
    }
}