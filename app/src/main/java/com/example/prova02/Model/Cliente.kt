package com.example.prova02.Model

class Cliente(val cpf : String, val nome : String, val email : String, val instagram : String){

    //CONSTRUTOR PADRÃO PARA O FIREBASE
    constructor(): this("", "", "", "")

    override fun toString(): String {
        return  " CPF: $cpf\n" +
                " Nome: $nome\n"+
                " Email: $email\n" +
                " Instagram: $instagram"
    }
}
