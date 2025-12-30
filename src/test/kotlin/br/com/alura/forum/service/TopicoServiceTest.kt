package br.com.alura.forum.service

import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.model.TopicoTest
import br.com.alura.forum.model.TopicoViewTest
import br.com.alura.forum.repository.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class TopicoServiceTest {

    val topicos = PageImpl(listOf(TopicoTest.build()))

    val paginaco: Pageable = mockk()

    val topicoRepository: TopicoRepository = mockk {
        // Sempre que o metodo findByCursoNome for chamado com qualquer nome de curso e qualquer Pageable
        // o mock retornará a página de tópicos criada acima
        every {
            findByCursoNome(any(), any())
        } returns topicos
    }
    val topicoViewMapper: TopicoViewMapper = mockk()
    val topicoFormMapper: TopicoFormMapper = mockk()

    val topicoService = TopicoService(
        topicoRepository, topicoViewMapper, topicoFormMapper
    )

    @Test
    fun deveListarTopicosPorNomeDoCurso() {
        every {
            topicoViewMapper.map(any())
        } returns TopicoViewTest.build()

        topicoService.listar("Kotlinn", paginaco)

        verify(exactly = 1) { // Verifica se o metodo findByCursoNome foi chamado exatamente 1 vez
            topicoRepository.findByCursoNome(any(), any())
        }
        verify(exactly = 1) { // Verifica se o metodo map do topicoViewMapper foi chamado exatamente 1 vez
            topicoViewMapper.map(any())
        }
        verify(exactly = 0) { // Verifica se o metodo findAll NÃO foi chamado
            topicoRepository.findAll(paginaco)
        }
    }

}