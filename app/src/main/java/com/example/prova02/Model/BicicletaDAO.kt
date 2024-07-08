package com.example.prova02.Model

import com.google.firebase.FirebaseException
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FirebaseFirestore;

class BicicletaDAO() {

    private val db = FirebaseFirestore.getInstance()
    private val bicicletaCollection = db.collection("bicicletas")

    suspend fun inserirBicicleta(bicicleta: Bicicleta) {
        try {
            bicicletaCollection.document(bicicleta.codigo).set(bicicleta).await()
        } catch (e: Exception) {
            throw Exception("Erro ao inserir bicicleta", e)
        }
    }

    suspend fun buscarBicicletas(): List<Bicicleta> {
        return try {
            val querySnapshot = bicicletaCollection.get().await()
            querySnapshot.toObjects(Bicicleta::class.java)
        } catch (e: Exception) {
            throw Exception("Erro ao buscar bicicletas", e)
        }
    }

    suspend fun buscarBicicletaPorCodigo(codigo: String): Bicicleta? {
        return try {
            val doc = bicicletaCollection.document(codigo).get().await()
            doc.toObject(Bicicleta::class.java)
        } catch (e: Exception) {
            throw Exception("Erro ao buscar bicicleta por código", e)
        }
    }

    suspend fun buscarBicicletaPorModelo(modelo: String): Bicicleta? {
        return try {
            val querySnapshot = bicicletaCollection.whereEqualTo("modelo", modelo).get().await()
            if (!querySnapshot.isEmpty) {
                querySnapshot.documents[0].toObject(Bicicleta::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            throw Exception("Erro ao buscar bicicleta por modelo", e)
        }
    }

    suspend fun updateBicicleta(bicicleta: Bicicleta) {
        try {
            bicicletaCollection.document(bicicleta.codigo).set(bicicleta).await()
        } catch (e: Exception) {
            throw Exception("Erro ao atualizar bicicleta", e)
        }
    }

    suspend fun deletarBicicleta(codigo: String) {
        try {
            bicicletaCollection.document(codigo).delete().await()
        } catch (e: Exception) {
            throw Exception("Erro ao deletar bicicleta", e)
        }
    }

    suspend fun deletarBicicletaCpfDono(cpf: String) {
        try {
            bicicletaCollection.document(cpf).delete().await()
        } catch (e: Exception) {
            throw Exception("Erro ao deletar bicicleta", e)
        }
    }

}
