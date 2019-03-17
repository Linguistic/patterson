package com.linguistic.patterson

import com.linguistic.patterson.agents.{GrammarPatternAgent, SentenceParsingAgent}
import com.linguistic.patterson.clients.TClient
import com.linguistic.patterson.constants.Language
import com.linguistic.patterson.models.local.GrammarPattern

class Patterson(client: TClient) {
  private val spa = new SentenceParsingAgent(client)
  private val gpa = new GrammarPatternAgent(Language.CHINESE, Language.ENGLISH)

  def matchGrammar(text: String): List[List[GrammarPattern]] = {
    this.spa.parseMulti(text).map(s ⇒ this.gpa.getMatchedPatterns(s))
  }
}
