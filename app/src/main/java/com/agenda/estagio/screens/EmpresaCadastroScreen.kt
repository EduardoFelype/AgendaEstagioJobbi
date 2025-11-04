package com.agenda.estagio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.agenda.estagio.data.models.UserType
import com.agenda.estagio.data.viewmodel.AuthState
import com.agenda.estagio.data.viewmodel.AuthViewModel
import com.agenda.estagio.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpresaCadastroScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    var nomeFantasia by remember { mutableStateOf("") }
    var razaoSocial by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var setor by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    
    val authState by authViewModel.authState.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()
    
    // Navegar quando cadastro for bem-sucedido
    LaunchedEffect(authState, currentUser) {
        if (authState is AuthState.Success && currentUser != null) {
            if (currentUser?.tipo == UserType.EMPRESA) {
                navController.navigate(Routes.EmpresaHome.route) {
                    popUpTo(Routes.UserTypeSelection.route) { inclusive = true }
                }
            }
            authViewModel.resetAuthState()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastro de Empresa") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF363636)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF000000))
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Dados da Empresa",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF5F5F5)
            )
            
            OutlinedTextField(
                value = nomeFantasia,
                onValueChange = { nomeFantasia = it },
                label = { Text("Nome Fantasia", color = Color(0xFFB0B0B0)) },
                leadingIcon = { Icon(Icons.Default.Business, "", tint = Color(0xFFB0B0B0)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedBorderColor = Color(0xFFB0B0B0),
                    cursorColor = Color(0xFF00C853),
                    focusedTextColor = Color(0xFFF5F5F5),
                    unfocusedTextColor = Color(0xFFF5F5F5)
                ),
                enabled = authState !is AuthState.Loading
            )
            
            OutlinedTextField(
                value = razaoSocial,
                onValueChange = { razaoSocial = it },
                label = { Text("Razão Social", color = Color(0xFFB0B0B0)) },
                leadingIcon = { Icon(Icons.Default.BusinessCenter, "", tint = Color(0xFFB0B0B0)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedBorderColor = Color(0xFFB0B0B0),
                    cursorColor = Color(0xFF00C853),
                    focusedTextColor = Color(0xFFF5F5F5),
                    unfocusedTextColor = Color(0xFFF5F5F5)
                ),
                enabled = authState !is AuthState.Loading
            )
            
            OutlinedTextField(
                value = cnpj,
                onValueChange = { cnpj = it },
                label = { Text("CNPJ", color = Color(0xFFB0B0B0)) },
                leadingIcon = { Icon(Icons.Default.Badge, "", tint = Color(0xFFB0B0B0)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedBorderColor = Color(0xFFB0B0B0),
                    cursorColor = Color(0xFF00C853),
                    focusedTextColor = Color(0xFFF5F5F5),
                    unfocusedTextColor = Color(0xFFF5F5F5)
                ),
                enabled = authState !is AuthState.Loading
            )
            
            OutlinedTextField(
                value = setor,
                onValueChange = { setor = it },
                label = { Text("Setor/Área de Atuação", color = Color(0xFFB0B0B0)) },
                leadingIcon = { Icon(Icons.Default.Category, "", tint = Color(0xFFB0B0B0)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedBorderColor = Color(0xFFB0B0B0),
                    cursorColor = Color(0xFF00C853),
                    focusedTextColor = Color(0xFFF5F5F5),
                    unfocusedTextColor = Color(0xFFF5F5F5)
                ),
                enabled = authState !is AuthState.Loading
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Contato",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF5F5F5)
            )
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail", color = Color(0xFFB0B0B0)) },
                leadingIcon = { Icon(Icons.Default.Email, "", tint = Color(0xFFB0B0B0)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedBorderColor = Color(0xFFB0B0B0),
                    cursorColor = Color(0xFF00C853),
                    focusedTextColor = Color(0xFFF5F5F5),
                    unfocusedTextColor = Color(0xFFF5F5F5)
                ),
                enabled = authState !is AuthState.Loading
            )
            
            OutlinedTextField(
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text("Telefone", color = Color(0xFFB0B0B0)) },
                leadingIcon = { Icon(Icons.Default.Phone, "", tint = Color(0xFFB0B0B0)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedBorderColor = Color(0xFFB0B0B0),
                    cursorColor = Color(0xFF00C853),
                    focusedTextColor = Color(0xFFF5F5F5),
                    unfocusedTextColor = Color(0xFFF5F5F5)
                ),
                enabled = authState !is AuthState.Loading
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Localização",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF5F5F5)
            )
            
            OutlinedTextField(
                value = cidade,
                onValueChange = { cidade = it },
                label = { Text("Cidade", color = Color(0xFFB0B0B0)) },
                leadingIcon = { Icon(Icons.Default.LocationOn, "", tint = Color(0xFFB0B0B0)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedBorderColor = Color(0xFFB0B0B0),
                    cursorColor = Color(0xFF00C853),
                    focusedTextColor = Color(0xFFF5F5F5),
                    unfocusedTextColor = Color(0xFFF5F5F5)
                ),
                enabled = authState !is AuthState.Loading
            )
            
            OutlinedTextField(
                value = estado,
                onValueChange = { estado = it },
                label = { Text("Estado (UF)", color = Color(0xFFB0B0B0)) },
                leadingIcon = { Icon(Icons.Default.Map, "", tint = Color(0xFFB0B0B0)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedBorderColor = Color(0xFFB0B0B0),
                    cursorColor = Color(0xFF00C853),
                    focusedTextColor = Color(0xFFF5F5F5),
                    unfocusedTextColor = Color(0xFFF5F5F5)
                ),
                enabled = authState !is AuthState.Loading
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Segurança",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF5F5F5)
            )
            
            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                label = { Text("Senha", color = Color(0xFFB0B0B0)) },
                leadingIcon = { Icon(Icons.Default.Lock, "", tint = Color(0xFFB0B0B0)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedBorderColor = Color(0xFFB0B0B0),
                    cursorColor = Color(0xFF00C853),
                    focusedTextColor = Color(0xFFF5F5F5),
                    unfocusedTextColor = Color(0xFFF5F5F5)
                ),
                enabled = authState !is AuthState.Loading
            )
            
            OutlinedTextField(
                value = confirmarSenha,
                onValueChange = { confirmarSenha = it },
                label = { Text("Confirmar Senha", color = Color(0xFFB0B0B0)) },
                leadingIcon = { Icon(Icons.Default.Lock, "", tint = Color(0xFFB0B0B0)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00C853),
                    unfocusedBorderColor = Color(0xFFB0B0B0),
                    cursorColor = Color(0xFF00C853),
                    focusedTextColor = Color(0xFFF5F5F5),
                    unfocusedTextColor = Color(0xFFF5F5F5)
                ),
                enabled = authState !is AuthState.Loading
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Mostrar mensagem de erro
            if (authState is AuthState.Error) {
                Text(
                    text = (authState as AuthState.Error).message,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
            
            Button(
                onClick = {
                    when {
                        nomeFantasia.isBlank() || razaoSocial.isBlank() || cnpj.isBlank() ||
                        email.isBlank() || telefone.isBlank() || setor.isBlank() ||
                        cidade.isBlank() || estado.isBlank() || senha.isBlank() -> {
                            // Todos os campos são obrigatórios
                        }
                        senha != confirmarSenha -> {
                            // Senhas não coincidem
                        }
                        senha.length < 6 -> {
                            // Senha muito curta
                        }
                        else -> {
                            authViewModel.cadastrarEmpresa(
                                email = email.trim(),
                                senha = senha,
                                nomeFantasia = nomeFantasia.trim(),
                                razaoSocial = razaoSocial.trim(),
                                cnpj = cnpj.trim(),
                                telefone = telefone.trim(),
                                setor = setor.trim(),
                                cidade = cidade.trim(),
                                estado = estado.trim()
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
                shape = RoundedCornerShape(8.dp),
                enabled = authState !is AuthState.Loading
            ) {
                if (authState is AuthState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Cadastrar", color = Color.White, fontSize = 18.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
