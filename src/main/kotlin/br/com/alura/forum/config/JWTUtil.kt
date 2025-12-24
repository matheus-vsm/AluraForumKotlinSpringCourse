package br.com.alura.forum.config

import br.com.alura.forum.service.UsuarioService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JWTUtil(
    private val usuarioService: UsuarioService
) {

    private val expiration: Long = 3600000 // 1 hour in milliseconds

    @Value("\${jwt.secret}")
    private lateinit var secret: String // gera a variavel secret apenas quando for inicializada

    private val key by lazy {
        Keys.hmacShaKeyFor(secret.toByteArray())
    }

    fun generateToken(username: String, authorities: MutableCollection<out GrantedAuthority>): String? {
        return Jwts.builder()
            .setSubject(username) // Define o "dono" do token (normalmente o username)
            .claim("role", authorities)
            .setExpiration(Date(System.currentTimeMillis() + expiration)) // Define quando o token irá expirar
            .signWith(SignatureAlgorithm.HS512, key)
            .compact() // Gera o token final no formato String
    }

    fun isValid(jwt: String?): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getAuthentication(jwt: String?): Authentication {
        val username = Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt).body.subject
        val user = usuarioService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(username, null, user.authorities)
    }

}