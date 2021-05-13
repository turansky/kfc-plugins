package com.test

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id_s: UserIdS,
    val id_i: UserIdI,
    val id_d: UserIdD,
)
