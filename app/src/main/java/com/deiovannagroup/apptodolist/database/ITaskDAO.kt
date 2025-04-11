package com.deiovannagroup.apptodolist.database

import com.deiovannagroup.apptodolist.models.Task

interface ITaskDAO {
    fun insertTask(task: Task): Boolean
    fun updateTask(task: Task): Boolean
    fun deleteTask(id: Int): Boolean
    fun getTasks(): List<Task>
}