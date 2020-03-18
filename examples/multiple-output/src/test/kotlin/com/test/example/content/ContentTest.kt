package com.test.example.content

import kotlin.test.Test
import kotlin.test.assertEquals

class ContentTest {
    @Test
    fun whatIsTheMeaningOfLife() {
        val app = Content()
        assertEquals(app.whatIsTheMeaningOfLife(), "42")
    }
}
