package com.linguistic.patterson.models.corenlp

import edu.stanford.nlp.pipeline.{CoreDocument, CoreSentence}

import scala.collection.JavaConverters._

private object Document {
    private def getTokensFromSentence(sentence: CoreSentence): List[Token] = {
        sentence.tokens()
            .asScala
            .map(t ⇒ new Token(t))
            .toList
    }

    private def getDependenciesFromSentence(sentence: CoreSentence): List[Dependency] = {
        sentence
            .dependencyParse()
            .edgeListSorted()
            .asScala
            .map(e ⇒ Dependency(
                dep = e.getRelation.toString,
                governor = e.getGovernor.index(),
                dependent = e.getDependent.index()
            )).toList
    }
}

class Document(val sentences: List[Sentence]) {
    def this(coreDocument: CoreDocument) {
        this(coreDocument.sentences().asScala.map(
            s ⇒ new Sentence(
                s.text(),
                Document.getTokensFromSentence(s),
                Document.getDependenciesFromSentence(s)
            )
        ).toList)
    }
}