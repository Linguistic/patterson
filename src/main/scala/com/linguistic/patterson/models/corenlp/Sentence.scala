package com.linguistic.patterson.models.corenlp

class Sentence(original: String, val tokens: List[Token], val basicDependencies: List[Dependency]) {
    // TODO: change to support non-chinese languages
    def text: String = this.tokens.map(t ⇒ t.word).mkString("")
}
