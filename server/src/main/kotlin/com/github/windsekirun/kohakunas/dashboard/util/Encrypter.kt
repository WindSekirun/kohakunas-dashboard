package com.github.windsekirun.kohakunas.dashboard.util

import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object Encrypter {

    fun encrypt(password: String, plainText: String): String = try {
        val secretKeySpec = generateKeySpec()
        val iv = ByteArray(BLOCK_SIZE)
        val secureRandom = SecureRandom.getInstanceStrong()
        secureRandom.nextBytes(iv)
        val ivParameterSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
        val encrypted = cipher.doFinal(plainText.toByteArray())
        String(Base64.getEncoder().encode(iv + encrypted))
    } catch (e: Exception) {
        throw RuntimeException("Error while encrypt")
    }

    fun decrypt(password: String, text: String): String = try {
        val secretKeySpec = generateKeySpec()
        val cipherText = Base64.getDecoder().decode(text)
        val iv = cipherText.copyOfRange(0, BLOCK_SIZE)
        val ivParameterSpec = IvParameterSpec(iv)
        val encrypted = cipherText.copyOfRange(BLOCK_SIZE, cipherText.size)

        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
        String(cipher.doFinal(encrypted))
    } catch (e: Exception) {
        throw RuntimeException("Error while decrypt")
    }

    private fun generateKeySpec() = try {
        val factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM)
        val spec = PBEKeySpec(key, salt, ITERATION_COUNT, DIGEST_BIT_LENGTH)
        SecretKeySpec(factory.generateSecret(spec).encoded, "AES")
    } catch (e: Exception) {
        println("Error while generating key: $e")
        null
    }

    private const val SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA256"
    private const val BLOCK_SIZE = 16
    private const val ITERATION_COUNT = 1024
    private const val DIGEST_BIT_LENGTH = 256
}