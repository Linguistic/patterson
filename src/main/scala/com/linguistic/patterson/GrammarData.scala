package com.linguistic.patterson

import java.io._

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.linguistic.patterson.models.local.{ExampleSentence, GrammarPattern, Reference}
import com.linguistic.patterson.util.JacksonPOJO.ParsedGrammarData

class GrammarData(private var sourceLanguage: String, targetLanguage: String) {
    private val mapper = new ObjectMapper(new YAMLFactory())

    var patterns = Map[String, GrammarPattern]()

    println(getFilesInResourceDirectory("/library/en/zh/"))

    try {
        val data : ParsedGrammarData =
            mapper.readValue(
                new BufferedInputStream(
                    this.getClass.getResourceAsStream("/library/en/zh/yuelaiyue.yml")
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

    private def getFilesInResourceDirectory(dir: String): List[String] = {
        var filenames = List[String]()

        try {
            val in = this.getClass.getResourceAsStream(dir)
            val br = new BufferedReader(new InputStreamReader(in))

            try {
                Stream
                    .continually(br.readLine)
                    .takeWhile(_ != null)
                    .foreach(println(_))
            } catch {
                case e ⇒ e.printStackTrace()
            } finally {
                if (in != null) in.close()
                if (br != null) br.close()
            }
        }

        return filenames
    }
}