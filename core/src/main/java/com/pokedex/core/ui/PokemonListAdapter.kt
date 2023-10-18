package com.pokedex.core.ui

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.pokedex.core.R
import com.pokedex.core.databinding.RvPokemonBinding
import com.pokedex.core.domain.model.PokemonModel
import com.pokedex.core.utils.Utils.getPokemonIdFromUrl
import java.util.Locale

class PokemonListAdapter : RecyclerView.Adapter<PokemonListAdapter.ListViewHolder>(), Filterable {
    var listData = ArrayList<PokemonModel>()
    private var listFiltered = ArrayList<PokemonModel>()
    var onItemClick: ((PokemonModel) -> Unit)? = null


    fun addItem(item: PokemonModel) {
        val position = listData.size
        listData.add(position, item)
        notifyItemInserted(position)
    }

    fun setData(newListData: List<PokemonModel>?) {
        if (newListData == null) return
        listData.clear()
        listFiltered.clear()
        listData.addAll(newListData)
        listFiltered.addAll(listData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_pokemon, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RvPokemonBinding.bind(itemView)
        fun bind(data: PokemonModel) {
            with(binding) {
                tvName.text = data.name
                val pokemonId = getPokemonIdFromUrl(data.url)
                Glide.with(itemView.context)
                    .load(itemView.context.getString(R.string.base_url_image, pokemonId))
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.isVisible = false
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            dataSource: com.bumptech.glide.load.DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.isVisible = false
                            return false
                        }

                    })
                    .into(image)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[absoluteAdapterPosition])
            }
        }
    }

    override fun getFilter(): Filter {
        return contactFilter
    }

    private val contactFilter: Filter = object : Filter() {
        @SuppressLint("DefaultLocale")
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<PokemonModel> = java.util.ArrayList()
            if (constraint.isEmpty()) {
                filteredList.clear()
                filteredList.addAll(listFiltered)
            } else {
                filteredList.clear()
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in listFiltered) {
                    if (item.name?.lowercase()?.contains(filterPattern) == true) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            listData.clear()
            listData.addAll(results.values as java.util.ArrayList<PokemonModel>)
            notifyDataSetChanged()
        }
    }
}