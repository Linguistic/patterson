package com.linguistic.patterson.agents

import com.linguistic.patterson.models.{GrammarPattern, Sentence}
import com.linguistic.patterson.util.RegexUtils
import com.linguistic.patterson.util.RegexUtils.Location

import scala.collection.mutable

class GrammarPatternAgent(grammarPatternMap: Map[String, GrammarPattern]) {
    def getMatchedPatterns(sentence: Sentence): List[GrammarPattern] = {
        val matches = mutable.Map[String, List[List[Location]]]()
        var grammarMatches = List[GrammarPattern]()
        val filteredMatchesMap = mutable.Map[String, List[List[Location]]]()

        // Loop through each stored grammar pattern and see the string contains the pattern
        this.grammarPatternMap.foreach(entry ⇒ {
            val m = entry._2.`match`(sentence)
            if (m != null) matches(entry._1) = m
        })

        // Render the matches to a list instead of a map
        val allMatches = matches.map(_._2).flatten

        // Loop through each match
        for (m ← matches) {
            // Filter out overlapping group matches so each match is unique
            val filteredMatches = matches(m._1).filter(locationMatch ⇒ {
                for (otherMatch ← allMatches)
                    if (RegexUtils.matchAContainsMatchB(otherMatch, locationMatch)) false
                true
            })

            // If we have unique matches, add those to our new map
            if (filteredMatches.size > 0)
                filteredMatchesMap(m._1) = filteredMatches
        }

        // Add back the original grammar pattern objects for the matched pattern
        for (m ← filteredMatchesMap)
            grammarMatches :+= this.grammarPatternMap(m._1)

        return grammarMatches
    }
}
