package com.linguistic.patterson.data.patterns.zh

import com.linguistic.patterson.models.local.{ExampleSentence, GrammarPattern}
import com.linguistic.patterson.data.references.AllSetChinese
import com.linguistic.patterson.models.corenlp.Sentence
import com.linguistic.patterson.util.RegexUtils
import com.linguistic.patterson.util.RegexUtils.{getMatchLocations, mergeMatchGroups}

class YueLaiYuePattern
    extends GrammarPattern(
      id = "yuelaiyue",
      structures = List("Subj. + 越来越 + Adj. + 了", "Subj. + 越来越 + Verb + 了"),
      regex = "(越来越)[^了]+(了)",
      refs = List(),
      examples = List(ExampleSentence("天气越来越冷了。", "The weather is getting colder and colder.", ref = AllSetChinese())),
      description =
        """
          越来越 (yuèláiyuè) is used frequently in Chinese to express that some quality or state is increasing with time, and
          is often translated into English as "more and more." This is the simple form of this pattern, which uses 来, but
          there is also a more complex one (which uses two different adjectives/verbs).
        """.stripMargin
    ) {

  override def matches(sentence: Sentence): List[List[RegexUtils.Location]] = {
    val matchLocations = getMatchLocations(sentence.text, regex)
    mergeMatchGroups(List(matchLocations))
  }
}

object YueLaiYuePattern {
  def apply() = new YueLaiYuePattern()
}
