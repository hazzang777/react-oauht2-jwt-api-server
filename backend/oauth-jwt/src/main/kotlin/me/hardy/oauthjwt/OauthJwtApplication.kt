package me.hardy.oauthjwt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OauthJwtApplication

fun main(args: Array<String>) {
    runApplication<OauthJwtApplication>(*args)
}
