package com.deiovannagroup.apptodolist.models

import java.io.Serializable

data class Task(
    val id: Int,
    val description: String,
    val creationDate: String,
) : Serializable
