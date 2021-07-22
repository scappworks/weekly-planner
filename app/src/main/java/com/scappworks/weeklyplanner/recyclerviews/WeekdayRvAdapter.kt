package com.scappworks.weeklyplanner.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scappworks.weeklyplanner.R
import com.scappworks.weeklyplanner.roomdb.weekdaytable.Weekday

class WeekdayRvAdapter : ListAdapter<Weekday, WeekdayRvAdapter.WeekdayViewHolder>(WordsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekdayViewHolder {
        return WeekdayViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WeekdayViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.day)
    }

    class WeekdayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.weekday_rv)

        fun bind(text: String?) {
            wordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): WeekdayViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.weekday_rv_item, parent, false)
                return WeekdayViewHolder(view)
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Weekday>() {
        override fun areItemsTheSame(oldItem: Weekday, newItem: Weekday): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Weekday, newItem: Weekday): Boolean {
            return oldItem.day == newItem.day
        }
    }
}