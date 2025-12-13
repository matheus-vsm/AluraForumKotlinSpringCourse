package br.com.alura.forum.service

import br.com.alura.forum.model.Curso
import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.Usuario
import org.springframework.stereotype.Service
import java.util.Arrays

@Service
class TopicoService(
    private var topicos: List<Topico>
) {

    init {
        val topico = Topico(
            id = 1,
            titulo = "Duvidas Spring",
            mensagem = "Mensagem",
            curso = Curso(
                id = 1,
                nome = "Spring",
                categoria = "Programacao"
            ),
            autor = Usuario(
                id = 1,
                nome = "Matheus",
                email = "Matheus@Spring.com",
            )
        )
        val topico2 = Topico(
            id = 2,
            titulo = "Duvidas Spring2",
            mensagem = "Mensagem2",
            curso = Curso(
                id = 2,
                nome = "Spring2",
                categoria = "Programacao2"
            ),
            autor = Usuario(
                id = 2,
                nome = "Matheus2",
                email = "Matheus@Spring.com2",
            )
        )
        val topico3 = Topico(
            id = 3,
            titulo = "Duvidas Spring3",
            mensagem = "Mensagem3",
            curso = Curso(
                id = 3,
                nome = "Spring3",
                categoria = "Programacao3"
            ),
            autor = Usuario(
                id = 3,
                nome = "Matheus3",
                email = "Matheus@Spring.com3",
            )
        )

        topicos = Arrays.asList(topico, topico2, topico3)
    }

    fun listar(): List<Topico> {
        return topicos
    }

    fun buscarPorId(id: Long): Topico {
        return topicos.filter { it.id == id }.first()
    }

}