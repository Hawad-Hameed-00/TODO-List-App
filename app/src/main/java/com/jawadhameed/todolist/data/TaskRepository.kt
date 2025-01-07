package com.jawadhameed.todolist.data

import androidx.lifecycle.LiveData
import com.jawadhameed.todolist.data.local.TaskDao
import com.jawadhameed.todolist.data.local.TaskEntity

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun createTask(taskEntity: TaskEntity){
        taskDao.createTask(taskEntity)
    }

    suspend fun updateTask(taskEntity: TaskEntity){
        taskDao.updateTask(taskEntity)
    }


    suspend fun deleteTask(taskEntity: TaskEntity){
        taskDao.deleteTask(taskEntity)
    }



    val taskList: LiveData<List<TaskEntity>> = taskDao.getTaskList()


}