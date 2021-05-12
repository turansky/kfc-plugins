package com.test

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class UserIdS(
    val id: String
)

@JvmInline
@Serializable
value class UserIdI(
    val id: Int
)

@JvmInline
@Serializable
value class UserIdD(
    val id: Double
)
