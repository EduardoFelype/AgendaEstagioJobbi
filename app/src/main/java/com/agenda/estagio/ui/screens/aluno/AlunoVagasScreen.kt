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
import com.agenda.estagio.data.models.Vaga
import com.agenda.estagio.data.viewmodel.AuthViewModel
import com.agenda.estagio.data.viewmodel.CandidaturaViewModel
import com.agenda.estagio.data.viewmodel.CandidaturasState
import com.agenda.estagio.data.viewmodel.VagaViewModel
import com.agenda.estagio.ui.theme.DarkCard
import com.agenda.estagio.ui.theme.PrimaryBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlunoVagasScreen(
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
                title = { Text("Todas as Vagas") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Filtros */ }) {
                        Icon(Icons.Default.FilterList, "Filtrar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkCard
                )
            )
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
                Text(
                    text = "${vagas.size} vagas disponíveis",
                    style = MaterialTheme.typography.titleMedium,
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

