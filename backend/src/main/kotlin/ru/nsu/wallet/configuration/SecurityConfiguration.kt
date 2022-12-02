package ru.nsu.wallet.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import ru.nsu.wallet.controller.filter.JwtSecurityFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    private val excludeAuthenticationPaths = listOf(
        "/authenticate/login",
        "/authenticate/refresh",
        "/user/register",
        "/error"
    )

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .accessDeniedHandler { request: HttpServletRequest?, response: HttpServletResponse, exception: AccessDeniedException ->
                response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    exception.message
                )
            }
            .and()
            .authorizeRequests()
            .antMatchers(
                *excludeAuthenticationPaths.toTypedArray()
            ).permitAll()
            .anyRequest().authenticated()

        http.addFilterBefore(getJwtSecurityFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun getJwtSecurityFilter(): JwtSecurityFilter {
        return JwtSecurityFilter(excludeAuthenticationPaths)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }
}
