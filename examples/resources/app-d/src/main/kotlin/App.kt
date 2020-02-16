fun App(): String = AAA() + BBB() + CCC()

fun icon(): String = require("aaa.svg")

external fun require(path: String): String
