package com.jawadhameed.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jawadhameed.todolist.data.TaskRepository
import com.jawadhameed.todolist.data.local.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    val tasks: LiveData<List<TaskEntity>> = taskRepository.taskList

    fun createTask(taskEntity: TaskEntity){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.createTask(taskEntity)
        }
    }

    fun updateTask(taskEntity: TaskEntity){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.updateTask(taskEntity)
        }
    }


    fun deleteTask(taskEntity: TaskEntity){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteTask(taskEntity)
        }
    }


}