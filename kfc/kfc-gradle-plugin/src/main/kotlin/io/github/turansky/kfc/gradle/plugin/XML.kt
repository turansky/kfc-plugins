package io.github.turansky.kfc.gradle.plugin

import java.io.StringWriter
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilderFactory
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
            .parse(file)

        // optional, but recommended
        // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        document.getDocumentElement().normalize()

        val result = StreamResult(StringWriter())
        TransformerFactory.newInstance().newTransformer()
            .transform(DOMSource(document), result)

        return result.writer.toString()
    }
}
