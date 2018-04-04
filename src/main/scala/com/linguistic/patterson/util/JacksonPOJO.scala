package com.linguistic.patterson.util

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

object JacksonPOJO {
    @JsonIgnoreProperties(ignoreUnknown = true)
    class JSONDependency() {
        private var dep: String = _
        private var governor: Int = _
        private var governorGloss: String = _
        private var dependent: Int = _
        private var dependentGloss: String = _

        def getDep : String = this.dep
        def setDep(dep: String): Unit = this.dep = dep
        def getGovernor : Int = this.governor
        def setGovernor(governor: Int): Unit = this.governor = governor
        def getGovernorGloss : String = this.governorGloss
        def setGovernorGloss(governorGloss: String): Unit = this.governorGloss = governorGloss
        def getDependent : Int = this.dependent
        def setDependent(dependent: Int): Unit = this.dependent = dependent
        def getDependentGloss : String = this.dependentGloss
        def setDependentGloss(dependentGloss: String): Unit = this.dependentGloss = dependentGloss
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class JSONToken() {
        private var index: Int = _
        private var word: String = _
        private var originalText: String = _
        private var lemma: String = _
        private var characterOffsetBegin: Int = _
        private var characterOffsetEnd: Int = _
        private var pos: String = _
        private var ner: String = _

        def getIndex: Int = this.index
        def setIndex(index: Int): Unit = this.index = index
        def getWord: String = this.word
        def setWord(word: String): Unit = this.word = word
        def getOriginalText: String = this.originalText
        def setOriginalText(originalText: String): Unit = this.originalText = originalText
        def getLemma: String = this.lemma
        def setLemma(lemma: String): Unit = this.lemma = lemma
        def getCharacterOffsetBegin: Int = this.characterOffsetBegin
        def setCharacterOffsetBegin(characterOffsetBegin: Int): Unit = this.characterOffsetBegin = characterOffsetBegin
        def getCharacterOffsetEnd: Int = this.characterOffsetEnd
        def setCharacterOffsetEnd(characterOffsetEnd: Int): Unit = this.characterOffsetEnd = characterOffsetEnd
        def getPos: String = this.pos
        def setPos(pos: String): Unit = this.pos = pos
        def getNer: String = this.ner
        def setNer(ner: String): Unit = this.ner = ner
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class JSONSentence() {
        private var index : Int = _
        private var basicDependencies : Array[JSONDependency] = Array[JSONDependency]()
        private var tokens : Array[JSONToken] = Array[JSONToken]()

        def getIndex: Int = this.index
        def setIndex(index: Int): Unit = this.index = index
        def getTokens: Array[JSONToken] = this.tokens
        def setTokens(tokens: Array[JSONToken]): Unit = this.tokens = tokens
        def getBasicDependencies: Array[JSONDependency] = this.basicDependencies
        def setBasicDependencies(basicDependencies: Array[JSONDependency]): Unit = this.basicDependencies = basicDependencies
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class JSONResponse() {
        private var sentences : Array[JSONSentence] = Array[JSONSentence]()

        def getSentences: Array[JSONSentence] = this.sentences
        def setSentences(sentences: Array[JSONSentence]): Unit = this.sentences = sentences
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class YAMLGrammarData() {
        private var id: String = _
        private var description: String = _
        private var regex: String = _
        private var structures: Array[String] = _
        private var refs: Array[String] = _
        private var examples: Array[Array[String]] = _

        def getId: String = this.id
        def setId(id: String): Unit = this.id = id
        def getDescription: String = this.description
        def setDescription(description: String): Unit = this.description = description
        def getRegex: String = this.regex
        def setRegex(regex: String): Unit = this.regex = regex
        def getStructures: Array[String] = this.structures
        def setStructures(structures: Array[String]): Unit = this.structures = structures
        def getRefs: Array[String] = this.refs
        def setRefs(refs: Array[String]): Unit = this.refs = refs
        def getExamples: Array[Array[String]] = this.examples
        def setExamples(examples: Array[Array[String]]): Unit = this.examples = examples
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class YAMLReference() {
        private var name: String = _
        private var url: String = _
        private var id: String = _

        def getId: String = this.id
        def setId(id: String): Unit = this.id = id
        def getName: String = this.name
        def setName(name: String): Unit = this.name = name
        def getUrl: String = this.url
        def setUrl(url: String): Unit = this.url = url
    }
}