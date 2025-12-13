package br.com.alura.forum.controller

import br.com.alura.forum.model.Curso
import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.Usuario
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Arrays

@RestController
@RequestMapping("/topicos")
class TopicoController {

    @GetMapping
    fun listar(): List<Topico> {
        val topico = Topico(
            id =  1,
            titulo = "Duvidas Spring",
            mensagem = "Mensagem",
            curso = Curso(
                id =  1,
                nome = "Spring",
                categoria = "Programacao"
            ),
            autor = Usuario(
                id =  1,
                nome = "Matheus",
                email = "Matheus@Spring.com",
            )
        )

        return Arrays.asList(topico, topico, topico)
    }

}