package com.lloydsbankingassignment.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lloydsbankingassignment.R
import com.lloydsbankingassignment.databinding.FragmentHomeBinding
import com.lloydsbankingassignment.presentation.state.UiEffect
import com.lloydsbankingassignment.presentation.state.HomeUiEvent
import com.lloydsbankingassignment.util.Constants.GAME_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    private var binding: FragmentHomeBinding? = null

    private val FragmentHomeBinding.listAdapter: GameListAdapter
        get() = recycler.adapter as GameListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.initLayout()
        initObserver()
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.uiEffect.collectLatest { effect ->
                when (effect) {
                    is UiEffect.OnError -> {
                        Toast.makeText(context, effect.errorMsg, Toast.LENGTH_LONG).show()
                    }

                    is UiEffect.NavigateTo -> {
                        val bundle = Bundle()
                        bundle.putInt(GAME_ID, effect.id)
                        findNavController().navigate(R.id.detailsFragment, bundle)
                    }
                }
            }
        }
    }

    private fun FragmentHomeBinding.initLayout() {
        recycler.apply {
            adapter =
                GameListAdapter(
                    context,
                    onItemClick = { viewModel.onEvent(HomeUiEvent.OnNavigation(it)) })
        }
        lifecycleScope.launch {
            viewModel.gameState.collectLatest { state ->
                if (state.gameList?.isNotEmpty() == true) {
                    listAdapter.updateList(state.gameList)
                    progressBar.visibility = View.GONE
                } else if (state.loading) {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}