package br.com.alura.forum.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JWTUtil {

    private val expiration: Long = 3600000 // 1 hour in milliseconds

    @Value("\${jwt.secret}")
    private lateinit var secret: String // gera a variavel secret apenas quando for inicializada

    fun generateToken(username: String): String? {
        return Jwts.builder()
            .setSubject(username) // Define o "dono" do token (normalmente o username)
            .setExpiration(Date(System.currentTimeMillis() + expiration)) // Define quando o token irá expirar
            .signWith(
                SignatureAlgorithm.HS512,
                secret.toByteArray()
            ) // Assina o token usando o algoritmo HS512 e a chave secreta
            .compact() // Gera o token final no formato String
    }

    fun isValid(jwt: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun getAuthentication(jwt: String?): Authentication {
        val username = Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt).body.subject
        return UsernamePasswordAuthenticationToken(username, null, null)
    }

}