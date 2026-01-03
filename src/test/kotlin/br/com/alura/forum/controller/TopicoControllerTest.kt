package br.com.alura.forum.controller

import br.com.alura.forum.config.JWTUtil
import br.com.alura.forum.model.Role
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity.status
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TopicoControllerTest {

    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    private lateinit var jwtUtil: JWTUtil

    private var token: String? = null

    companion object {
        private const val RECURSO = "/topicos/"
        private const val RECURSO_ID = RECURSO.plus("%s") // Exemplo: /topicos/1
//        private const val RECURSO_ID = "$RECURSO/%s" // mesma coisa que o de cima
    }

    @BeforeEach // Configurações iniciais antes de cada teste
    fun setUp() {
        token = gerarToken()

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder>(
                SecurityMockMvcConfigurers.springSecurity()
            )
            .build()
    }

    @Test
    fun deveRetornarCodigo400QuandoChamarTopicosSemToken() {
        mockMvc.get(RECURSO).andExpect {
            status {
                is4xxClientError()
            }
        }
    }

    @Test
    fun deveRetornarCodigo200QuandoChamarTopicosComToken() {
        mockMvc.get(RECURSO) {
            header("Authorization", "Bearer $token")
        }.andExpect {
            status {
                is2xxSuccessful()
            }
        }
    }

    @Test
    fun deveRetornarCodigo200QuandoChamarTopicoPorIdComToken() {
       mockMvc.get(RECURSO_ID.format("1")) {
            header("Authorization", "Bearer $token")
        }.andExpect {
            status {
                is2xxSuccessful()
            }
        }
    }

    private fun gerarToken(): String? {
        val authorities = mutableListOf(Role(1, "LEITURA_ESCRITA"))

        return jwtUtil.generateToken("ana@email.com", authorities)
    }

}