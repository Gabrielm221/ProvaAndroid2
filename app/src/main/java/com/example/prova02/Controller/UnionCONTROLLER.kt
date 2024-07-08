package com.example.prova02.Controller

import com.example.prova02.Model.Union
import com.example.prova02.Model.UnionDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UnionCONTROLLER(private val unionDAO: UnionDAO) {

    suspend fun inserirUniao(union: Union) {
        withContext(Dispatchers.IO) {
            unionDAO.inserirUniao(union)
        }
    }

    suspend fun buscarUnions(): List<Union> {
        return withContext(Dispatchers.IO) {
            unionDAO.buscarunioes()
        }
    }

    suspend fun buscarUnionsPorCPF(cpf: String): List<Union> {
        return withContext(Dispatchers.IO) {
            unionDAO.buscarUniaoPorCpf(cpf)
        }
    }

    suspend fun buscarUnionsPorNome(nome: String): List<Union> {
        return withContext(Dispatchers.IO) {
            unionDAO.buscarUniaoPorNome(nome)
        }
    }

    suspend fun updateUnion(union: Union) {
        withContext(Dispatchers.IO) {
            unionDAO.updateCliente(union)
        }
    }

    suspend fun deletarUnion(cpf: String) {
        withContext(Dispatchers.IO) {
            unionDAO.deletarUniao(cpf)
        }
    }
}
