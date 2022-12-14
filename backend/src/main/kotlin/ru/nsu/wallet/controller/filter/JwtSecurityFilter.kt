package ru.nsu.wallet.controller.filter

import com.auth0.jwt.exceptions.JWTVerificationException
import mu.KotlinLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.AntPathMatcher
import org.springframework.util.PathMatcher
import org.springframework.web.filter.OncePerRequestFilter
import ru.nsu.wallet.entity.User
import ru.nsu.wallet.exception.AuthException
import ru.nsu.wallet.utils.JwtUtils
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtSecurityFilter(private val excludePath: List<String>) : OncePerRequestFilter() {

    private val pathMatcher: PathMatcher

    init {
        pathMatcher = AntPathMatcher("/")
    }

    companion object {
        private val LOGGER = KotlinLogging.logger {}
    }

    override fun shouldNotFilter(request: HttpServletRequest) = excludePath.stream()
        .anyMatch { pattern -> pathMatcher.match(pattern, request.requestURI) }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val accessTokenHeader =
                request.getHeader("Authorization")
                    ?: throw AuthException("Отсутствует обязательный header Authorization")
            verifyJWT(accessTokenHeader)

            val userDetails: UserDetails = User(id = JwtUtils.getUserIdFromToken(accessTokenHeader))

            val authentication = UsernamePasswordAuthenticationToken(
                userDetails,
                JwtUtils.getUserIdFromToken(accessTokenHeader),
                setOf()
            )

            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

            SecurityContextHolder.getContext().authentication = authentication
            filterChain.doFilter(request, response)
        } catch (exception: AuthException) {
            LOGGER.info { exception }
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.message)
        }
    }

    @Throws(AuthException::class)
    fun verifyJWT(accessToken: String) {
        try {
            JwtUtils.verifyToken(accessToken)
        } catch (exception: JWTVerificationException) {
            throw AuthException("Переданный JWT токен не валиден")
        }
    }
}