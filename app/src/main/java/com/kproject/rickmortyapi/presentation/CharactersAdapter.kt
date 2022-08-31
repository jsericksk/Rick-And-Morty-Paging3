package com.kproject.rickmortyapi.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kproject.rickmortyapi.databinding.RecyclerviewCharacterItemBinding
import com.kproject.rickmortyapi.model.Character

class CharactersAdapter :
    PagingDataAdapter<Character, CharactersAdapter.CharactersViewHolder>(CHARACTERS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding = RecyclerviewCharacterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val characterItem = getItem(position)
        characterItem?.let { character ->
            holder.bind(character)
        }
    }

    inner class CharactersViewHolder(
        private val binding: RecyclerviewCharacterItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            with(binding) {
                tvName.text = character.name
                tvStatus.text = character.status
                tvSpecies.text = character.species
                ivImage.load(character.image)
            }
        }
    }

    companion object {
        private val CHARACTERS_COMPARATOR = object : DiffUtil.ItemCallback<Character>() {

            override fun areItemsTheSame(oldItem: Character, newItem: Character) =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character) =
                    oldItem == newItem
        }
    }
}