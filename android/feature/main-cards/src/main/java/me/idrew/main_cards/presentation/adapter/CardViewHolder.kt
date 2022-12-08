package me.idrew.main_cards.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.nsu.alphacontest.main_cards.databinding.ItemCardLayoutBinding

class CardViewHolder(
    private val binding: ItemCardLayoutBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CardListItem) {
        binding.title.text = item.title
        binding.description.text = item.number
    }
}