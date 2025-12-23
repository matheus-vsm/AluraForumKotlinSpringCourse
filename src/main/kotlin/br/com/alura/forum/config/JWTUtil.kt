package br.com.alura.forum.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JWTUtil {

    private val expiration: Long = 3600000 // 1 hour in milliseconds
    @Value("\${jwt.secret}")
    private lateinit var secret: String // gera a variavel secret apenas quando for inicializada

    fun generateToken(username: String): String? {
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
            .compact()
    }

}