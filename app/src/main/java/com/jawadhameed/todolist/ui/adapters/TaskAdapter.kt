package com.jawadhameed.todolist.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jawadhameed.todolist.R
import com.jawadhameed.todolist.data.local.TaskEntity

class TaskAdapter(
    private var taskArrayList: List<TaskEntity>,
    private var isCheckBoxChanged: (TaskEntity) -> Unit,
    private var longClicked: (TaskEntity) -> Unit
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_list_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskArrayList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskArrayList[position]
        holder.taskName.apply {
            text = task.taskName
            paintFlags = if (task.isChecked == 1) {
                paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }


        holder.checkBox.isChecked = task.isChecked == 1

        holder.checkBox.setOnCheckedChangeListener(null)

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            val updatedTask = task.copy(isChecked = if (isChecked) 1 else 0)
            isCheckBoxChanged(updatedTask)
        }

        holder.itemView.setOnLongClickListener {
            longClicked(task)
            true
        }


    }

    inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val taskName = itemView.findViewById<TextView>(R.id.task_text)
        val checkBox = itemView.findViewById<CheckBox>(R.id.checkbox)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTasks(newTasks: List<TaskEntity>) {
        taskArrayList = newTasks
        notifyDataSetChanged()
    }
}