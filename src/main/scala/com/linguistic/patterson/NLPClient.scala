package com.linguistic.patterson;

import edu.stanford.nlp.pipeline.CoreDocument
import edu.stanford.nlp.pipeline.StanfordCoreNLP

import java.util.Properties

class NLPClient {
    private val props = new Properties()
    props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref,kbp,quote");
    private val pipeline = new StanfordCoreNLP(props)

    def parse(text: String): CoreDocument = {
        val document = new CoreDocument(text)
        this.pipeline.annotate(document)
        return document
    }
}
