package com.deiovannagroup.apptodolist.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.deiovannagroup.apptodolist.R
import com.deiovannagroup.apptodolist.adapter.TaskAdapter
import com.deiovannagroup.apptodolist.database.TaskDAO
import com.deiovannagroup.apptodolist.databinding.ActivityMainBinding
import com.deiovannagroup.apptodolist.models.Task

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var taskDAO: TaskDAO
    private lateinit var taskAdapter: TaskAdapter
    private var tasks = emptyList<Task>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeLayout()

        with(binding) {
            fabAdd.setOnClickListener {
                val intent = Intent(
                    this@MainActivity,
                    AddTaskActivity::class.java,
                )

                startActivity(intent)
            }
            taskAdapter = TaskAdapter(
                { task -> editTask(task) },
                { id -> confirmExclusion(id) },
            )
            rvTasks.adapter = taskAdapter
            rvTasks.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onStart() {
        super.onStart()
        updateTasks()
    }

    private fun updateTasks() {
        taskDAO = TaskDAO(this)
        tasks = taskDAO.getTasks()
        taskAdapter.setTasks(tasks)
    }

    private fun editTask(task: Task) {
        val intent = Intent(
            this,
            AddTaskActivity::class.java,
        )

        intent.putExtra(
            getString(R.string.task_key),
            task,
        )
        startActivity(intent)
    }

    private fun confirmExclusion(id: Int) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.confirm_exclusion_title_alert_dialog))
            setMessage(getString(R.string.confirm_exclusion_message_alert_dialog))
            setPositiveButton(
                getString(R.string.yes_button_text),
            ) { _, _ ->
                if (taskDAO.deleteTask(id)) {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.success_on_delete_task_text),
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateTasks()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.error_on_delete_task_text),
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
            setNegativeButton(
                getString(R.string.no_button_text),
                null,
            )
        }
            .create()
            .show()
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