package com.example.safepass2026.domain.repository

import com.example.safepass2026.domain.model.AcademicGrade
import kotlinx.coroutines.flow.Flow

interface GradeRepository {
    fun getAllGrades(): Flow<List<AcademicGrade>>
    suspend fun addGrade(grade: AcademicGrade)
}
