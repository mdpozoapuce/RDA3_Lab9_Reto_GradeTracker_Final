package com.example.myapplication.data.repository

import com.example.myapplication.domain.model.AcademicGrade
import com.example.myapplication.domain.repository.GradeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryGradeRepository : GradeRepository {

    // Flujo reactivo mutable encapsulado simulando la base de datos en memoria
    private val _grades = MutableStateFlow<List<AcademicGrade>>(emptyList())

    init {
        // Cargar inicialmente con al menos dos registros de ejemplo
        _grades.value = listOf(
            AcademicGrade(id = "1", activityName = "Práctica de Consolidación", subject = "Mecánica de Suelos", grade = 8.5),
            AcademicGrade(id = "2", activityName = "Examen Parcial", subject = "Resistencia de Materiales", grade = 9.0)
        )
    }

    override fun getAllGrades(): Flow<List<AcademicGrade>> {
        return _grades.asStateFlow()
    }

    override suspend fun addGrade(grade: AcademicGrade) {
        _grades.update { currentList ->
            currentList + grade
        }
    }
}