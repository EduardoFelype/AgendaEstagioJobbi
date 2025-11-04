package com.agenda.estagio.ui.screens.aluno

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.agenda.estagio.data.models.Candidatura
import com.agenda.estagio.data.models.CandidaturaStatus
import com.agenda.estagio.data.viewmodel.CandidaturaViewModel
import com.agenda.estagio.navigation.Routes
import com.agenda.estagio.ui.theme.DarkCard
import com.agenda.estagio.ui.theme.PrimaryBlue
import com.agenda.estagio.ui.theme.SuccessGreen
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlunoCandidaturasScreen(
    navController: NavController,
    candidaturaViewModel: CandidaturaViewModel = viewModel()
) {
    val minhasCandidaturas by candidaturaViewModel.minhasCandidaturas.collectAsState()
    
    LaunchedEffect(Unit) {
        candidaturaViewModel.carregarMinhasCandidaturas()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minhas Candidaturas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkCard
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = DarkCard
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, "Home") },
                    label = { Text("Início") },
                    selected = false,
                    onClick = { navController.navigate(Routes.AlunoHome.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Work, "Vagas") },
                    label = { Text("Vagas") },
                    selected = false,
                    onClick = { navController.navigate(Routes.AlunoVagas.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Bookmark, "Candidaturas") },
                    label = { Text("Minhas") },
                    selected = true,
                    onClick = { }
                )
            }
        }
    ) { padding ->
        if (minhasCandidaturas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.WorkOff,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Nenhuma candidatura ainda",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Explore as vagas disponíveis e candidate-se",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Button(
                        onClick = { navController.navigate(Routes.AlunoHome.route) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        )
                    ) {
                        Text("Ver Vagas")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "Total: ${minhasCandidaturas.size} candidaturas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                item { Spacer(modifier = Modifier.height(8.dp)) }
                
                items(minhasCandidaturas) { candidatura ->
                    CandidaturaCard(
                        candidatura = candidatura,
                        onCancelar = {
                            candidaturaViewModel.cancelarCandidatura(candidatura.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CandidaturaCard(
    candidatura: Candidatura,
    onCancelar: () -> Unit
) {
    var showCancelDialog by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = DarkCard
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = candidatura.vagaTitulo,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = candidatura.empresaNome,
                        style = MaterialTheme.typography.bodyMedium,
                        color = PrimaryBlue
                    )
                }
                
                StatusChip(status = candidatura.status)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Divider()
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Enviada em",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = formatDate(candidatura.criadoEm),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                if (candidatura.status == CandidaturaStatus.PENDENTE || 
                    candidatura.status == CandidaturaStatus.EM_ANALISE) {
                    TextButton(
                        onClick = { showCancelDialog = true }
                    ) {
                        Text("Cancelar", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
    
    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Cancelar Candidatura") },
            text = { Text("Tem certeza que deseja cancelar esta candidatura?") },
            confirmButton = {
                Button(
                    onClick = {
                        onCancelar()
                        showCancelDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Cancelar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) {
                    Text("Voltar")
                }
            }
        )
    }
}

@Composable
fun StatusChip(status: CandidaturaStatus) {
    val (text, color) = when (status) {
        CandidaturaStatus.PENDENTE -> "Pendente" to MaterialTheme.colorScheme.primary
        CandidaturaStatus.EM_ANALISE -> "Em Análise" to PrimaryBlue
        CandidaturaStatus.APROVADA -> "Aprovada" to SuccessGreen
        CandidaturaStatus.REJEITADA -> "Rejeitada" to MaterialTheme.colorScheme.error
        CandidaturaStatus.CANCELADA -> "Cancelada" to MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    AssistChip(
        onClick = { },
        label = { Text(text) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = color.copy(alpha = 0.2f),
            labelColor = color
        )
    )
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
