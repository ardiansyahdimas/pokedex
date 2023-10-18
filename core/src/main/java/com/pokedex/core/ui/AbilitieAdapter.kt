package com.pokedex.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pokedex.core.R
import com.pokedex.core.databinding.RvAbilitieBinding

class AbilitieAdapter : RecyclerView.Adapter<AbilitieAdapter.ListViewHolder>() {
    private var listName = ArrayList<String>()

    fun setData(newListName: List<String>?) {
        if (newListName == null) return
        listName.clear()
        listName.addAll(newListName)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_abilitie, parent, false))

    override fun getItemCount() = listName.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val name = listName[position]
        holder.bind(name)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RvAbilitieBinding.bind(itemView)
        fun bind(name: String) {
            with(binding) {
                tvName.text = name
            }
        }
    }
}