package ecma.test.longbigint

import js.core.n
import js.core.unaryMinus
import kotlin.test.Test
import kotlin.test.assertIs


class LongBigIntTest {
    @Test
    fun `long is bigint`() {
        assertIsBigInt(-1_234_567L)
        assertIsBigInt(-42L)
        assertIsBigInt(-1L)
        assertIsBigInt(0L)
        assertIsBigInt(1L)
        assertIsBigInt(42L)
        assertIsBigInt(1_234_567L)
    }

    @Test
    fun `bigint is long`() {
        assertIs<Long>(-1_234_567.n)
        assertIs<Long>(-42.n)
        assertIs<Long>(-1.n)
        assertIs<Long>(0.n)
        assertIs<Long>(1.n)
        assertIs<Long>(42.n)
        assertIs<Long>(1_234_567.n)
    }
}
