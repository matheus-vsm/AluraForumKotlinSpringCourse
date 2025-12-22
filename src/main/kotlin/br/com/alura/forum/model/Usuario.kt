package br.com.alura.forum.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "usuario")
data class Usuario(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val nome: String,
    val email: String,
    val password: String,

    @ManyToMany(fetch = FetchType.EAGER) // Carrega os roles junto com o usuário
    @JoinColumn(name = "usuario_role")
    @JsonIgnore // Evita serialização para prevenir problemas de recursão infinita
    val role: List<Role> = mutableListOf()

)