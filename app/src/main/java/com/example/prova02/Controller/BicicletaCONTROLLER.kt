package com.example.prova02.Controller

import com.example.prova02.Model.BicicletaDAO
import com.example.prova02.Model.Bicicleta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.firebase.firestore.FirebaseFirestore


class BicicletaCONTROLLER(private val bicicletaDAO: BicicletaDAO) {

    suspend fun inserirBicicleta(bicicleta: Bicicleta) {
        withContext(Dispatchers.IO) {
            bicicletaDAO.inserirBicicleta(bicicleta)
        }
    }

    suspend fun buscarBicicletas(): List<Bicicleta> {
        return withContext(Dispatchers.IO) {
            bicicletaDAO.buscarBicicletas()
        }
    }

    suspend fun buscarBicicletaPorCodigo(codigo: String): Bicicleta? {
        return withContext(Dispatchers.IO) {
            bicicletaDAO.buscarBicicletaPorCodigo(codigo)
        }
    }

    suspend fun buscarBicicletaPorModelo(modelo: String): Bicicleta? {
        return withContext(Dispatchers.IO) {
            bicicletaDAO.buscarBicicletaPorModelo(modelo)
        }
    }

    suspend fun updateBicicleta(bicicleta: Bicicleta) {
        withContext(Dispatchers.IO) {
            bicicletaDAO.updateBicicleta(bicicleta)
        }
    }

    suspend fun deletarBicicleta(codigo: String) {
        withContext(Dispatchers.IO) {
            bicicletaDAO.deletarBicicleta(codigo)
        }
    }

    suspend fun deletarBicicletaCpfDono(cpf: String) {
        withContext(Dispatchers.IO) {
            bicicletaDAO.deletarBicicletaCpfDono(cpf)
        }
    }
}
