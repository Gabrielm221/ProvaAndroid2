package com.example.prova02.Controller

import com.example.prova02.Model.Cliente
import com.example.prova02.Model.ClienteDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClienteCONTROLLER(private val clienteDAO: ClienteDAO) {

    suspend fun inserirCliente(cliente: Cliente) {
        withContext(Dispatchers.IO) {
            clienteDAO.inserirCliente(cliente)
        }
    }

    suspend fun buscarClientePorCpf(cpf: String): Cliente? {
        return withContext(Dispatchers.IO) {
            clienteDAO.buscarClientePorCpf(cpf)
        }
    }

    suspend fun buscarClientePorNome(nome: String): Cliente? {
        return withContext(Dispatchers.IO) {
            clienteDAO.buscarClientePorNome(nome)
        }
    }

    suspend fun buscarClientes(): List<Cliente> {
        return withContext(Dispatchers.IO) {
            clienteDAO.buscarClientes()
        }
    }

    suspend fun deletarClientes(idCpf: String) {
        withContext(Dispatchers.IO) {
            clienteDAO.deletarCliente(idCpf)
        }
    }
}
