package com.deiovannagroup.apptodolist.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.deiovannagroup.apptodolist.models.Task

class TaskDAO(context: Context) : ITaskDAO {

    private val writable = DatabaseHelper(context).writableDatabase
    private val readable = DatabaseHelper(context).readableDatabase

    override fun insertTask(tarefa: Task): Boolean {
        val values = ContentValues().apply {
            put(DatabaseHelper.DESCRIPTION, tarefa.description)
        }

        try {
            writable.insert(
                DatabaseHelper.TASK_TABLE,
                null,
                values
            )
            Log.i("info_db", "Success on insert a task")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Error on insert a task ${e.message}")
            return false
        }
        return true
    }

    override fun updateTask(tarefa: Task): Boolean {
        val values = ContentValues().apply {
            put(DatabaseHelper.DESCRIPTION, tarefa.description)
        }
        val args = arrayOf(tarefa.id.toString())

        try {
            writable.update(
                DatabaseHelper.TASK_TABLE,
                values,
                "${DatabaseHelper.TASK_ID} = ?",
                args
            )
            Log.i("info_db", "Success on update a task")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Error on update a task ${e.message}")
            return false
        }
        return true
    }

    override fun deleteTask(taskId: Int): Boolean {
        val args = arrayOf(taskId.toString())

        try {
            writable.delete(
                DatabaseHelper.TASK_TABLE,
                "${DatabaseHelper.TASK_ID} = ?",
                args
            )
            Log.i("info_db", "Success on delete a task")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("info_db", "Error on delete a task ${e.message}")
            return false
        }
        return true
    }

    override fun getTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val sql = "SELECT " +
                "${DatabaseHelper.TASK_ID}, " +
                "${DatabaseHelper.DESCRIPTION}, " +
                "strftime('%d/%m/%Y %H:%M', ${DatabaseHelper.CREATION_DATE}) " +
                "${DatabaseHelper.CREATION_DATE} " +
                "FROM ${DatabaseHelper.TASK_TABLE};"


        val cursor = readable.rawQuery(
            sql,
            null,
        )

        val taskIdIndex = cursor.getColumnIndex(DatabaseHelper.TASK_ID)
        val descriptionIndex = cursor.getColumnIndex(DatabaseHelper.DESCRIPTION)
        val creationDateIndex = cursor.getColumnIndex(DatabaseHelper.CREATION_DATE)

        while (cursor.moveToNext()) {
            val taskId = cursor.getInt(taskIdIndex)
            val description = cursor.getString(descriptionIndex)
            val creationDate = cursor.getString(creationDateIndex)

            tasks.add(
                Task(
                    taskId,
                    description,
                    creationDate,
                )
            )

            if (cursor.isLast) {
                cursor.close()
            }
        }

        return tasks
    }

}