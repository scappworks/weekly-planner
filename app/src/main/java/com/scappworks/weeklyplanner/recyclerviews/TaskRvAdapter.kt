package com.scappworks.weeklyplanner.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scappworks.weeklyplanner.R
import com.scappworks.weeklyplanner.WeekdayActivity
import com.scappworks.weeklyplanner.roomdb.tasktable.Task

class TaskRvAdapter(private val activity: WeekdayActivity? ) : ListAdapter<Task, TaskRvAdapter.TaskViewHolder>(TaskComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val current = getItem(position)
        if (activity != null) {
            holder.bind(current.task, activity, current)
        } else {
            holder.bind(current.task, null, current)
        }
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskItemView: TextView = itemView.findViewById(R.id.task_rv_item)

        fun bind(text: String?, activity: WeekdayActivity?, currentTask: Task) {
            taskItemView.text = text

            if (activity != null) {
                itemView.setOnLongClickListener {
                    activity.deleteTask(currentTask)
                    return@setOnLongClickListener true
                }
            }
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