package br.com.alura.forum.service

import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.model.TopicoTest
import br.com.alura.forum.model.TopicoViewTest
import br.com.alura.forum.repository.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.persistence.EntityNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.Optional

class TopicoServiceTest {

    val topicos = PageImpl(listOf(TopicoTest.build()))

    val paginacao: Pageable = mockk()

    val topicoRepository: TopicoRepository = mockk {
        // Sempre que o metodo findByCursoNome for chamado com qualquer nome de curso e qualquer Pageable
        // o mock retornará a página de tópicos criada acima
        every {
            findByCursoNome(any(), any())
        } returns topicos

        every {
            findAll(paginacao)
        } returns topicos
    }

    val topicoViewMapper: TopicoViewMapper = mockk {
        every {
            map(any())
        } returns TopicoViewTest.build()
    }

    val topicoFormMapper: TopicoFormMapper = mockk()

    val topicoService = TopicoService(
        topicoRepository, topicoViewMapper, topicoFormMapper
    )

    @Test
    fun deveListarTopicosPorNomeDoCurso() {
        topicoService.listar("Kotlinn", paginacao)

        verify(exactly = 1) { // Verifica se o metodo findByCursoNome foi chamado exatamente 1 vez
            topicoRepository.findByCursoNome(any(), any())
        }
        verify(exactly = 1) { // Verifica se o metodo map do topicoViewMapper foi chamado exatamente 1 vez
            topicoViewMapper.map(any())
        }
        verify(exactly = 0) { // Verifica se o metodo findAll NÃO foi chamado
            topicoRepository.findAll(paginacao)
        }
    }

    @Test
    fun deveListarTodosOsTopicosQuandoONomeDoCursoForNulo() {
        topicoService.listar(null, paginacao)

        verify(exactly = 0) { // Verifica se o metodo findByCursoNome NÃO foi chamado
            topicoRepository.findByCursoNome(any(), any())
        }
        verify(exactly = 1) { // Verifica se o metodo map do topicoViewMapper foi chamado exatamente 1 vez
            topicoViewMapper.map(any())
        }
        verify(exactly = 1) { // Verifica se o metodo findAll foi chamado exatamente 1 vez
            topicoRepository.findAll(paginacao)
        }
    }

    @Test
    fun deveListarNotFoundExceptionQuandoTopicoNaoExistir() {
        // Sempre que o metodo findById for chamado com qualquer ID,
        // ele irá retornar Optional.empty(), simulando que o tópico não existe
        every {
            topicoRepository.findById(any())
        } returns Optional.empty()

        // Executa o metodo buscarPorId e verifica se uma NotFoundException é lançada
        // A exceção lançada é armazenada na variável 'atual'
        val atual = assertThrows<NotFoundException> {
            topicoService.buscarPorId(2)
        }

        // Verifica se a mensagem da exceção é exatamente a esperada
        assertThat(atual.message).isEqualTo("Topico nao encontrado")
    }

}