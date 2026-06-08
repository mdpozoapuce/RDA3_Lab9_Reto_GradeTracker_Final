package com.example.safepass2026.ui.presentation.grades

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun GradeAppScreen(viewModel: GradeViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    when (currentScreen) {
        GradeScreenRoute.LIST -> GradeListScreen(viewModel)
        GradeScreenRoute.FORM -> GradeFormScreen(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeListScreen(viewModel: GradeViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.navigateTo(GradeScreenRoute.FORM) }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Calificación")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (val state = uiState) {
                is GradeUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is GradeUiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error: ${state.message}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(onClick = { viewModel.clearError() }) {
                            Text("Reintentar")
                        }
                    }
                }
                is GradeUiState.Success -> {
                    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Promedio General Acumulado", style = MaterialTheme.typography.titleMedium)
                                Text(
                                    text = String.format(Locale.getDefault(), "%.2f", state.average),
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        }

                        LazyColumn {
                            items(state.grades) { grade ->
                                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(grade.activityName, style = MaterialTheme.typography.titleMedium)
                                        Text(grade.subject, style = MaterialTheme.typography.bodyMedium)
                                        Text("Nota: ${grade.grade}", style = MaterialTheme.typography.bodyLarge)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradeFormScreen(viewModel: GradeViewModel) {
    var activityName by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var gradeText by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp).fillMaxSize()) {
            Text("Añadir Nueva Calificación", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 16.dp))

            OutlinedTextField(
                value = activityName,
                onValueChange = { activityName = it },
                label = { Text("Nombre de la actividad") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Asignatura") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = gradeText,
                onValueChange = { gradeText = it },
                label = { Text("Nota (0.0 - 10.0)") },
                modifier = Modifier.fillMaxWidth()
            )

            if (uiState is GradeUiState.Error) {
                Text(
                    text = (uiState as GradeUiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val isFormValid = activityName.isNotBlank() && subject.isNotBlank() && gradeText.isNotBlank()

            Row {
                Button(
                    onClick = { viewModel.addGrade(activityName, subject, gradeText) },
                    enabled = isFormValid,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Registrar")
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(
                    onClick = {
                        viewModel.clearError()
                        viewModel.navigateTo(GradeScreenRoute.LIST)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}
