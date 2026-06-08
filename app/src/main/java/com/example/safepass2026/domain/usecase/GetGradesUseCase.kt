package com.example.safepass2026.domain.usecase

import com.example.safepass2026.domain.model.AcademicGrade
import com.example.safepass2026.domain.repository.GradeRepository
import kotlinx.coroutines.flow.Flow

class GetGradesUseCase(private val repository: GradeRepository) {
    operator fun invoke(): Flow<List<AcademicGrade>> {
        return repository.getAllGrades()
    }
}
