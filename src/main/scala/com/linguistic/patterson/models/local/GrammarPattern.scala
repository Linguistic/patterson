package com.linguistic.patterson.models.local

import com.linguistic.patterson.models.corenlp.Sentence
import com.linguistic.patterson.util.RegexUtils._

class GrammarPattern(val id: String, val structures: List[String], val description: String, val refs: List[Reference], val regex: String, val examples: List[ExampleSentence]) {
    def `match`(sentence: Sentence): List[List[Location]] = {
        val matchLocations = getMatchLocations(sentence.text, regex)
        mergeMatchGroups(List(matchLocations))
    }
}
