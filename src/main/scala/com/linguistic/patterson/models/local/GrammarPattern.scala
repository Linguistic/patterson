package com.linguistic.patterson.models.local

import com.linguistic.patterson.agents.RegexMatchingAgent
import com.linguistic.patterson.models.corenlp.Sentence
import com.linguistic.patterson.util.RegexUtils
import com.linguistic.patterson.util.RegexUtils.Location

class GrammarPattern(val id: String, val structures: List[String], val description: String, val refs: List[Reference], val regex: String, val examples: List[ExampleSentence]) {
    val ram = new RegexMatchingAgent

    def `match`(sentence: Sentence): List[List[Location]] = {
        val matchLocations = this.ram.getMatchLocations(sentence.text, regex)
        RegexUtils.mergeMatchGroups(List(matchLocations))
    }
}
