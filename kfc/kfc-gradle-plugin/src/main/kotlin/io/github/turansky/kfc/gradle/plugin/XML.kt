package io.github.turansky.kfc.gradle.plugin

import java.io.StringWriter
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


internal object XML {
    fun compressedContent(
        file: String,
    ): String {
        val document = DocumentBuilderFactory.newInstance()
            .also { it.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true) }
            .newDocumentBuilder()
            .parse(file.byteInputStream())

        // optional, but recommended
        // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        document.documentElement.normalize()

        val result = StreamResult(StringWriter())
        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.METHOD, "html")
        transformer.setOutputProperty(OutputKeys.INDENT, "no")
        transformer.transform(DOMSource(document), result)

        return result.writer.toString()
    }
}
