package com.test.example.app

import kotlin.test.Test
import kotlin.test.assertEquals

class AppTest {
    @Test
    fun whatIsTheMeaningOfLife() {
        val app = App()
        assertEquals(app.whatIsTheMeaningOfLife(), "42")
    }
}
