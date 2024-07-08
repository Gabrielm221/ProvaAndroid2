package com.example.prova02.Model


class Union (val cli : Cliente = Cliente(), val bic : Bicicleta = Bicicleta()){

    constructor() : this(Cliente(), Bicicleta())

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("Código da bike: ${bic.codigo}\n")
        sb.append("Modelo da bike: ${bic.modelo}\n")
        sb.append("Material do chassi: ${bic.materialDoChassi}\n")
        sb.append("Aro da bike: ${bic.aro}\n")
        sb.append("Preço da bike: ${bic.preco}\n")
        sb.append("Quantidade de marchas: ${bic.quantidadeDeMarchas}\n")
        sb.append("Cpf do cliente: ${bic.clienteCpf}\n")
        sb.append("Nome do cliente: ${cli.nome}\n")
        sb.append("Email do cliente: ${cli.email}\n")
        sb.append("Instagram do cliente: ${cli.instagram}\n")
        sb.append("==================\n")
        return sb.toString()
    }
}