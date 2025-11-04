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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.agenda.estagio.data.models.Candidatura
import com.agenda.estagio.data.models.Vaga
import com.agenda.estagio.data.viewmodel.AuthViewModel
import com.agenda.estagio.data.viewmodel.CandidaturaViewModel
import com.agenda.estagio.data.viewmodel.CandidaturasState
import com.agenda.estagio.data.viewmodel.VagaViewModel
import com.agenda.estagio.navigation.Routes
import com.agenda.estagio.ui.theme.DarkCard
import com.agenda.estagio.ui.theme.PrimaryBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlunoHomeScreen(
    navController: NavController,
    vagaViewModel: VagaViewModel = viewModel(),
    candidaturaViewModel: CandidaturaViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val vagas by vagaViewModel.vagas.collectAsState()
    val minhasCandidaturas by candidaturaViewModel.minhasCandidaturas.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()
    val candidaturasState by candidaturaViewModel.candidaturasState.collectAsState()
    
    var searchText by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    
    val snackbarHostState = remember { SnackbarHostState() }
    
    LaunchedEffect(Unit) {
        vagaViewModel.carregarVagasAtivas()
        candidaturaViewModel.carregarMinhasCandidaturas()
    }
    
    LaunchedEffect(candidaturasState) {
        when (candidaturasState) {
            is CandidaturasState.Success -> {
                snackbarMessage = (candidaturasState as CandidaturasState.Success).message
                showSnackbar = true
                candidaturaViewModel.resetState()
            }
            is CandidaturasState.Error -> {
                snackbarMessage = (candidaturasState as CandidaturasState.Error).message
                showSnackbar = true
                candidaturaViewModel.resetState()
            }
            else -> {}
        }
    }
    
    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            snackbarHostState.showSnackbar(snackbarMessage)
            showSnackbar = false
        }
    }
    
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Encontrar Estágios") },
                actions = {
                    IconButton(onClick = { /* Perfil */ }) {
                        Icon(Icons.Default.AccountCircle, "Perfil")
                    }
                },
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
                    selected = true,
                    onClick = { }
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
                    selected = false,
                    onClick = { navController.navigate(Routes.AlunoCandidaturas.route) }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Olá, ${currentUser?.nome?.split(" ")?.firstOrNull() ?: "Estudante"}!",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Encontre o estágio perfeito para você",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            item {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { 
                        searchText = it
                        if (it.isNotBlank()) {
                            vagaViewModel.buscarVagas(it)
                        } else {
                            vagaViewModel.carregarVagasAtivas()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Buscar vagas...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, "Buscar")
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    ),
                    singleLine = true
                )
            }
            
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoCard(
                        title = "Vagas",
                        value = "${vagas.size}",
                        icon = Icons.Default.Work,
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        title = "Candidaturas",
                        value = "${minhasCandidaturas.size}",
                        icon = Icons.Default.Send,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            item {
                Text(
                    text = "Vagas em Destaque",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            if (vagas.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.SearchOff,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Nenhuma vaga encontrada",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(vagas) { vaga ->
                    VagaCard(
                        vaga = vaga,
                        onCandidatar = {
                            currentUser?.let { user ->
                                val candidatura = Candidatura(
                                    vagaId = vaga.id,
                                    vagaTitulo = vaga.titulo,
                                    empresaId = vaga.empresaId,
                                    empresaNome = vaga.empresaNome,
                                    alunoNome = user.nome,
                                    alunoEmail = user.email,
                                    alunoCurso = user.curso,
                                    alunoInstituicao = user.instituicao
                                )
                                candidaturaViewModel.candidatar(candidatura)
                            }
                        },
                        jaCandidatou = minhasCandidaturas.any { it.vagaId == vaga.id }
                    )
                }
            }
        }
    }
}

@Composable
fun InfoCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(90.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkCard
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier.size(24.dp)
            )
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

