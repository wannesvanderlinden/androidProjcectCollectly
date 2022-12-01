package com.example.androidprojcectcollectly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidprojcectcollectly.entities.GameConsole
import com.example.androidprojcectcollectly.R


class GameConsoleListAdapter(val clicklistener:(String)->Unit) : ListAdapter<GameConsole, GameConsoleListAdapter.GameConsoleViewHolder>(
    GameConsolesComparator(),)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameConsoleViewHolder {
        return GameConsoleViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: GameConsoleViewHolder, position: Int) {
        val current = getItem(position)
        //Add click listener for getting value and go to the next page
        holder.itemView.setOnClickListener{
            clicklistener(getItem(position).name.toString())
        }
        holder.bind(current.name)
    }

    class GameConsoleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gameconsoleItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            gameconsoleItemView.text = text

        }

        companion object {
            fun create(parent: ViewGroup): GameConsoleViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)

                return GameConsoleViewHolder(view)
            }
        }


    }


    class GameConsolesComparator : DiffUtil.ItemCallback<GameConsole>() {
        override fun areItemsTheSame(oldItem: GameConsole, newItem: GameConsole): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GameConsole, newItem: GameConsole): Boolean {
            return oldItem.name == newItem.name
        }
    }

}