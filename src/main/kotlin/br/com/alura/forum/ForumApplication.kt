package br.com.alura.forum

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching // Habilita o cache na aplicação
class ForumApplication

fun main(args: Array<String>) {
	runApplication<ForumApplication>(*args)
}
