package kfc.test.assets

import kotlin.test.Test
import kotlin.test.assertEquals

class AssetsRegistryTest {
    @Test
    fun oneLine() {
        assertEquals(AssetRegistry.getSymbolId("system/close"), "kfc-gis__system__close")
    }

    @Test
    fun multiLine() {
        assertEquals(AssetRegistry.getSymbolId("multiline/close"), "kfc-gis__multiline__close")
    }
}
