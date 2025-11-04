package com.agenda.estagio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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

@Composable
fun AlunoLoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    
    val authState by authViewModel.authState.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()
    
    // Navegar quando login for bem-sucedido
    LaunchedEffect(authState, currentUser) {
        if (authState is AuthState.Success && currentUser != null) {
            if (currentUser?.tipo == UserType.ALUNO) {
                navController.navigate(Routes.AlunoHome.route) {
                    popUpTo(Routes.UserTypeSelection.route) { inclusive = true }
                }
            }
            authViewModel.resetAuthState()
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF363636)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Login do Aluno",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF5F5F5)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("E-mail", color = Color(0xFFB0B0B0)) },
                        leadingIcon = { 
                            Icon(Icons.Default.Email, contentDescription = "", tint = Color(0xFFB0B0B0)) 
                        },
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
                    
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    OutlinedTextField(
                        value = senha,
                        onValueChange = { senha = it },
                        label = { Text("Senha", color = Color(0xFFB0B0B0)) },
                        leadingIcon = { 
                            Icon(Icons.Default.Lock, contentDescription = "", tint = Color(0xFFB0B0B0)) 
                        },
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
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Mostrar mensagem de erro
                    if (authState is AuthState.Error) {
                        Text(
                            text = (authState as AuthState.Error).message,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                    
                    Button(
                        onClick = {
                            if (email.isNotBlank() && senha.isNotBlank()) {
                                authViewModel.login(email.trim(), senha)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
                        shape = RoundedCornerShape(8.dp),
                        enabled = authState !is AuthState.Loading
                    ) {
                        if (authState is AuthState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Entrar", color = Color.White, fontSize = 18.sp)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Esqueceu a senha?",
                            color = Color(0xFF00C853),
                            fontSize = 14.sp,
                            modifier = Modifier.clickable { /* TODO: Recuperar senha */ }
                        )
                        Text(
                            text = "Cadastre-se",
                            color = Color(0xFF00C853),
                            fontSize = 14.sp,
                            modifier = Modifier.clickable { 
                                navController.navigate(Routes.AlunoCadastro.route) 
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "Entrar como Empresa",
                color = Color(0xFF00C853),
                fontSize = 14.sp,
                modifier = Modifier.clickable { 
                    navController.navigate(Routes.EmpresaLogin.route) 
                }
            )
        }
    }
}
