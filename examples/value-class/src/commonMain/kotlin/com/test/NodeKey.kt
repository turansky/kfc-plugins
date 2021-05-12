package com.test

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class NodeKeyS(
    val value: String
)

@JvmInline
@Serializable
value class NodeKeyI(
    val value: Int
)

@JvmInline
@Serializable
value class NodeKeyD(
    val value: Double
)
