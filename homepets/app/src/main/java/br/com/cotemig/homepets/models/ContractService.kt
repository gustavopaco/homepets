package br.com.cotemig.homepets.models

import java.io.Serializable
import java.time.LocalDateTime

data class ContractService(
    var idServico: Int,
    var dataExecucao: String,
    var idPet: Int
) : Serializable