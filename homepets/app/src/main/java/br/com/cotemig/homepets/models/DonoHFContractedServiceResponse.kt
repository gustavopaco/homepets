package br.com.cotemig.homepets.models

data class DonoHFContractedServiceResponse(
    var nomeServico: String,
    var nomePrestador: String,
    var dataExecucao: String,
    var preco: Double,
    var tipoPreco: Int,
    var nomePet: String
)