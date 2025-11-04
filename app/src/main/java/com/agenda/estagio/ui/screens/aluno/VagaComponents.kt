package com.agenda.estagio.ui.screens.aluno
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.agenda.estagio.data.models.Vaga
import com.agenda.estagio.ui.theme.DarkCard
import com.agenda.estagio.ui.theme.PrimaryBlue

@Composable
fun VagaCard(
    vaga: Vaga,
    onCandidatar: () -> Unit,
    jaCandidatou: Boolean
) {
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = vaga.titulo,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = vaga.empresaNome,
                        style = MaterialTheme.typography.bodyMedium,
                        color = PrimaryBlue
                    )
                }
                IconButton(onClick = { /* Favoritar */ }) {
                    Icon(
                        Icons.Default.BookmarkBorder,
                        "Salvar",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Chip(
                    text = vaga.area,
                    icon = Icons.Default.Category
                )
                Chip(
                    text = vaga.localizacao,
                    icon = Icons.Default.LocationOn
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AttachMoney,
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = vaga.bolsa.ifBlank { "A combinar" },
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Button(
                    onClick = onCandidatar,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (jaCandidatou)
                            MaterialTheme.colorScheme.surfaceVariant
                        else
                            PrimaryBlue
                    ),
                    shape = RoundedCornerShape(8.dp),
                    enabled = !jaCandidatou
                ) {
                    Text(if (jaCandidatou) "Candidatado" else "Candidatar-se")
                }
            }
        }
    }
}

@Composable
fun Chip(
    text: String,
    icon: ImageVector
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
