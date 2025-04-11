package com.deiovannagroup.apptodolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deiovannagroup.apptodolist.databinding.ItemTaskBinding
import com.deiovannagroup.apptodolist.models.Task


class TaskAdapter(
    val onClickEdit: (Task) -> Unit,
    val onClickRemove: (Int) -> Unit,
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var tasks: List<Task> = emptyList()

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemBinding: ItemTaskBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        private val binding = itemBinding

        fun bind(task: Task) {
            with(binding) {
                textDescription.text = task.description
                textCreationDate.text = task.creationDate
                btnEdit.setOnClickListener { onClickEdit(task) }
                btnRemove.setOnClickListener { onClickRemove(task.id) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(
            layoutInflater,
            parent,
            false,
        )

        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = tasks.size
}