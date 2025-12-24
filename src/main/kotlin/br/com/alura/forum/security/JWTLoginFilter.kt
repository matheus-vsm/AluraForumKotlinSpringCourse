package br.com.alura.forum.security

import br.com.alura.forum.config.JWTUtil
import br.com.alura.forum.model.Credentials
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import tools.jackson.databind.ObjectMapper

// Filtro responsável por autenticar o usuário e gerar o JWT
class JWTLoginFilter(
    // AuthenticationManager é usado para validar usuário e senha
    private val authManager: AuthenticationManager,
    // Classe utilitária responsável por gerar o token JWT
    private val jwtUtil: JWTUtil
) : UsernamePasswordAuthenticationFilter() {

    // Metodo chamado quando alguém tenta se autenticar (login)
    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {

        // Lê o corpo da requisição (JSON) e converte para a classe Credentials
        // Espera algo como: { "username": "...", "password": "..." }
        val (username, password) =
            ObjectMapper().readValue(request?.inputStream, Credentials::class.java)

        // Cria um token de autenticação com username e password
        val token = UsernamePasswordAuthenticationToken(username, password)

        // Delegar a autenticação para o AuthenticationManager
        return authManager.authenticate(token)
    }

    // Metodo chamado automaticamente quando a autenticação é bem-sucedida
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {

        // Obtém o usuário autenticado
        val user = (authResult.principal as UserDetails)

        // Gera o token JWT com base no username
        val token = jwtUtil.generateToken(user.username, user.authorities)

        // Adiciona o token JWT no header da resposta
        // Exemplo: Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
        response?.addHeader("Authorization", "Bearer $token")
    }
}