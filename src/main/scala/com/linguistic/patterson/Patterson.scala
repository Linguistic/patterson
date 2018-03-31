package com.linguistic.patterson

import com.linguistic.patterson.agents.SentenceParsingAgent
import com.linguistic.patterson.models.Sentence

class Patterson {
    private val client = new NLPClient
    private val sentenceParser = new SentenceParsingAgent(client)
    private val matchReducer = null

    def matchGrammar(text: String): List[Sentence] = {
        val sentences = this.sentenceParser.parseMulti(text)
        return sentences.map(s ⇒ this.matchReducer)
    }
}
