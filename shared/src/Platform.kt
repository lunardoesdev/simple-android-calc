package org.example.simpleandroidcalc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
