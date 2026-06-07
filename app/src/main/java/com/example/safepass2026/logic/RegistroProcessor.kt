package com.example.safepass2026.logic

import com.example.safepass2026.extensions.esMayorDeEdad
import com.example.safepass2026.extensions.esNombreValido
import com.example.safepass2026.extensions.normalizarTipoEntrada
import com.example.safepass2026.model.Asistente
import com.example.safepass2026.state.RegistroState

fun aplicarBeneficio(
    asistente: Asistente,
    regla: (Asistente) -> String
): String = regla(asistente)

fun procesarRegistro(
    nombreInput: String?,
    edadInput: String?,
    tipoEntradaInput: String?,
    reglaPrioridad: (Asistente) -> String = { asistente ->
        when (asistente.tipoEntrada.uppercase()) {
            "VIP" -> "Ingreso prioritario habilitado"
            "RESERVA" -> "Descuento de reserva aplicado"
            else -> "Ingreso general"
        }
    }
): RegistroState {

    val nombre = nombreInput?.trim().orEmpty()

    if (!nombre.esNombreValido()) {
        return RegistroState.Error("El nombre no puede estar vacío.")
    }

    val edad = edadInput
        ?.trim()
        ?.takeIf { it.isNotEmpty() }
        ?.toIntOrNull()
        ?.let { valor ->
            if (valor.esMayorDeEdad()) {
                valor
            } else {
                return RegistroState.Error("El asistente debe ser mayor de edad.")
            }
        }
        ?: return RegistroState.Error("La edad es nula, vacía o inválida.")

    val asistente = Asistente(
        nombre = nombre,
        edad = edad,
        tipoEntrada = tipoEntradaInput?.normalizarTipoEntrada() ?: "General"
    ).apply {
        require(tipoEntrada.isNotBlank())
    }

    val beneficio = aplicarBeneficio(asistente, reglaPrioridad)

    val resumen = asistente.run {
        "Asistente: $nombre | Edad: ${edad ?: 0} | Entrada: $tipoEntrada | $beneficio"
    }

    return RegistroState.Success(asistente, resumen)
}