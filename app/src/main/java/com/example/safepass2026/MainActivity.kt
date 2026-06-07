package com.example.safepass2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.safepass2026.data.repository.InMemoryTaskRepository
import com.example.safepass2026.domain.usecase.AddTaskUseCase
import com.example.safepass2026.domain.usecase.GetTasksUseCase
import com.example.safepass2026.ui.presentation.tasks.AcademicTaskApp
import com.example.safepass2026.ui.presentation.tasks.AcademicTaskViewModel
import com.example.safepass2026.ui.presentation.tasks.AcademicTaskViewModelFactory
import com.example.safepass2026.ui.theme.SafePass2026Theme

class MainActivity : ComponentActivity() {

    private val repository by lazy { InMemoryTaskRepository() }
    private val getTasksUseCase by lazy { GetTasksUseCase(repository) }
    private val addTaskUseCase by lazy { AddTaskUseCase(repository) }

    private val viewModel: AcademicTaskViewModel by viewModels {
        AcademicTaskViewModelFactory(
            getTasksUseCase,
            addTaskUseCase,
            repository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SafePass2026Theme {
                AcademicTaskApp(viewModel = viewModel)
            }
        }
    }
}