@file:Suppress("MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate"
)

package com.solanacode.challange8.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solanacode.challange8.databinding.ItemMovieBinding
import com.solanacode.challange8.model.ItemResponseItem


class MovieAdapter(val listener : Clicked):RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    val data = object : DiffUtil.ItemCallback<ItemResponseItem>(){
        override fun areItemsTheSame(
            oldItem: ItemResponseItem,
            newItem: ItemResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ItemResponseItem,
            newItem: ItemResponseItem
        ): Boolean {
           return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val util = AsyncListDiffer(this,data)

    fun submitData(list : List<ItemResponseItem>) = util.submitList(list)

    inner class MovieViewHolder(val binding : ItemMovieBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
       holder.binding.apply {
           textName.text = util.currentList[position].title
           tvDirector.text = util.currentList[position].director
           tvOriTitle.text = util.currentList[position].originalTitleRomanised
           tvRealaseDate.text = util.currentList[position].releaseDate
           Glide.with(root.context).load(util.currentList[position].image).into(imageMovie)
           cards.setOnClickListener {
               listener.onClick(util.currentList[position])
           }

       }
    }

    override fun getItemCount(): Int = util.currentList.size

    interface Clicked{
        fun onClick(itemResponseItem: ItemResponseItem)
    }
}