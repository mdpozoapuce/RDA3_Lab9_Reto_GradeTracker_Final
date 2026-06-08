package com.example.safepass2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.safepass2026.data.repository.InMemoryGradeRepository
import com.example.safepass2026.domain.usecase.AddGradeUseCase
import com.example.safepass2026.domain.usecase.GetGradesUseCase
import com.example.safepass2026.ui.presentation.grades.GradeAppScreen
import com.example.safepass2026.ui.presentation.grades.GradeViewModel
import com.example.safepass2026.ui.theme.SafePass2026Theme

class MainActivity : ComponentActivity() {

    private val viewModel: GradeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = InMemoryGradeRepository()
                val getGradesUseCase = GetGradesUseCase(repository)
                val addGradeUseCase = AddGradeUseCase(repository)
                return GradeViewModel(getGradesUseCase, addGradeUseCase) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SafePass2026Theme {
                GradeAppScreen(viewModel = viewModel)
            }
        }
    }
}