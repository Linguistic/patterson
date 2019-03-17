package com.linguistic.patterson.models.local

import com.linguistic.patterson.models.corenlp.Sentence
import com.linguistic.patterson.util.RegexUtils._

class GrammarPattern(val id: String,
                     val structures: List[String],
                     val description: String,
                     val refs: List[Reference],
                     val examples: List[ExampleSentence],
                     val regex: String = null) {

  def matches(sentence: Sentence): List[List[Location]] = {
    throw new Error("No matcher implemented")
  }

}
