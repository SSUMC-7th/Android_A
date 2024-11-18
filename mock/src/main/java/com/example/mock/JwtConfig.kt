package com.example.mock

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.Date

object JwtConfig {
    private const val SECRET = "your-secret-key"
    private const val REFRESH_SECRET = "your-refresh-secret-key"
    private const val ISSUER = "mock-server"
    private const val ACCESS_TOKEN_VALIDITY = 1000 * 60 * 30 // 30 minutes
    private const val REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 14 // 14 days

    private val accessAlgorithm = Algorithm.HMAC256(SECRET)
    private val refreshAlgorithm = Algorithm.HMAC256(REFRESH_SECRET)

    fun createTokenPair(user: User): TokenPair {
        val accessToken = JWT.create().withSubject("Authentication").withIssuer(ISSUER)
            .withClaim("userId", user.id).withClaim("email", user.email)
            .withExpiresAt(Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
            .sign(accessAlgorithm)

        val refreshToken =
            JWT.create().withSubject("Refresh").withIssuer(ISSUER).withClaim("userId", user.id)
                .withExpiresAt(Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .sign(refreshAlgorithm)

        return TokenPair(accessToken, refreshToken)
    }

    fun verifyAccessToken(token: String): DecodedJWT =
        JWT.require(accessAlgorithm).withIssuer(ISSUER).build().verify(token)

    fun verifyRefreshToken(token: String): DecodedJWT =
        JWT.require(refreshAlgorithm).withIssuer(ISSUER).build().verify(token)
}

data class TokenPair(
    val accessToken: String, val refreshToken: String
)