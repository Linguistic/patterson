package com.linguistic.patterson.models.corenlp

import edu.stanford.nlp.ling.CoreLabel

class Token(val index: Int, val word: String, val beginPosition: Int, val endPosition: Int, val pos: String, val ner: String) {
    var governor: Token = _
    var dependencyType: String = _
    var dependents: List[Token] = List[Token]()

    def this(label: CoreLabel) {
        this(label.index(), label.word(), label.beginPosition(), label.endPosition(), label.tag(), label.ner())
    }

    def setGovernor(governor: Token, dependencyType: String): Unit = {
        this.governor = governor
        this.dependencyType = dependencyType
        governor.dependents :+= this
    }
}
