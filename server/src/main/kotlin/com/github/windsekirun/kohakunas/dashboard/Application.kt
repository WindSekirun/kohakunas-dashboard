package com.github.windsekirun.kohakunas.dashboard

import com.auth0.jwt.JWTVerifier
import com.github.windsekirun.kohakunas.dashboard.api.injection.ApiInjection
import com.github.windsekirun.kohakunas.dashboard.config.Config
import com.github.windsekirun.kohakunas.dashboard.database.DatabaseProvider
import com.github.windsekirun.kohakunas.dashboard.database.DatabaseProviderContract
import com.github.windsekirun.kohakunas.dashboard.database.injection.DaoInjection
import com.github.windsekirun.kohakunas.dashboard.modules.auth.JwtConfig
import com.github.windsekirun.kohakunas.dashboard.modules.auth.TokenProvider
import com.github.windsekirun.kohakunas.dashboard.modules.injection.ModulesInjection
import com.github.windsekirun.kohakunas.dashboard.util.PasswordManager
import com.github.windsekirun.kohakunas.dashboard.util.PasswordManagerContract
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.util.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.koin.dsl.module
import org.koin.ktor.ext.Koin

@ObsoleteCoroutinesApi
@KtorExperimentalAPI
fun main(args: Array<String>) {
    val environment = System.getenv()["ENVIRONMENT"] ?: handleDefaultEnvironment()
    val config = extractConfig(environment, HoconApplicationConfig(ConfigFactory.load()))
    JwtConfig.initialize(config)

    embeddedServer(Netty, port = config.port) {
        println("Starting instance in 0.0.0.0:${config.port}")
        module {
            install(Koin) {
                modules(
                    module {
                        single { config }
                        single<DatabaseProviderContract> { DatabaseProvider() }
                        single<JWTVerifier> { JwtConfig.verifier }
                        single<PasswordManagerContract> { PasswordManager }
                        single<TokenProvider> { JwtConfig }
                    },
                    ApiInjection.koinBeans,
                    ModulesInjection.koinBeans,
                    DaoInjection.koinBeans
                )
            }
            main()
        }
    }.start(wait = true)
}

fun handleDefaultEnvironment(): String {
    println("Falling back to default environment 'dev'")
    return "dev"
}

fun Application.main() {
    module()
}

@KtorExperimentalAPI
fun extractConfig(environment: String, hoconConfig: HoconApplicationConfig): Config {
    val hoconEnvironment = hoconConfig.config("ktor.deployment.$environment")
    val password = System.getenv().getOrDefault("DATABASE_PASSWORD", null)
    requireNotNull(password) { "Password can't be null, assign DATABASE_PASSWORD in environment variables." }

    val jwtSecret = System.getenv().getOrDefault("JWT_SECRET", null)
    requireNotNull(jwtSecret) { "jwtSecret can't be null, assign JWT_SECRET in environment variables." }

    val registerSecret = System.getenv().getOrDefault("REGISTER_SECRET", null)
    requireNotNull(registerSecret) { "registerSecret can't be null, assign REGISTER_SECRET in environment variables." }
    require(registerSecret.matches(UUID_REGEX)) { "REGISTER SECRET must be in the form corresponding to UUID4." }

    return Config(
        hoconEnvironment.property("port").getString().toInt(),
        hoconEnvironment.property("databaseHost").getString(),
        hoconEnvironment.property("databasePort").getString(),
        hoconEnvironment.property("databaseUser").getString(),
        hoconEnvironment.property("databaseName").getString(),
        password,
        jwtSecret,
        registerSecret
    )
}

private val UUID_REGEX =
    "[a-f0-9]{8}-?[a-f0-9]{4}-?4[a-f0-9]{3}-?[89ab][a-f0-9]{3}-?[a-f0-9]{12}".toRegex(RegexOption.IGNORE_CASE)