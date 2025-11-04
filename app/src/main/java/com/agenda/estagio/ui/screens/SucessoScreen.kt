package com.agenda.estagio.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.agenda.estagio.navigation.Routes
import com.agenda.estagio.ui.theme.PrimaryBlue
import com.agenda.estagio.ui.theme.SuccessGreen
import kotlinx.coroutines.delay

@Composable
fun SucessoScreen(
    navController: NavController,
    mensagem: String
) {
    LaunchedEffect(Unit) {
        delay(2500)
        navController.navigate(Routes.UserTypeSelection.route) {
            popUpTo(0)
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Sucesso",
            modifier = Modifier.size(120.dp),
            tint = SuccessGreen
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Sucesso!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = SuccessGreen
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = mensagem,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        CircularProgressIndicator(
            color = PrimaryBlue,
            modifier = Modifier.size(32.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Redirecionando...",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = {
                navController.navigate(Routes.UserTypeSelection.route) {
                    popUpTo(0)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Voltar ao Início")
        }
    }
}
