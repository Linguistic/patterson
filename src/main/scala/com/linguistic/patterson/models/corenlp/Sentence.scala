package com.linguistic.patterson.models.corenlp

class Sentence(original: String, val tokens: List[Token], val basicDependencies: List[Dependency]) {
    def text = this.tokens.map(t ⇒ t.word).mkString("")
}
