package com.agenda.estagio.ui.screens.empresa

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.agenda.estagio.data.models.Vaga
import com.agenda.estagio.data.models.VagaStatus
import com.agenda.estagio.data.viewmodel.AuthViewModel
import com.agenda.estagio.data.viewmodel.VagaViewModel
import com.agenda.estagio.data.viewmodel.VagasState
import com.agenda.estagio.ui.theme.DarkCard
import com.agenda.estagio.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpresaCriarVagaScreen(
    navController: NavController,
    vagaViewModel: VagaViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }
    var requisitos by remember { mutableStateOf("") }
    var beneficios by remember { mutableStateOf("") }
    var cargaHoraria by remember { mutableStateOf("") }
    var bolsa by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var modalidade by remember { mutableStateOf("Presencial") }
    var numeroVagas by remember { mutableStateOf("1") }
    
    val vagasState by vagaViewModel.vagasState.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()
    
    val modalidadeOptions = listOf("Presencial", "Remoto", "Híbrido")
    var expandedModalidade by remember { mutableStateOf(false) }
    
    // Navegar de volta quando vaga for criada
    LaunchedEffect(vagasState) {
        if (vagasState is VagasState.Success) {
            navController.navigateUp()
            vagaViewModel.resetState()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Criar Nova Vaga") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkCard
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Informações da Vaga",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título da Vaga") },
                leadingIcon = { Icon(Icons.Default.Work, "") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = vagasState !is VagasState.Loading
            )
            
            OutlinedTextField(
                value = area,
                onValueChange = { area = it },
                label = { Text("Área") },
                leadingIcon = { Icon(Icons.Default.Category, "") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = vagasState !is VagasState.Loading
            )
            
            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") },
                leadingIcon = { Icon(Icons.Default.Description, "") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 3,
                maxLines = 5,
                enabled = vagasState !is VagasState.Loading
            )
            
            OutlinedTextField(
                value = requisitos,
                onValueChange = { requisitos = it },
                label = { Text("Requisitos") },
                leadingIcon = { Icon(Icons.Default.Checklist, "") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 3,
                maxLines = 5,
                enabled = vagasState !is VagasState.Loading
            )
            
            OutlinedTextField(
                value = beneficios,
                onValueChange = { beneficios = it },
                label = { Text("Benefícios") },
                leadingIcon = { Icon(Icons.Default.CardGiftcard, "") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                minLines = 2,
                maxLines = 4,
                enabled = vagasState !is VagasState.Loading
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = cargaHoraria,
                    onValueChange = { cargaHoraria = it },
                    label = { Text("Carga Horária") },
                    leadingIcon = { Icon(Icons.Default.Schedule, "") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("Ex: 6h/dia") },
                    enabled = vagasState !is VagasState.Loading
                )
                
                OutlinedTextField(
                    value = numeroVagas,
                    onValueChange = { numeroVagas = it },
                    label = { Text("Nº Vagas") },
                    leadingIcon = { Icon(Icons.Default.Numbers, "") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    enabled = vagasState !is VagasState.Loading
                )
            }
            
            OutlinedTextField(
                value = bolsa,
                onValueChange = { bolsa = it },
                label = { Text("Bolsa/Remuneração") },
                leadingIcon = { Icon(Icons.Default.MonetizationOn, "") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text("Ex: R$ 1.500,00") },
                enabled = vagasState !is VagasState.Loading
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Localização",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            ExposedDropdownMenuBox(
                expanded = expandedModalidade,
                onExpandedChange = { expandedModalidade = !expandedModalidade }
            ) {
                OutlinedTextField(
                    value = modalidade,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Modalidade") },
                    leadingIcon = { Icon(Icons.Default.LocationOn, "") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedModalidade) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    enabled = vagasState !is VagasState.Loading
                )
                
                ExposedDropdownMenu(
                    expanded = expandedModalidade,
                    onDismissRequest = { expandedModalidade = false }
                ) {
                    modalidadeOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                modalidade = option
                                expandedModalidade = false
                            }
                        )
                    }
                }
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = cidade,
                    onValueChange = { cidade = it },
                    label = { Text("Cidade") },
                    leadingIcon = { Icon(Icons.Default.LocationCity, "") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    enabled = vagasState !is VagasState.Loading
                )
                
                OutlinedTextField(
                    value = estado,
                    onValueChange = { estado = it },
                    label = { Text("Estado") },
                    leadingIcon = { Icon(Icons.Default.Map, "") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("UF") },
                    enabled = vagasState !is VagasState.Loading
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Mostrar mensagem de erro
            if (vagasState is VagasState.Error) {
                Text(
                    text = (vagasState as VagasState.Error).message,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
            
            Button(
                onClick = {
                    if (titulo.isNotBlank() && descricao.isNotBlank() && area.isNotBlank()) {
                        val vaga = Vaga(
                            empresaNome = currentUser?.nomeFantasia ?: currentUser?.nome ?: "",
                            titulo = titulo.trim(),
                            descricao = descricao.trim(),
                            area = area.trim(),
                            requisitos = requisitos.trim(),
                            beneficios = beneficios.trim(),
                            cargaHoraria = cargaHoraria.trim(),
                            bolsa = bolsa.trim(),
                            localizacao = "$cidade, $estado",
                            cidade = cidade.trim(),
                            estado = estado.trim(),
                            modalidade = modalidade,
                            status = VagaStatus.ATIVA,
                            numeroVagas = numeroVagas.toIntOrNull() ?: 1
                        )
                        vagaViewModel.criarVaga(vaga)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = vagasState !is VagasState.Loading
            ) {
                if (vagasState is VagasState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Publicar Vaga",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
