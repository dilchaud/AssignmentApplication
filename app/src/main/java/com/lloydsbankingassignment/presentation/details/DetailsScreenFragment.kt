package com.lloydsbankingassignment.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.lloydsbankingassignment.databinding.FragmentGameDetailsBinding
import com.lloydsbankingassignment.presentation.state.DetailsUiEvent
import com.lloydsbankingassignment.util.Constants.GAME_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsScreenFragment : Fragment() {

    private var binding: FragmentGameDetailsBinding? = null
    private val viewModel by viewModels<DetailsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGameDetailsBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.initLayout()
        arguments?.getInt(GAME_ID)?.let { DetailsUiEvent.GetDetails(it) }
            ?.let { viewModel.onEvent(it) }
    }

    private fun FragmentGameDetailsBinding.initLayout() {
        lifecycleScope.launch {
            viewModel.gameState.collectLatest { state ->
                if (state.gameItem != null) {
                    state.gameItem.run {
                        gameTitle.text = title
                        gameDescription.text = description
                        gameImage.load(thumbnail)
                    }

                    progressBar.visibility = View.GONE
                } else if (state.loading) {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

}