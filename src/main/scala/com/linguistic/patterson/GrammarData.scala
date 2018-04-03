package com.linguistic.patterson

import java.io.{BufferedInputStream, File, FileInputStream}

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.linguistic.patterson.models.{ExampleSentence, GrammarPattern, Reference}
import org.apache.commons.lang3.builder.{ReflectionToStringBuilder, ToStringStyle}

class ParsedGrammarData() {
    private var id: String = _
    private var description: String = _
    private var regex: String = _
    private var structures: Array[String] = _
    private var refs: Array[String] = _
    private var examples: Array[Array[String]] = _

    def getId(): String = this.id
    def setId(id: String): Unit = this.id = id
    def getDescription(): String = this.description
    def setDescription(description: String): Unit = this.description = description
    def getRegex(): String = this.regex
    def setRegex(regex: String): Unit = this.regex = regex
    def getStructures(): Array[String] = this.structures
    def setStructures(structures: Array[String]) = this.structures = structures
    def getRefs(): Array[String] = this.refs
    def setRefs(refs: Array[String]) = this.refs = refs
    def getExamples(): Array[Array[String]] = this.examples
    def setExamples(examples: Array[Array[String]]): Unit = this.examples = examples
}

class GrammarData(private var sourceLanguage: String, targetLanguage: String) {
    private val mapper = new ObjectMapper(new YAMLFactory())

    var patterns = Map[String, GrammarPattern]()

    try {
        val data : ParsedGrammarData =
            mapper.readValue(
                new BufferedInputStream(
                    this.getClass.getResourceAsStream("/resources/library/en/zh/yuelaiyue.yml")
                ),
            classOf[ParsedGrammarData])

        patterns += (data.getId() → new GrammarPattern(
            id = data.getId(),
            description = data.getDescription(),
            regex = data.getRegex(),
            structures = data.getStructures().toList,
            refs = data.getRefs().map(x ⇒ new Reference(null, null, null)).toList,
            examples = data.getExamples().map(x ⇒ new ExampleSentence(x(0), x(1), null)).toList
        ))
    } catch {
        case e: Exception ⇒ e.printStackTrace()
    }

}