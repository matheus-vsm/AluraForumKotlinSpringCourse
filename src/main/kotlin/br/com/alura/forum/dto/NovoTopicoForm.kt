package br.com.alura.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class NovoTopicoForm(
    @field:NotEmpty(message = "Titulo não pode ser vazio!")
    @field:Size(min = 1, max = 100, message = "Titulo não pode ser vazio e deve ter entre 1 e 100 caracteres!")
    val titulo: String,
    @field:NotEmpty(message = "Mensagem não pode ser vazia e deve ter entre 1 e 200 caracteres!")
    @field:Size(min = 1, max = 200)
    val mensagem: String,
    @field:NotNull
    val idCurso: Long,
    @field:NotNull
    val idAutor: Long
)