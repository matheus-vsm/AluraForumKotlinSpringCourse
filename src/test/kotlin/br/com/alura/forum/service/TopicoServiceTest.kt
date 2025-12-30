package br.com.alura.forum.service

import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.model.TopicoTest
import br.com.alura.forum.repository.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.domain.PageImpl

class TopicoServiceTest {

    val topicos = PageImpl(listOf(TopicoTest.build()))

    val topicoRepository: TopicoRepository = mockk {
        // Sempre que o metodo findByCursoNome for chamado
        // com qualquer nome de curso e qualquer Pageable
        // o mock retornará a página de tópicos criada acima
        every {
            findByCursoNome(any(), any())
        } returns topicos
    }
    val topicoViewMapper: TopicoViewMapper = mockk()
    val topicoFormMapper: TopicoFormMapper = mockk()

    val topicoservice = TopicoService(
        topicoRepository, topicoViewMapper, topicoFormMapper
    )


}