package com.linguistic.patterson.agents

import com.linguistic.patterson.models.{GrammarPattern, Sentence}

class GrammarPatternAgent(grammarPatternMap: Map[String, GrammarPattern]) {
    private def mapMatches(sentence: Sentence): Map[String, GrammarPattern] = {
        val matches = this.grammarPatternMap.filter(entry ⇒ entry._2.`match`(sentence) != null)
//        val pureMatches = Map[String, GrammarPattern]()
//        val allMatches = matches.values.toList.flatten

        return matches
    }
}
