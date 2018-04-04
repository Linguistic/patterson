package com.linguistic.patterson.clients

import java.io.BufferedInputStream
import java.util.Properties

import com.linguistic.patterson.models.corenlp.Document
import edu.stanford.nlp.pipeline.{CoreDocument, StanfordCoreNLP}

class LocalClient(propertiesFile: String) extends TClient {
    private val config = new BufferedInputStream(this.getClass.getResourceAsStream(propertiesFile))
    private val props = new Properties()

    props.load(config)

    private val pipeline = new StanfordCoreNLP(props)

    def parse(text: String): Document = {
        val document = new CoreDocument(text)
        this.pipeline.annotate(document)
        new Document(document)
    }
}