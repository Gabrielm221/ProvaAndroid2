package com.example.prova02.View

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.prova02.Controller.ClienteCONTROLLER
import com.example.prova02.Model.Cliente
import com.example.prova02.Model.ClienteDAO
import kotlinx.coroutines.launch

@Composable
fun ClientesScreen(
    lifecycleScope: LifecycleCoroutineScope,
    onNavigateBack: () -> Unit
) {
    // State para a tela de clientes
    var cpf by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var instagram by remember { mutableStateOf("") }

    val context = LocalContext.current
    val clienteController = remember { ClienteCONTROLLER(ClienteDAO()) }

    Column(modifier = Modifier.padding(16.dp)) {
        // Compose UI para cadastro de clientes
        Text("Tela de Clientes", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // Campos de texto para cadastro de clientes
        TextField(
            value = cpf,
            onValueChange = { cpf = it },
            label = { Text("CPF") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = instagram,
            onValueChange = { instagram = it },
            label = { Text("Instagram") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val cliente = Cliente(
                    cpf = cpf,
                    nome = nome,
                    email = email,
                    instagram = instagram
                )
                lifecycleScope.launch {
                    try {
                        clienteController.inserirCliente(cliente)
                        Toast.makeText(context, "Cliente inserido com sucesso", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Erro ao inserir cliente", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Inserir Cliente")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Voltar")
        }
    }
}

