package ecma.test.longbigint

import js.core.BigInt
import kotlin.contracts.contract
import kotlin.test.asserter

@JsName("typeof")
external fun jsTypeof(o: Any?): String

@OptIn(kotlin.contracts.ExperimentalContracts::class)
inline fun assertIsBigInt(
    value: Any?,
) {
    contract {
        returns() implies (value is BigInt)
    }

    assertIsOfType(value, jsTypeof(value) == "bigint")
}

@PublishedApi
internal fun assertIsOfType(
    value: Any?,
    result: Boolean,
) {
    asserter.assertTrue(
        { "Expected value to be of type <BigInt>, actual <${value?.let { it::class }}>." },
        result,
    )
}
