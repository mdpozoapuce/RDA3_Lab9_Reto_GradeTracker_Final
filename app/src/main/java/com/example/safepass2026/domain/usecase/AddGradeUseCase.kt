package com.example.safepass2026.domain.usecase

import com.example.safepass2026.domain.model.AcademicGrade
import com.example.safepass2026.domain.repository.GradeRepository
import java.util.UUID

class AddGradeUseCase(private val repository: GradeRepository) {
    suspend operator fun invoke(activityName: String, subject: String, grade: Double) {
        if (activityName.isBlank() || subject.isBlank()) {
            throw IllegalArgumentException("Los campos no pueden estar vacíos")
        }
        if (grade !in 0.0..10.0) {
            throw IllegalArgumentException("La nota debe estar estrictamente entre 0.0 y 10.0")
        }

        val newGrade = AcademicGrade(
            id = UUID.randomUUID().toString(),
            activityName = activityName,
            subject = subject,
            grade = grade
        )
        repository.addGrade(newGrade)
    }
}
