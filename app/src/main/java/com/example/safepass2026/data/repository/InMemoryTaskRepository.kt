package com.example.safepass2026.data.repository

import com.example.safepass2026.domain.model.AcademicTask
import com.example.safepass2026.domain.repository.AcademicTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

class InMemoryTaskRepository : AcademicTaskRepository {

    private val tasksFlow = MutableStateFlow(
        listOf(
            AcademicTask(
                id = UUID.randomUUID().toString(),
                title = "Estudiar Patrón MVVM",
                isCompleted = false
            ),
            AcademicTask(
                id = UUID.randomUUID().toString(),
                title = "Analizar principios de Clean Architecture",
                isCompleted = true
            )
        )
    )

    override fun getTasksStream(): Flow<List<AcademicTask>> = tasksFlow

    override suspend fun addTask(title: String) {
        val newTask = AcademicTask(
            id = UUID.randomUUID().toString(),
            title = title,
            isCompleted = false
        )
        tasksFlow.value = tasksFlow.value + newTask
    }

    override suspend fun toggleTaskCompletion(taskId: String) {
        tasksFlow.value = tasksFlow.value.map { task ->
            if (task.id == taskId) {
                task.copy(isCompleted = !task.isCompleted)
            } else {
                task
            }
        }
    }
}