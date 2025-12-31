package br.com.alura.forum.integration

import br.com.alura.forum.repository.TopicoRepository
import org.springframework.beans.factory.annotation.Autowired

class TopicoRepositoryTest {

    @Autowired
    private lateinit var repository: TopicoRepository

}