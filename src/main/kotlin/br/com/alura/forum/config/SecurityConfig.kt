package br.com.alura.forum.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            // Define regras de autorização
            .authorizeHttpRequests { auth ->
                // Requisições para /topicos exigem a autoridade "LEITURA_ESCRITA"
                auth.requestMatchers("/topicos").hasAuthority("LEITURA_ESCRITA")
                // Qualquer requisição precisa estar autenticada
                auth.anyRequest().authenticated()
            }
            // Configuração de gerenciamento de sessão
            .sessionManagement { session ->
                // Não cria sessão (ideal para APIs REST)
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            // Desativa login via formulário HTML
            .formLogin { form ->
                form.disable()
            }
            // Ativa autenticação via HTTP Basic
            .httpBasic { }

        return http.build()
    }

    @Bean
    fun authenticationManager(
        http: HttpSecurity,
        passwordEncoder: PasswordEncoder
    ): AuthenticationManager {
        // Obtém o AuthenticationManagerBuilder compartilhado pelo Spring
        val authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder::class.java)

        // Define como o Spring vai autenticar usuários
        authenticationManagerBuilder
            .userDetailsService(userDetailsService) // Busca usuários
            .passwordEncoder(passwordEncoder) // Valida senhas com BCrypt

        // Cria o AuthenticationManager
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
