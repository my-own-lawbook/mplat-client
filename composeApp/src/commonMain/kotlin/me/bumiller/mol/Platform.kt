package me.bumiller.mol

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform