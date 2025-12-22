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

    // Relacionamento Many-to-Many com tabela intermediária
    @ManyToMany(fetch = FetchType.EAGER) // Carrega os roles junto com o usuário
    @JoinTable(
        name = "usuario_role", // nome da tabela de junção
        joinColumns = [JoinColumn(name = "usuario_id")], // FK para Usuario
        inverseJoinColumns = [JoinColumn(name = "role_id")] // FK para Role
    )
    @JsonIgnore // Evita loop infinito na serialização JSON
    val role: List<Role> = mutableListOf()

)