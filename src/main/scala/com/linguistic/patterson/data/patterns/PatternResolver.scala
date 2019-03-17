package com.linguistic.patterson.data.patterns

import com.linguistic.patterson.constants.Language
import com.linguistic.patterson.data.patterns.zh.YueLaiYuePattern
import com.linguistic.patterson.models.local.GrammarPattern

object PatternResolver {
  def resolve(sourceLanguageCode: String, targetLanguageCode: String): List[GrammarPattern] = {
    sourceLanguageCode match {
      case Language.CHINESE ⇒
        List(
          YueLaiYuePattern()
        )
    }
  }
}
