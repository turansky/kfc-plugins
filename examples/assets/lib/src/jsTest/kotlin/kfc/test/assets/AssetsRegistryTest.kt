package kfc.test.assets

import kotlin.test.Test
import kotlin.test.assertEquals

class AssetsRegistryTest {
    @Test
    fun oneLine() {
        assertEquals(AssetRegistry["system/close"], "<close/>")
    }

    @Test
    fun multiLine() {
        assertEquals(AssetRegistry["multiline/close"], "<close><child/></close>")
    }
}
