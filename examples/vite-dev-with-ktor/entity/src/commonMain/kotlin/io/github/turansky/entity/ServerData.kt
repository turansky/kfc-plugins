package io.github.turansky.entity

import kotlinx.serialization.Serializable

@Serializable
data class ServerData(
    val serverDir: String,
    val staticDir: String,
    val entryFile: String,
)
