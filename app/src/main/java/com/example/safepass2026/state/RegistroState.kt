package com.example.safepass2026.state

import com.example.safepass2026.model.Asistente

sealed class RegistroState {
    object Idle : RegistroState()

    data class Success(
        val asistente: Asistente,
        val resumen: String
    ) : RegistroState()

    data class Error(val mensaje: String) : RegistroState()
}