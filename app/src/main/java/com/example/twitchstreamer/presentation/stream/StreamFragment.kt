package com.example.twitchstreamer.presentation.stream

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.twitchstreamer.R
import com.example.twitchstreamer.databinding.FragmentStreamBinding
import com.example.twitchstreamer.domain.stream.StreamStatus
import com.example.twitchstreamer.presentation.stream.engine.RootEncoderStreamingEngine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StreamFragment : Fragment() {

    private var _binding: FragmentStreamBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StreamViewModel by viewModels()

    @Inject
    lateinit var engine: RootEncoderStreamingEngine

    private val permissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val granted =
            result[Manifest.permission.CAMERA] == true && result[Manifest.permission.RECORD_AUDIO] == true
        if (granted) {
            startPreview()
        } else {
            Toast.makeText(
                requireContext(),
                R.string.camera_microphone_permissions,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStreamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        checkPermissionsAndMaybeStartPreview()

        observeUiState()
        observeNavigateToViewers()
    }

    private fun checkPermissionsAndMaybeStartPreview() {
        val hasCamera = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        val hasAudio = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        if (hasCamera && hasAudio) {
            startPreview()
        } else {
            permissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
                )
            )
        }
    }

    private fun startPreview() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.onStartPreview()
        }
    }

    private fun initViews() = with(binding) {
        engine.attachPreview(openGlView)

        btnStartStream.setOnClickListener {
            viewModel.onStartStopStreamClicked()
        }
        btnStopStream.setOnClickListener {
            viewModel.onStartStopStreamClicked()
        }

        val toggleMicOnClickListener = View.OnClickListener {
            it.isSelected = !it.isSelected
            viewModel.onToggleMicClicked()
        }
        btnToggleMicOn.setOnClickListener(toggleMicOnClickListener)
        btnToggleMicOff.setOnClickListener(toggleMicOnClickListener)

        btnSwitchCamera.setOnClickListener {
            viewModel.onSwitchCameraClicked()
        }

        btnViewers.setOnClickListener {
            viewModel.onViewersClicked()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        with(binding) {
                            val formattedStatus = when (state.status) {
                                StreamStatus.OFFLINE -> getString(R.string.status_offline)
                                StreamStatus.CONNECTING -> getString(R.string.status_connecting)
                                StreamStatus.ONLINE -> getString(R.string.status_online)
                                StreamStatus.RECONNECTING -> getString(R.string.status_reconnecting)
                                StreamStatus.ERROR -> getString(R.string.error_other)
                            }
                            tvStatus.text = "${getString(R.string.status)} $formattedStatus"

                            when (state.status) {
                                StreamStatus.ONLINE -> {
                                    tvDurations.visibility = View.VISIBLE
                                    tvDurations.text = state.formattedDuration
                                    btnStartStream.visibility = View.GONE
                                    btnStopStream.visibility = View.VISIBLE
                                }

                                StreamStatus.OFFLINE -> {
                                    tvDurations.visibility = View.GONE
                                    btnStartStream.visibility = View.VISIBLE
                                    btnStopStream.visibility = View.GONE
                                }

                                else -> {
                                    tvDurations.visibility = View.GONE
                                    btnStartStream.visibility = View.GONE
                                    btnStopStream.visibility = View.GONE
                                }
                            }

                            if (state.isMicEnabled) {
                                btnToggleMicOn.visibility = View.VISIBLE
                                btnToggleMicOff.visibility = View.GONE
                            } else {
                                btnToggleMicOn.visibility = View.GONE
                                btnToggleMicOff.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun observeNavigateToViewers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(State.STARTED) {
                viewModel.navigateToViewers.collectLatest {
                    findNavController().navigate(R.id.action_streamFragment_to_viewersFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        engine.detachPreview()
        _binding = null
        super.onDestroyView()
    }
}