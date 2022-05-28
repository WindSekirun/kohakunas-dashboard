package com.github.windsekirun.kohakunas.dashboard.util

import com.github.windsekirun.kohakunas.dashboard.config.Config
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object Encrypter : KoinComponent {
    private val config by inject<Config>()
    private val key by lazy { config.keySecret.toByteArray() }

    fun encrypt(plainText: String): String = try {
        val secretKeySpec = generateKeySpec()
        val cipher = Cipher.getInstance(SECRET_KEY_ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val encrypted = cipher.doFinal(plainText.toByteArray())
        String(Base64.getEncoder().encode(encrypted))
    } catch (e: Exception) {
        throw RuntimeException("Error while encrypt")
    }

    fun decrypt(text: String): String = try {
        val secretKeySpec = generateKeySpec()
        val cipherText = Base64.getDecoder().decode(text)
        val cipher = Cipher.getInstance(SECRET_KEY_ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        String(cipher.doFinal(cipherText))
    } catch (e: Exception) {
        throw RuntimeException("Error while decrypt")
    }

    private fun generateKeySpec() = try {
        SecretKeySpec(key, SECRET_KEY_ALGORITHM)
    } catch (e: Exception) {
        println("Error while generating key: $e")
        null
    }

    private const val SECRET_KEY_ALGORITHM = "Blowfish"
}