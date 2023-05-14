package io.github.turansky.kfc.gradle.plugin

import org.w3c.dom.Node
import org.w3c.dom.NodeList
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

        trimWhitespace(document.documentElement)

        val result = StreamResult(StringWriter())
        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.METHOD, "xml")
        transformer.setOutputProperty(OutputKeys.INDENT, "no")
        transformer.transform(DOMSource(document), result)

        return result.writer.toString()
            .substringAfter("?>")
    }
}

private fun trimWhitespace(node: Node) {
    val children: NodeList = node.childNodes
    for (i in 0 until children.length) {
        val child: Node = children.item(i)
        if (child.nodeType == Node.TEXT_NODE) {
            child.textContent = child.textContent.trim()
        }

        trimWhitespace(child)
    }
}
