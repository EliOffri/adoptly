package com.example.dogadoption.ui.reportstray

import android.Manifest
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.dogadoption.R
import com.example.dogadoption.databinding.FragmentReportStrayBinding
import com.example.dogadoption.util.PermissionUtils
import com.example.dogadoption.util.Resource
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class ReportStrayFragment : Fragment() {

    private var _binding: FragmentReportStrayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportStrayViewModel by viewModels()
    private var pendingPhotoUri: Uri? = null

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            launchCamera()
        } else {
            showPermissionDeniedSnackbar(getString(R.string.camera_permission_denied))
        }
    }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            fetchLocation()
        } else {
            showPermissionDeniedSnackbar(getString(R.string.location_permission_denied))
        }
    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            pendingPhotoUri?.let { uri -> viewModel.setPhotoUri(uri) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportStrayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

        setupSubmitButtonState()
        binding.buttonTakePhoto.setOnClickListener { onTakePhotoClicked() }
        binding.buttonGetLocation.setOnClickListener { onGetLocationClicked() }
        binding.buttonLocationSet.setOnClickListener { onGetLocationClicked() }
        binding.buttonSubmitReport.setOnClickListener {
            val description = binding.editTextDescription.text?.toString()?.trim() ?: ""
            viewModel.submitReport(description)
        }
        binding.buttonSaveDraft.setOnClickListener {
            Snackbar.make(binding.root, getString(R.string.draft_saved), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setupSubmitButtonState() {
        binding.buttonSubmitReport.isEnabled = false
        binding.editTextDescription.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) { updateSubmitButton() }
        })
        viewModel.photoUri.observe(viewLifecycleOwner) { updateSubmitButton() }
        viewModel.location.observe(viewLifecycleOwner) { updateSubmitButton() }
    }

    private fun updateSubmitButton() {
        val description = binding.editTextDescription.text?.toString()?.trim() ?: ""
        val hasPhoto = viewModel.photoUri.value != null
        val hasLocation = viewModel.location.value != null
        binding.buttonSubmitReport.isEnabled = description.isNotEmpty() && hasPhoto && hasLocation
    }

    private fun observeViewModel() {
        viewModel.photoUri.observe(viewLifecycleOwner) { uri ->
            if (uri != null) {
                binding.photoZonePlaceholder.visibility = View.INVISIBLE
                binding.imagePhotoPreview.visibility = View.VISIBLE
                Glide.with(this)
                    .load(uri)
                    .centerCrop()
                    .into(binding.imagePhotoPreview)
                setDotActive(binding.viewStatusDotPhoto)
                binding.textPhotoStatus.text = getString(R.string.photo_attached)
            } else {
                binding.photoZonePlaceholder.visibility = View.VISIBLE
                binding.imagePhotoPreview.visibility = View.INVISIBLE
                setDotInactive(binding.viewStatusDotPhoto)
                binding.textPhotoStatus.text = getString(R.string.photo_not_attached)
            }
        }

        viewModel.location.observe(viewLifecycleOwner) { location ->
            if (location != null) {
                binding.buttonLocationSet.setBackgroundResource(R.drawable.bg_location_button_active)
                setDotActive(binding.viewStatusDotLocation)
                binding.textLocationStatus.text = getString(R.string.location_format, location.first, location.second)
            } else {
                binding.buttonLocationSet.setBackgroundResource(R.drawable.bg_location_button)
                setDotInactive(binding.viewStatusDotLocation)
                binding.textLocationStatus.text = getString(R.string.location_not_attached)
            }
        }

        viewModel.submissionState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.buttonSubmitReport.isEnabled = false
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Snackbar.make(binding.root, getString(R.string.report_success), Snackbar.LENGTH_LONG).show()
                    resetForm()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    updateSubmitButton()
                    val message = when (resource.message) {
                        "description" -> getString(R.string.error_description_empty)
                        "photo" -> getString(R.string.error_photo_required)
                        "location" -> getString(R.string.error_location_required)
                        else -> getString(R.string.error_loading_data)
                    }
                    Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setDotActive(dot: View) {
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(ContextCompat.getColor(requireContext(), R.color.user_added_badge))
        }
        dot.background = drawable
    }

    private fun setDotInactive(dot: View) {
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(ContextCompat.getColor(requireContext(), R.color.border))
        }
        dot.background = drawable
    }

    private fun onTakePhotoClicked() {
        when {
            PermissionUtils.hasCameraPermission(requireContext()) -> launchCamera()
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showRationaleDialog(getString(R.string.camera_rationale)) {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
            else -> cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun onGetLocationClicked() {
        when {
            PermissionUtils.hasLocationPermission(requireContext()) -> fetchLocation()
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                showRationaleDialog(getString(R.string.location_rationale)) {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
            else -> locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun launchCamera() {
        val photoFile = createImageFile()
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            photoFile
        )
        pendingPhotoUri = uri
        takePictureLauncher.launch(uri)
    }

    private fun fetchLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        lifecycleScope.launch {
            try {
                val location = fusedLocationClient.lastLocation.await()
                if (location != null) {
                    viewModel.setLocation(location.latitude, location.longitude)
                } else {
                    Snackbar.make(binding.root, getString(R.string.location_not_attached), Snackbar.LENGTH_LONG).show()
                }
            } catch (e: SecurityException) {
                showPermissionDeniedSnackbar(getString(R.string.location_permission_denied))
            }
        }
    }

    private fun createImageFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
    }

    private fun showRationaleDialog(message: String, onConfirm: () -> Unit) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(message)
            .setPositiveButton(getString(R.string.confirm)) { _, _ -> onConfirm() }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showPermissionDeniedSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.open_settings)) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", requireContext().packageName, null)
                }
                startActivity(intent)
            }
            .show()
    }

    private fun resetForm() {
        binding.editTextDescription.text?.clear()
        viewModel.clearState()
        updateSubmitButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
