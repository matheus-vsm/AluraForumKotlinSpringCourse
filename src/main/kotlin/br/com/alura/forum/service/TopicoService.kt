package br.com.alura.forum.service

import br.com.alura.forum.dto.AtualizacaoTopicoForm
import br.com.alura.forum.dto.NovoTopicoForm
import br.com.alura.forum.dto.TopicoPorCategoriaDto
import br.com.alura.forum.dto.TopicoView
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.repository.TopicoRepository
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TopicoService(
    private val repository: TopicoRepository,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val notFoundMessage: String = "Topico não encontrado",
    private val em: EntityManager
) {

    fun listar(
        nomeCurso: String?,
        paginacao: Pageable
    ): Page<TopicoView> {
        print(em)
        val topicos = if (nomeCurso == null) repository.findAll(paginacao)
        else repository.findByCursoNome(nomeCurso, paginacao)

        return topicos.map { t -> topicoViewMapper.map(t) }
    }

    fun buscarPorId(id: Long): TopicoView {
        val topico = repository.getReferenceById(id) ?: throw NotFoundException(notFoundMessage)

        return topicoViewMapper.map(topico)
    }

    fun cadastrar(form: NovoTopicoForm): TopicoView {
        val topico = topicoFormMapper.map(form)
        repository.save(topico)

        return topicoViewMapper.map(topico)
    }

    fun atualizar(form: AtualizacaoTopicoForm): TopicoView {
        val topico = repository.getReferenceById(form.id) ?: throw NotFoundException(notFoundMessage)
        topico.titulo = form.titulo
        topico.mensagem = form.mensagem
        return topicoViewMapper.map(topico)
    }

    fun deletar(id: Long) {
        repository.deleteById(id)
    }

    fun relatorio(): List<TopicoPorCategoriaDto> {
        return repository.relatorio()
    }

}