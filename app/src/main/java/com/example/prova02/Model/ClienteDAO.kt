package com.example.prova02.Model

import com.google.firebase.FirebaseException
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FirebaseFirestore

class ClienteDAO {
    private val db = FirebaseFirestore.getInstance()
    private val clienteCollection = db.collection("clientes")

    suspend fun inserirCliente(cliente: Cliente) {
        try {
            clienteCollection.document(cliente.cpf).set(cliente).await()
        } catch (e: Exception) {
            throw Exception("Erro ao inserir cliente", e)
        }
    }

    suspend fun buscarClientes(): List<Cliente> {
        return try {
            val querySnapshot = clienteCollection.get().await()
            querySnapshot.toObjects(Cliente::class.java)
        } catch (e: Exception) {
            throw Exception("Erro ao buscar clientes", e)
        }
    }

    suspend fun buscarClientePorCpf(cpf: String): Cliente? {
        return try {
            val doc = clienteCollection.document(cpf).get().await()
            doc.toObject(Cliente::class.java)
        } catch (e: Exception) {
            throw Exception("Erro ao buscar cliente por CPF", e)
        }
    }

    suspend fun buscarClientePorNome(nome: String): Cliente? {
        return try {
            val querySnapshot = clienteCollection.whereEqualTo("nome", nome).get().await()
            if (!querySnapshot.isEmpty) {
                querySnapshot.documents[0].toObject(Cliente::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            throw Exception("Erro ao buscar cliente por nome", e)
        }
    }

    suspend fun updateCliente(cliente: Cliente) {
        try {
            clienteCollection.document(cliente.cpf).set(cliente).await()
        } catch (e: Exception) {
            throw Exception("Erro ao atualizar cliente", e)
        }
    }
    suspend fun deletarCliente(cpf: String) {
        try {
            clienteCollection.document(cpf).delete().await()
        } catch (e: Exception) {
            throw Exception("Erro ao deletar cliente", e)
        }
    }
}