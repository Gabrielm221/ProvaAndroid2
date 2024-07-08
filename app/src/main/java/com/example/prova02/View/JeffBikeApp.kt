package com.example.prova02.View

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.prova02.Controller.BicicletaCONTROLLER
import com.example.prova02.Controller.ClienteCONTROLLER
import com.example.prova02.Model.Bicicleta
import com.example.prova02.Model.BicicletaDAO
import com.example.prova02.Model.Cliente
import com.example.prova02.Model.ClienteDAO
import kotlinx.coroutines.launch
import kotlin.collections.List

@Composable
fun JeffBikeApp(lifecycleScope: LifecycleCoroutineScope) {
    var currentScreen by remember { mutableStateOf(Screen.None) }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = {
                currentScreen = Screen.Bicicletas
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text("Operações de Bicicletas")
        }

        Button(
            onClick = {
                currentScreen = Screen.Clientes
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text("Operações de Clientes")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (currentScreen) {
            Screen.Bicicletas -> BicicletasScreen(lifecycleScope = lifecycleScope) {
                currentScreen = Screen.None
            }
            Screen.Clientes -> ClientesScreen(lifecycleScope = lifecycleScope) {
                currentScreen = Screen.None
            }
            Screen.None -> {
                // Mostrar algo quando nenhum botão foi selecionado
                Text("Selecione uma operação acima para começar.")
            }
        }
    }
}

@Composable
fun CadastroBicicletasScreen(
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
        // Campos de texto, botões, etc.
    }
}

@Composable
fun CadastroClientesScreen(
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
        // Campos de texto, botões, etc.
    }
}

enum class Screen {
    Bicicletas, Clientes, None
}
