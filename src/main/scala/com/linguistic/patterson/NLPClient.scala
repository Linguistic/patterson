package com.linguistic.patterson

import java.io.{BufferedInputStream, File}
import java.util.Properties

import edu.stanford.nlp.pipeline.{CoreDocument, StanfordCoreNLP}

class NLPClient {
    private val config = new BufferedInputStream(this.getClass.getResourceAsStream("/StanfordCoreNLP-chinese.properties"))
    private val props = new Properties()

    props.load(config)

    private val pipeline = new StanfordCoreNLP(props)

    def parse(text: String): CoreDocument = {
        val document = new CoreDocument(text)
        this.pipeline.annotate(document)
        return document
    }
}