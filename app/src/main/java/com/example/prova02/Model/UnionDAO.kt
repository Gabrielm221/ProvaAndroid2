package com.example.prova02.Model

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UnionDAO {
    private val db = FirebaseFirestore.getInstance()
    private val unionCollection = db.collection("unioes")

    suspend fun inserirUniao(union: Union) {
        try {
            unionCollection.document(union.cli.cpf).set(union).await()
        } catch (e: Exception) {
            throw Exception("Erro ao inserir cliente", e)
        }
    }

    suspend fun buscarunioes(): List<Union> {
        return try {
            val querySnapshot = unionCollection.get().await()
            querySnapshot.toObjects(Union::class.java)
        } catch (e: Exception) {
            throw Exception("Erro ao buscar uniões", e)
        }
    }

    suspend fun buscarUniaoPorCpf(cpf: String): List<Union> {
        return try {
            val querySnapshot = unionCollection.whereEqualTo("cli.cpf", cpf).get().await()
            querySnapshot.toObjects(Union::class.java)
        } catch (e: Exception) {
            throw Exception("Erro ao buscar união por cpf", e)
        }
    }

    suspend fun buscarUniaoPorNome(nome: String): List<Union> {
        return try {
            val querySnapshot = unionCollection.whereEqualTo("cli.nome", nome).get().await()
            querySnapshot.toObjects(Union::class.java)
        } catch (e: Exception) {
            throw Exception("Erro ao buscar união por nome", e)
        }
    }

    suspend fun updateCliente(union: Union) {
        try {
            unionCollection.document(union.cli.cpf).set(union).await()
        } catch (e: Exception) {
            throw Exception("Erro ao atualizar a união dos clientes", e)
        }
    }

    suspend fun deletarUniao(cpf: String) {
        try {
            unionCollection.document(cpf).delete().await()
        } catch (e: Exception) {
            throw Exception("Erro ao deletar união", e)
        }
    }
}
