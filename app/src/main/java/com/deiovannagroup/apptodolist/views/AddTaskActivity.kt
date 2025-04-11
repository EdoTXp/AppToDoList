package com.deiovannagroup.apptodolist.views

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.deiovannagroup.apptodolist.R
import com.deiovannagroup.apptodolist.database.TaskDAO
import com.deiovannagroup.apptodolist.databinding.ActivityAddTaskBinding
import com.deiovannagroup.apptodolist.models.Task

class AddTaskActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAddTaskBinding.inflate(layoutInflater)
    }
    private lateinit var taskDAO: TaskDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeLayout()
        taskDAO = TaskDAO(this)

        var task: Task? = null
        val bundle = intent.extras
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                task =
                    bundle.getSerializable(
                        getString(R.string.task_key),
                        Task::class.java,
                    ) as Task
            } else {
                @Suppress("DEPRECATION")
                task = bundle.getSerializable(getString(R.string.task_key)) as Task
            }
            binding.txtTitle.text = getString(R.string.update_task_text)
            binding.editTask.setText(task.description)
            binding.btnSave.text = getString(R.string.update_button_text)
        }

        with(binding) {
            btnSave.setOnClickListener {
                if (editTask.text.isNotEmpty()) {
                    if (task != null) {
                        editTask(task)
                    } else {
                        saveTask()
                    }
                } else {
                    Toast.makeText(
                        this@AddTaskActivity,
                        "Add task",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }

    private fun saveTask() {
        val task = Task(
            -1,
            binding.editTask.text.toString(),
            "Default",
        )

        if (taskDAO.insertTask(task)) {
            Toast.makeText(
                this,
                getString(R.string.task_added_toast_message),
                Toast.LENGTH_SHORT,
            ).show()

            finish()
        }
    }

    private fun editTask(task: Task) {
        val updatedTask = Task(
            task.id,
            binding.editTask.text.toString(),
            "Default",
        )
        if (taskDAO.updateTask(updatedTask)) {
            Toast.makeText(
                this,
                getString(R.string.task_updated_toast_message),
                Toast.LENGTH_SHORT,
            ).show()

            finish()
        }

    }

    private fun setEdgeToEdgeLayout() {
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom,
            )
            insets
        }
    }
}