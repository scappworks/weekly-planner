package com.scappworks.weeklyplanner.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scappworks.weeklyplanner.R
import com.scappworks.weeklyplanner.roomdb.tasktable.Task
import com.scappworks.weeklyplanner.roomdb.weekdaytable.Weekday

class TaskRvAdapter : ListAdapter<Task, TaskRvAdapter.TaskViewHolder>(TaskComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.task)
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val weekdayItemView: TextView = itemView.findViewById(R.id.weekday_rv_item)

        fun bind(text: String?) {
            weekdayItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): TaskViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.task_rv_item, parent, false)
                return TaskViewHolder(view)
            }
        }
    }

    class TaskComparator : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.task == newItem.task
        }
    }
}