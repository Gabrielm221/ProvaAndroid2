package com.example.prova02.View

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prova02.Controller.BicicletaCONTROLLER
import com.example.prova02.Controller.ClienteCONTROLLER
import com.example.prova02.Controller.UnionCONTROLLER
import com.example.prova02.Model.*
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JeffBikeApp()
        }
    }
}

@Composable
fun JeffBikeApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("list/{flag}/{nome}/{cpf}") { backStackEntry ->
            val flag = backStackEntry.arguments?.getString("flag") ?: ""
            val nome = backStackEntry.arguments?.getString("nome") ?: ""
            val cpf = backStackEntry.arguments?.getString("cpf") ?: ""
            ListScreen(navController, flag, nome, cpf)
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    val bicicletaController = remember { BicicletaCONTROLLER(BicicletaDAO()) }
    val clienteController = remember { ClienteCONTROLLER(ClienteDAO()) }
    val unionController = remember { UnionCONTROLLER(UnionDAO()) }
    val context = LocalContext.current

    var codigo by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var materialDoChassi by remember { mutableStateOf("") }
    var aro by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var quantidadeDeMarchas by remember { mutableStateOf("") }
    var clienteCpf by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var instagram by remember { mutableStateOf("") }
    var clientes by remember { mutableStateOf<List<Cliente>>(emptyList()) }
    var clientesNomes by remember { mutableStateOf<List<String>>(emptyList()) }
    var bicicletas by remember { mutableStateOf<List<Bicicleta>>(emptyList()) }
    var unions by remember { mutableStateOf<List<Union>>(emptyList()) }
    var bicicletax by remember { mutableStateOf(Bicicleta("", "", "", "", 0.0, 0, "")) }

    LaunchedEffect(Unit) {
        clientes = clienteController.buscarClientes()
        clientesNomes = clientes.map { it.nome }
        bicicletas = bicicletaController.buscarBicicletas()
        unions = unionController.buscarUnions()
    }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // UI para inserir bicicletas
        Text("Cadastro de Bicicletas", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

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
            label = { Text("Aro") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = preco,
            onValueChange = { preco = it },
            label = { Text("Preço") },
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
        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome do Cliente") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail do Cliente") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = instagram,
            onValueChange = { instagram = it },
            label = { Text("Instagram do Cliente") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    // Validar se todos os campos estão preenchidos
                    if (codigo.isBlank() || modelo.isBlank() || materialDoChassi.isBlank() ||
                        aro.isBlank() || preco.isBlank() || quantidadeDeMarchas.isBlank() ||
                        clienteCpf.isBlank() || nome.isBlank() || email.isBlank() || instagram.isBlank()
                    ) {
                        Toast.makeText(
                            context,
                            "Preencha todos os campos antes de inserir!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    // Validar se o CPF do cliente já existe
                    val clienteExistente = clientes.find { it.cpf == clienteCpf }
                    if (clienteExistente != null) {
                        Toast.makeText(
                            context,
                            "Já existe um cliente cadastrado com esse CPF!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    // Validar se o código da bicicleta já existe
                    val bicicletaExistente = bicicletas.find { it.codigo == codigo }
                    if (bicicletaExistente != null) {
                        Toast.makeText(
                            context,
                            "Já existe uma bicicleta cadastrada com esse código!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    val novaBicicleta = Bicicleta(
                        codigo = codigo,
                        modelo = modelo,
                        materialDoChassi = materialDoChassi,
                        aro = aro,
                        preco = preco.toDoubleOrNull() ?: 0.0,
                        quantidadeDeMarchas = quantidadeDeMarchas.toIntOrNull() ?: 0,
                        clienteCpf = clienteCpf
                    )

                    val novoCliente = Cliente(
                        cpf = clienteCpf,
                        nome = nome,
                        email = email,
                        instagram = instagram
                    )

                    val novaUniao = Union(
                        cli = novoCliente,
                        bic = novaBicicleta
                    )

                    coroutineScope.launch {
                        try {
                            bicicletaController.inserirBicicleta(novaBicicleta)
                            bicicletas = bicicletaController.buscarBicicletas()
                            clienteController.inserirCliente(novoCliente)
                            clientes = clienteController.buscarClientes()
                            unionController.inserirUniao(novaUniao)
                            unions = unionController.buscarUnions()
                            Toast.makeText(
                                context,
                                "Bicicleta e Cliente inseridos com sucesso!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                "Erro ao inserir Bicicleta e Cliente: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Inserir")
            }

            Button(
                onClick = {
                    // Validar se o CPF do cliente está preenchido
                    if (clienteCpf.isBlank()) {
                        Toast.makeText(
                            context,
                            "Informe o CPF do cliente para excluir!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    coroutineScope.launch {
                        try {
                            // Buscar a bicicleta do cliente
                            val bicicletaCliente = bicicletas.find { it.clienteCpf == clienteCpf }
                            if (bicicletaCliente != null) {
                                // Deletar bicicleta
                                bicicletaController.deletarBicicleta(bicicletaCliente.codigo)
                                bicicletas = bicicletaController.buscarBicicletas()
                            }

                            // Deletar cliente
                            clienteController.deletarClientes(clienteCpf)
                            clientes = clienteController.buscarClientes()

                            // Deletar união
                            unionController.deletarUnion(clienteCpf)
                            unions = unionController.buscarUnions()

                            Toast.makeText(
                                context,
                                "Cliente e sua bicicleta excluídos com sucesso!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                "Erro ao excluir Cliente e sua bicicleta: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Excluir")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    navController.navigate("list/flag1/null/null")
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Listar Todos")
            }

            Button(
                onClick = {
                    // Validar se o nome do cliente está preenchido
                    if (nome.isBlank()) {
                        Toast.makeText(
                            context,
                            "Informe o nome do cliente para buscar!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    navController.navigate("list/flag2/$nome/null")
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Buscar por Nome")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    // Validar se o CPF do cliente está preenchido
                    if (clienteCpf.isBlank()) {
                        Toast.makeText(
                            context,
                            "Informe o CPF do cliente para atualizar!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    // Validar se todos os campos estão preenchidos
                    if (codigo.isBlank() || modelo.isBlank() || materialDoChassi.isBlank() ||
                        aro.isBlank() || preco.isBlank() || quantidadeDeMarchas.isBlank() ||
                        nome.isBlank() || email.isBlank() || instagram.isBlank()
                    ) {
                        Toast.makeText(
                            context,
                            "Preencha todos os campos antes de atualizar!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    // Validar se o cliente já possui uma bicicleta cadastrada
                    if (clienteCpf.isBlank() || codigo.isBlank() || materialDoChassi.isBlank() || aro.isBlank() || preco.isBlank() || nome.isBlank() || email.isBlank() || instagram.isBlank()) {
                        Toast.makeText(context, "Dados não preenchidos corretamente, informe corretamente", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // Validar se o CPF do cliente existe na lista de clientes
                    val clienteExistente = clientes.find { it.cpf == clienteCpf }
                    if (clienteExistente == null) {
                        Toast.makeText(context, "Cliente não cadastrado ou você alterou o cpf!! Para alteração de cpf entre em contato com o suporte.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    //Validar se já existe o código da bike no bd pra evitar duplicação de dados
                    val idExistente = bicicletas.find {it.codigo == codigo}
                    if(idExistente == null){
                        Toast.makeText(context, "Cliente não cadastrado ou você id da bike!! Para alteração de id entre em contato com o suporte!!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val novaBicicleta = Bicicleta(
                        codigo = codigo,
                        modelo = modelo,
                        materialDoChassi = materialDoChassi,
                        aro = aro,
                        preco = preco.toDoubleOrNull() ?: 0.0,
                        quantidadeDeMarchas = quantidadeDeMarchas.toIntOrNull() ?: 0,
                        clienteCpf = clienteCpf
                    )

                    val novoCliente = Cliente(
                        cpf = clienteCpf,
                        nome = nome,
                        email = email,
                        instagram = instagram
                    )

                    val novaUniao = Union(
                        cli = novoCliente,
                        bic = novaBicicleta
                    )

                    coroutineScope.launch {
                        try {
                            val bicicletaEncontrada = bicicletaController.buscarBicicletaPorCodigo(codigo)
                            bicicletax = bicicletaEncontrada ?: throw Exception("Bicicleta não encontrada")

                            val salvaCpf = bicicletax.clienteCpf

                            bicicletaController.deletarBicicletaCpfDono(codigo)
                            bicicletas = bicicletaController.buscarBicicletas()
                            clienteController.deletarClientes(salvaCpf)
                            clientes = clienteController.buscarClientes()
                            unionController.deletarUnion(salvaCpf)
                            bicicletaController.inserirBicicleta(novaBicicleta)
                            bicicletas = bicicletaController.buscarBicicletas()
                            clienteController.inserirCliente(novoCliente)
                            unionController.inserirUniao(novaUniao)
                            unions = unionController.buscarUnions()

                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                "Erro ao atualizar o Cliente e sua bicicleta: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Atualizar")
            }

            Button(
                onClick = {
                    exportDatabaseToTxt(context, unions)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Exportar para TXT")
            }
            Button(onClick = {
                if (clienteCpf.isBlank()) {
                    Toast.makeText(context, "Campo vazio, preencha o CPF para buscar por CPF!", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                navController.navigate("list/flag3/null/$clienteCpf")
            }) {
                Text("Buscar cliente e bicicleta por CPF")
            }
        }
        }
    }


//OUTRA PÁGINA DO COMPOSE PARA EXIBIR A LISTA DE RESULTADOS DE CLIENTE E BIKE
@Composable
fun ListScreen(navController: NavHostController, flag: String, nome: String, cpf: String) {
    val unionController = remember { UnionCONTROLLER(UnionDAO()) }
    var unions by remember { mutableStateOf<List<Union>>(emptyList()) }

    LaunchedEffect(flag, nome, cpf) {
        unions = when (flag) {
            "flag1" -> unionController.buscarUnions()
            "flag2" -> unionController.buscarUnionsPorNome(nome)
            "flag3" -> unionController.buscarUnionsPorCPF(cpf)
            else -> emptyList()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Clientes e suas bicicletas cadastradas", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(unions) { union ->
                Text(union.toString())
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Voltar para Home")
        }
    }
}

fun exportDatabaseToTxt(context: android.content.Context, unions: List<Union>) {

    val fileName = "database_export.txt"
    val dir = context.getExternalFilesDir(null)
    val file = File(dir, fileName)

    try {
        val writer = BufferedWriter(FileWriter(file))
        for (union in unions) {
            writer.write("Cliente:")
            writer.newLine()
            writer.write("CPF: ${union.cli.cpf}")
            writer.newLine()
            writer.write("Nome: ${union.cli.nome}")
            writer.newLine()
            writer.write("Email: ${union.cli.email}")
            writer.newLine()
            writer.write("Instagram: ${union.cli.instagram}")
            writer.newLine()
            writer.write("Bicicleta:")
            writer.newLine()
            writer.write("Código: ${union.bic.codigo}")
            writer.newLine()
            writer.write("Modelo: ${union.bic.modelo}")
            writer.newLine()
            writer.write("Material do Chassi: ${union.bic.materialDoChassi}")
            writer.newLine()
            writer.write("Aro: ${union.bic.aro}")
            writer.newLine()
            writer.write("Preço: ${union.bic.preco}")
            writer.newLine()
            writer.write("Quantidade de Marchas: ${union.bic.quantidadeDeMarchas}")
            writer.newLine()
            writer.newLine()
        }
        writer.close()
        Toast.makeText(context, "Dados exportados para $fileName", Toast.LENGTH_SHORT).show()
    } catch (e: IOException) {
        Log.e("IOException", "Erro ao exportar dados: ${e.message}")
        Toast.makeText(context, "Erro ao exportar dados: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}



