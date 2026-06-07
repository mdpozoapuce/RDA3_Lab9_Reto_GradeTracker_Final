package com.example.safepass2026.extensions

fun String.esNombreValido(): Boolean = this.trim().isNotEmpty()

fun Int.esMayorDeEdad(): Boolean = this >= 18

fun String.normalizarTipoEntrada(): String =
    this.trim().takeIf { it.isNotEmpty() } ?: "General"