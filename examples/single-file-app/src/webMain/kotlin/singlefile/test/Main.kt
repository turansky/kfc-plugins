package singlefile.test

import web.console.console

fun main() {
    console.log("A:", A.aaa())
    console.log("B:", B.bbb())
    console.log("C:", C.ccc())
    console.log("ABC:", A.aaa() + B.bbb() + C.ccc())
}