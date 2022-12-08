package me.idrew.main_cards.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.alphacontest.main_cards.databinding.ItemCardLayoutBinding

class CardsAdapter : RecyclerView.Adapter<CardViewHolder>() {

    var items: List<CardListItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding =
            ItemCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}