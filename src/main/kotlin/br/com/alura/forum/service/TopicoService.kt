package br.com.alura.forum.service

import br.com.alura.forum.model.Topico
import org.springframework.stereotype.Service

@Service
class TopicoService(
    private var topicos: List<Topico> = ArrayList()
) {

    fun listar(): List<Topico> {
        return topicos
    }

    fun buscarPorId(id: Long): Topico {
        return topicos.filter { it.id == id }.first()
    }

    fun cadastrar(topico: Topico) {
        topicos.plus(topico)
    }

}