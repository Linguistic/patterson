package com.linguistic.patterson

import java.io._
import java.util.jar.JarFile

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.linguistic.patterson.models.local.{ExampleSentence, GrammarPattern, Reference}
import com.linguistic.patterson.util.JacksonPOJO.{YAMLGrammarData, YAMLReference}

class GrammarData(private var sourceLanguage: String, targetLanguage: String) {
    private val mapper = new ObjectMapper(new YAMLFactory())
    private val grammarPaths = getGrammarFilesInResourceDirectory(s"library/$targetLanguage/$sourceLanguage/")
    private val referenceStream = this.getClass.getResourceAsStream(s"/library/$targetLanguage/$sourceLanguage/_refs.yml")

    private var referenceMap = Map[String, Reference]()

    if (referenceStream != null) {
        val refs = mapper.readValue(new BufferedInputStream(referenceStream), classOf[Array[YAMLReference]])

        refs.foreach(ref ⇒ {
            referenceMap += (ref.getId → Reference(ref.getId, ref.getName, ref.getUrl))
        })
    }

    val patterns: Map[String, GrammarPattern] = this.readGrammarFiles()

    private def readGrammarFiles(): Map[String, GrammarPattern] = {
        var patterns: Map[String, GrammarPattern] = Map[String, GrammarPattern]()

        this.grammarPaths.foreach(path ⇒ {
            try {
                val data : YAMLGrammarData =
                    mapper.readValue(
                        new BufferedInputStream(
                            this.getClass.getResourceAsStream(path)
                        ),
                        classOf[YAMLGrammarData])

                patterns += (data.getId() → new GrammarPattern(
                    id = data.getId(),
                    description = data.getDescription(),
                    regex = data.getRegex(),
                    structures = data.getStructures().toList,
                    refs = data.getRefs().map(x ⇒ this.referenceMap.getOrElse(x, null)).toList,
                    examples = data.getExamples().map(x ⇒ ExampleSentence(x(0), x(1), null)).toList
                ))
            } catch {
                case e: Exception ⇒ e.printStackTrace()
            }
        })

        patterns
    }

    private def getGrammarFilesInResourceDirectory(dir: String): List[String] = {
        val jarFile = new File(getClass.getProtectionDomain.getCodeSource.getLocation.getPath)
        val jar = new JarFile(jarFile)
        val entries = jar.entries

        var files = List[String]()

        while (entries.hasMoreElements) {
            val name = entries.nextElement.getName

            if (
                // Make sure it's in the correct directory
                name.startsWith(dir) &&
                // Make sure it's a YAML file
                name.contains(".yml") &&
                // Make sure it's not the references file
                !name.contains("_refs.yml") &&
                // Make sure that it's not a directory
                name(name.length - 1) != '/'
            ) files :+= s"/$name"
        }

        files
    }
}