package com.linguistic.patterson.agents

import com.linguistic.patterson.data.patterns.PatternResolver
import com.linguistic.patterson.models.corenlp.Sentence
import com.linguistic.patterson.models.local.GrammarPattern
import com.linguistic.patterson.util.RegexUtils
import com.linguistic.patterson.util.RegexUtils.Location

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class GrammarPatternAgent(sourceLanguage: String, targetLanguage: String) {
  val grammarPatterns: List[GrammarPattern] = PatternResolver.resolve(sourceLanguage, targetLanguage)
  val grammarMap: Map[String, GrammarPattern] = grammarPatterns.iterator.map(x ⇒ (x.id, x)).toMap

  def getMatchedPatterns(sentence: Sentence): List[GrammarPattern] = {
    val matches = mutable.Map[String, List[List[Location]]]()
    val grammarMatches = new ListBuffer[GrammarPattern]()
    val filteredMatchesMap = mutable.Map[String, List[List[Location]]]()

//    // Loop through each stored grammar pattern and see the string contains the pattern
    this.grammarPatterns.foreach(pattern ⇒ {
      val m = pattern.matches(sentence)

      if (m != null) {
        matches(pattern.id) = m
      }
    })

    // Render the matches to a list instead of a map
    val allMatches = matches.flatMap(_._2)

    // Loop through each match
    for (m ← matches) {
      // Filter out overlapping group matches so each match is unique
      val filteredMatches = matches(m._1).filter(locationMatch ⇒ {
        for (otherMatch ← allMatches)
          if (RegexUtils.matchAContainsMatchB(otherMatch, locationMatch)) false
        true
      })

      // If we have unique matches, add those to our new map
      if (filteredMatches.nonEmpty)
        filteredMatchesMap(m._1) = filteredMatches
    }

    // Add back the original grammar pattern objects for the matched pattern
    for (m ← filteredMatchesMap) {
      grammarMatches += this.grammarMap(m._1)
    }

    grammarMatches.toList
  }
}
