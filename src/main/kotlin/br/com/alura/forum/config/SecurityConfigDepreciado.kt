//package br.com.alura.forum.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.config.http.SessionCreationPolicy
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//
//@Configuration
//@EnableWebSecurity
//class SecurityConfigDepreciado(
//    private val userDetailsService: UserDetailsService
//    private val jwtUtil: JWTUtil
//) : WebSecurityConfigurerAdapter(){
//
//    override fun configure(http: HttpSecurity) {
//        http?.authorizeRequests()?.
//        //antMatchers("/topicos")?.hasAuthority("LEITURA_ESCRITA")?.
//        antMatchers(HttpMethod.POST, "/login")?.permitAll()?.
//        anyRequest()?.
//        authenticated()?.
//        and().
//        http?.addFilterBefore(JWTLoginFilter(authManager = authenticationManager(), jwtUtil = jwtUtil), UsernamePasswordAuthenticationFilter().javaClass)
//        http?.addFilterBefore(JWTAuthenticationFilter(jwtUtil = jwtUtil), OncePerRequestFilter::class.java)?
//        http?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)?
//    }
//
//    override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.userDetailsService(userDetailsService)?.passwordEncoder(bCryptPasswordEncoder())
//    }
//
//    @Bean
//    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
//        return BCryptPasswordEncoder()
//    }
//
//}