package com.example.twitchstreamer.presentation.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.twitchstreamer.R
import com.example.twitchstreamer.databinding.FragmentConfigBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConfigFragment : Fragment() {

    private var _binding: FragmentConfigBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ConfigViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfigBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        observeConfigUiState()
        observeErrorType()
        observeNavigateToMain()
    }

    private fun initViews() = with(binding) {
        etStreamKey.addTextChangedListener { text ->
            viewModel.onStreamKeyChanged(text?.toString().orEmpty())
        }

        btnSave.setOnClickListener {
            viewModel.onSaveClicked()
        }
    }

    private fun observeConfigUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(State.STARTED) {
                viewModel.state.collect { state ->
                    val currentText = binding.etStreamKey.text.toString()
                    if (currentText != state.streamKey) {
                        binding.etStreamKey.setText(state.streamKey)
                        binding.etStreamKey.setSelection(state.streamKey.length)
                    }

                    binding.btnSave.isEnabled = state.isSaveEnabled && !state.isLoading
                    binding.progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun observeErrorType() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(State.STARTED) {
                viewModel.errorType
                    .filterNotNull()
                    .collect { type ->
                        Toast.makeText(
                            /* context = */ requireContext(),
                            /* resId = */ when (type) {
                                ErrorType.EMPTY_INPUT -> R.string.error_empty_input
                                ErrorType.OTHER -> R.string.error_other
                            },
                            /* duration = */ Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }

    private fun observeNavigateToMain() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(State.STARTED) {
                viewModel.navigateToMain.collectLatest {
                    findNavController().navigate(R.id.action_configFragment_to_streamFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}