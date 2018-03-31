package com.linguistic.patterson.models

import com.linguistic.patterson.agents.RegexMatchingAgent
import com.linguistic.patterson.util.RegexUtils
import com.linguistic.patterson.util.RegexUtils.Location

class GrammarPattern(id: String, structures: List[String], description: String, refs: List[Reference], regex: String, examples: List[ExampleSentence]) {
    val ram = new RegexMatchingAgent

    def `match`(sentence: Sentence): List[List[Location]] ={
        val matchLocations = this.ram.getMatchLocations(sentence.text, regex)
        val mergedGroups = RegexUtils.mergeMatchGroups(List(matchLocations))

        return mergedGroups
    }
}
