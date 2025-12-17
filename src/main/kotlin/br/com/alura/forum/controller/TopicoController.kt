package br.com.alura.forum.controller

import br.com.alura.forum.dto.AtualizacaoTopicoForm
import br.com.alura.forum.dto.NovoTopicoForm
import br.com.alura.forum.dto.TopicoView
import br.com.alura.forum.service.TopicoService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/topicos")
class TopicoController(
    private val service: TopicoService
) {

    @GetMapping
    fun listar(
        @RequestParam(required = false) nomeCurso: String? //http://localhost:8080/topicos?nomeCurso=Kotlin
    ): List<TopicoView> {
        return service.listar(nomeCurso)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoView {
        return service.buscarPorId(id)
    }

    @PostMapping
    @Transactional
    fun cadastrar(
        @RequestBody @Valid dto: NovoTopicoForm,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicoView> {
        val topicoView = service.cadastrar(dto)
        val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri()

        return ResponseEntity.created(uri).body(topicoView)
    }

    @PutMapping
    @Transactional
    fun atualziar(@RequestBody @Valid form: AtualizacaoTopicoForm): ResponseEntity<TopicoView> {
        val topicoView = service.atualizar(form)

        return ResponseEntity.ok(topicoView)
    }

    @DeleteMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleter(@PathVariable id: Long) {
        service.deletar(id)
    }

}