package com.lloydsbankingassignment.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lloydsbankingassignment.databinding.LayoutGameItemBinding
import com.lloydsbankingassignment.domain.model.GameItem

class GameListAdapter(context: Context, private val onItemClick: (Int) -> Unit = {}) :
    ListAdapter<GameItem, RecyclerView.ViewHolder>(GameItemDiffCallback()) {

    private val inflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        GameItemHolder(LayoutGameItemBinding.inflate(inflater, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val element = getItem(position)
        when (holder) {
            is GameItemHolder -> holder.bind(element)
        }
    }

    fun updateList(gameList: List<GameItem>) {
        submitList(gameList)
    }

    inner class GameItemHolder(private val binding: LayoutGameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gameItem: GameItem) = with(binding) {
            gameTitle.text = gameItem.title
            gameDescription.text = gameItem.description
            gameImage.load(gameItem.thumbnail)
            root.setOnClickListener {
                onItemClick(gameItem.id)
            }
        }
    }

}

class GameItemDiffCallback : DiffUtil.ItemCallback<GameItem>() {
    override fun areItemsTheSame(oldItem: GameItem, newItem: GameItem) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: GameItem, newItem: GameItem) = oldItem.id == newItem.id
}