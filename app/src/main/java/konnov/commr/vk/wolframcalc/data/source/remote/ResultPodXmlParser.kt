package konnov.commr.vk.wolframcalc.data.source.remote

import konnov.commr.vk.wolframcalc.data.ResultPod
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource

import java.io.StringReader
import java.util.ArrayList

import javax.xml.parsers.DocumentBuilderFactory

object ResultPodXmlParser {
    fun parseResultXml(resultXml: String): ArrayList<ResultPod>? {

        val resultPods = ArrayList<ResultPod>()

        try {
            val document = loadXMLFromString(resultXml)
            document.documentElement.normalize()

            val rootElement = document.getElementsByTagName("queryresult").item(0) as Element

            val pods = rootElement.getElementsByTagName("pod")

            for (count in 0 until pods.length) {

                val pod = pods.item(count)
                val subPodElement = (pod as Element).getElementsByTagName("subpod").item(0) as Element
                val descriptionElement = subPodElement.getElementsByTagName("plaintext").item(0) as Element

                val resultPod = ResultPod()

                // Set result pod title and description

                resultPod.title = pod.getAttribute("title")

                // Set description only if it is available

                if (descriptionElement.textContent.isNotEmpty()) {
                    resultPod.description = descriptionElement.textContent
                    resultPods.add(resultPod)
                }

            }

            if (resultPods.size > 0)
                return resultPods

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    @Throws(Exception::class)
    fun loadXMLFromString(xml: String): Document {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val source = InputSource(StringReader(xml))
        return builder.parse(source)
    }
}
