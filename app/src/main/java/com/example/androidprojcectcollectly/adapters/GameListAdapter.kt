package com.example.androidprojcectcollectly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidprojcectcollectly.R
import com.example.androidprojcectcollectly.entities.Game
import com.example.androidprojcectcollectly.entities.GameConsole


class GameListAdapter: ListAdapter<Game, GameListAdapter.GameViewHolder>(
    GameComparator(),) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val current = getItem(position)

        holder.bind(current.title)
    }

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gameItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            gameItemView.text = text

        }

        companion object {
            fun create(parent: ViewGroup): GameViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)

                return GameViewHolder(view)
            }
        }


    }


    class GameComparator : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.title == newItem.title
        }

    }
}