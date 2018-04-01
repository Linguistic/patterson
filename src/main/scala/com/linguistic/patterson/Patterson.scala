package com.linguistic.patterson

import com.linguistic.patterson.agents.{GrammarPatternAgent, SentenceParsingAgent}
import com.linguistic.patterson.models.{GrammarPattern, Sentence}

class Patterson {
    private val client = new NLPClient
    private val spa = new SentenceParsingAgent(client)
    private val gpa = new GrammarPatternAgent(Map[String, GrammarPattern]())

    def matchGrammar(text: String): List[List[GrammarPattern]] = {
        val sentences = this.spa.parseMulti(text)
        return sentences.map(s ⇒ this.gpa.getMatchedPatterns(s))
    }
}
