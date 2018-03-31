package com.linguistic.patterson.models

class Sentence(original: String, tokens: List[Token]) {
    def text = this.tokens.map(t ⇒ t.word).mkString("")
}
