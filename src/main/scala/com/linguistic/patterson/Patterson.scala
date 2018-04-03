package com.linguistic.patterson

import com.linguistic.patterson.agents.{GrammarPatternAgent, SentenceParsingAgent}
import com.linguistic.patterson.models.GrammarPattern

class Patterson {
    private val client = new NLPClient
    private val grammarData = new GrammarData("y", "n")
    private val spa = new SentenceParsingAgent(client)
    private val gpa = new GrammarPatternAgent(grammarData.patterns)

    def matchGrammar(text: String): List[List[GrammarPattern]] = {
        return this.spa.parseMulti(text).map(s ⇒ this.gpa.getMatchedPatterns(s))
    }
}