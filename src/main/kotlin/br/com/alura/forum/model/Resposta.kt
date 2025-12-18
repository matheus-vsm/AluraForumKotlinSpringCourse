package br.com.alura.forum.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "resposta")
data class Resposta(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val mensagem: String,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    val autor: Usuario,
    @ManyToOne
    val topico: Topico,
    val solucao: Boolean
)