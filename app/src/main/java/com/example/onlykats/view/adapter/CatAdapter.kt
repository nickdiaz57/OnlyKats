package com.example.onlykats.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onlykats.databinding.CatItemBinding
import com.example.onlykats.model.Cat
import com.example.onlykats.util.loadWithGlide

/**
 * ListView - loads all objects into memory
 * RecyclerView - Leverages the ViewHolder Pattern to optimizing scrolling and memory consumption
 * ListAdapter - Same as Recyclerview but we don't have to use the notify methods to update the adapter
 */
class CatAdapter(
    private val catList: MutableList<Cat> = mutableListOf()
) : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CatViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.loadCat(catList[position])
    }

    override fun getItemCount() = catList.size

    fun updateList(cats: List<Cat>) {
        catList.addAll(cats)
        notifyItemRangeChanged(0, catList.size)
    }

    class CatViewHolder(private val binding: CatItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun loadCat(cat: Cat) = with(binding) {
            ivCat.loadWithGlide(cat.url)

            if (cat.breeds.isEmpty()) {
                breedDisplayBox.text = ""
            } else {
                breedDisplayBox.text = cat.breeds[0].name
            }
        }

        companion object {
            fun getInstance(parent: ViewGroup): CatViewHolder {
                val binding = CatItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return CatViewHolder(binding)
            }
        }
    }
}
