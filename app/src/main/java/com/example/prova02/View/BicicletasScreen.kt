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
import com.example.prova02.Controller.BicicletaCONTROLLER
import com.example.prova02.Model.Bicicleta
import com.example.prova02.Model.BicicletaDAO
import kotlinx.coroutines.launch


@Composable
fun BicicletasScreen(
    lifecycleScope: LifecycleCoroutineScope,
    onNavigateBack: () -> Unit
) {
    // State para a tela de bicicletas
    var codigo by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var materialDoChassi by remember { mutableStateOf("") }
    var aro by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var quantidadeDeMarchas by remember { mutableStateOf("") }
    var clienteCpf by remember { mutableStateOf("") }

    val context = LocalContext.current
    val bicicletaController = remember { BicicletaCONTROLLER(BicicletaDAO()) }

    Column(modifier = Modifier.padding(16.dp)) {
        // Compose UI para cadastro de bicicletas
        Text("Cadastro de Bicicletas", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // Campos de texto para cadastro de bicicletas
        TextField(
            value = codigo,
            onValueChange = { codigo = it },
            label = { Text("Código") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = modelo,
            onValueChange = { modelo = it },
            label = { Text("Modelo") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = materialDoChassi,
            onValueChange = { materialDoChassi = it },
            label = { Text("Material do Chassi") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = aro,
            onValueChange = { aro = it },
            label = { Text("Aro da Bicicleta") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = preco,
            onValueChange = { preco = it },
            label = { Text("Preço da Bicicleta") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = quantidadeDeMarchas,
            onValueChange = { quantidadeDeMarchas = it },
            label = { Text("Quantidade de Marchas") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = clienteCpf,
            onValueChange = { clienteCpf = it },
            label = { Text("CPF do Cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val bicicleta = Bicicleta(
                    codigo = codigo,
                    modelo = modelo,
                    materialDoChassi = materialDoChassi,
                    aro = aro,
                    preco = preco.toDoubleOrNull() ?: 0.0,
                    quantidadeDeMarchas = quantidadeDeMarchas.toIntOrNull() ?: 0,
                    clienteCpf = clienteCpf
                )
                lifecycleScope.launch {
                    try {
                        bicicletaController.inserirBicicleta(bicicleta)
                        Toast.makeText(context, "Bicicleta inserida com sucesso", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Erro ao inserir bicicleta", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Inserir Bicicleta")
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

