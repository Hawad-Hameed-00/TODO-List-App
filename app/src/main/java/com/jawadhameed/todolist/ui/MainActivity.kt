package com.jawadhameed.todolist.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.jawadhameed.todolist.R
import com.jawadhameed.todolist.data.TaskRepository
import com.jawadhameed.todolist.data.local.TaskDatabase
import com.jawadhameed.todolist.data.local.TaskEntity
import com.jawadhameed.todolist.databinding.ActivityMainBinding
import com.jawadhameed.todolist.ui.adapters.TaskAdapter
import com.jawadhameed.todolist.viewmodel.TaskViewModel
import com.jawadhameed.todolist.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskArrayList: List<TaskEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskRepository = TaskRepository(TaskDatabase.getInstance(applicationContext).taskDao())
        val taskViewModelFactory = TaskViewModelFactory(taskRepository)
        taskViewModel = ViewModelProvider(this, taskViewModelFactory)[TaskViewModel::class.java]


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        taskArrayList = emptyList()

        taskAdapter = TaskAdapter(
            taskArrayList, { task->
                taskViewModel.updateTask(task)
            },{ task ->
               showDeleteTaskDialog(task)
            }
        )

        binding.recyclerView.adapter = taskAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        binding.floatingActionButton.setOnClickListener {
            showAddTaskDialog()
        }



        taskViewModel.tasks.observe(this) { tasks ->
            taskAdapter.updateTasks(tasks)
        }
    }


    private fun showAddTaskDialog(){
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)

            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle("Add New Task")
                .setView(dialogView)
                .setPositiveButton("Save") { dialog, _ ->
                    val taskName = dialogView.findViewById<TextInputEditText>(R.id.dialog_edittext).text.toString()

                    if (taskName.isNotEmpty()) {
                        val taskEntity = TaskEntity(0, taskName, 0)
                        taskViewModel.createTask(taskEntity)
                    } else {
                        Toast.makeText(this, "Task name cannot be empty", Toast.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            dialog.show()

    }
    private fun showDeleteTaskDialog(taskEntity: TaskEntity) {
        MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle("Delete Task")
            .setMessage("Do you want to delete the task?")
            .setPositiveButton("Delete") { dialog, _ ->
                taskViewModel.deleteTask(taskEntity)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                // Dismiss the dialog
                dialog.dismiss()
            }
            .show()
    }
}