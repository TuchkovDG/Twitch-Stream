package com.example.twitchstreamer.presentation.viewers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitchstreamer.databinding.FragmentViewersBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ViewersFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentViewersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewersViewModel by viewModels()

    private val adapter = ViewerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        observeUiState()
        observeOneShotErrors()
    }

    private fun initViews() = with(binding) {
        etFilter.addTextChangedListener { text ->
            viewModel.onFilterChanged(text?.toString().orEmpty())
        }

        rvViewers.layoutManager = LinearLayoutManager(requireContext())
        rvViewers.adapter = adapter
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when {
                        state.isLoading -> {
                            binding.progress.visibility = View.VISIBLE
                            binding.etFilter.visibility = View.GONE
                            binding.tvError.visibility = View.GONE
                            binding.tvEmptyList.visibility = View.GONE
                            binding.rvViewers.visibility = View.GONE
                        }

                        state.viewers.isEmpty() -> {
                            binding.progress.visibility = View.GONE
                            binding.etFilter.visibility = View.VISIBLE
                            binding.tvError.visibility = View.GONE
                            binding.tvEmptyList.visibility = View.VISIBLE
                            binding.rvViewers.visibility = View.GONE
                        }

                        else -> {
                            binding.progress.visibility = View.GONE
                            binding.etFilter.visibility = View.VISIBLE
                            binding.tvError.visibility = View.GONE
                            binding.tvEmptyList.visibility = View.GONE
                            binding.rvViewers.visibility = View.VISIBLE
                        }
                    }

                    adapter.submitList(state.viewers)
                }
            }
        }
    }

    private fun observeOneShotErrors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.oneShotError.collect { message ->
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}