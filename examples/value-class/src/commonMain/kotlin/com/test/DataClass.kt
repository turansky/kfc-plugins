package com.test

import kotlinx.serialization.Serializable

@Serializable
data class DataClass(
    val value: Point
)

@Serializable
data class Point(
    val x: Double,
    val y: Double,
)
