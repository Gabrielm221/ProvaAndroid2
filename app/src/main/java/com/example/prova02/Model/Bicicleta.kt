package com.example.prova02.Model

class Bicicleta(val codigo : String, val modelo : String, val materialDoChassi : String, val aro : String, val preco : Double, val quantidadeDeMarchas : Int, val clienteCpf: String) {

    //CONSTRUTOR PADRÃO PARA O FIREBASE

    constructor() : this("", "", "", "", 0.0, 0, "")

    override fun toString(): String {
        return  " Código: $codigo\n"+
                " Modelo: $modelo\n" +
                " Material do Chassi : $materialDoChassi\n" +
                " Aro: $aro\n" +
                " Preço: $preco\n" +
                " Quantidade de marchas: $quantidadeDeMarchas\n" +
                " Cpf do Cliente: $clienteCpf\n"
    }
}