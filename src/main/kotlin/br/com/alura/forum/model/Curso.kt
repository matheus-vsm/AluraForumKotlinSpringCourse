package br.com.alura.forum.model

import jakarta.persistence.*

@Entity
@Table(name = "curso")
data class Curso(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val nome: String,
    val categoria: String
)