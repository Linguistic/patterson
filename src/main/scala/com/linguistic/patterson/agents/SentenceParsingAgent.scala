package com.linguistic.patterson.agents

import java.util.regex.Pattern

import com.linguistic.patterson.clients.TClient
import com.linguistic.patterson.models.corenlp.{Sentence, Token}
import com.linguistic.patterson.util.StringUtils._

class SentenceParsingAgent(client: TClient) {
  private final val SENTENCE_SPLIT_REGEX_BASE: String = "[.](?!\\d)|[!?]+|[。]|[！？]+"
  private final val SENTENCE_PUNCT_MATCH_REGEX: Pattern =
    Pattern.compile(s"^$SENTENCE_SPLIT_REGEX_BASE$$", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE)

  def parse(text: String): Sentence = {
    val preprocessedText = text.replaceAll("\\s+", "")
    val parsedText = this.client.parse(preprocessedText)
    val rootToken = new Token(0, "", 0, 0, "", "")
    val firstSentence = parsedText.sentences.head
    val basicDeps = firstSentence.basicDependencies

    var tokens = firstSentence.tokens

    tokens = rootToken :: tokens

    basicDeps.foreach(d ⇒ {
      val governor = tokens(d.governor)
      val dependent = tokens(d.dependent)
      val dependencyType = d.dep

      dependent.setGovernor(governor, dependencyType)
    })

    new Sentence(preprocessedText, tokens, basicDeps)
  }

  def parseMulti(text: String): List[Sentence] = {
    this.splitTextSentences(text).map(st ⇒ this.parse(st))
  }

  /**
    * Given a string of text, split into an array of sentences in the text
    * @param text Text to split into sentences
    */
  def splitTextSentences(text: String): List[String] = {
    val splitText = text.split(s"($SENTENCE_SPLIT_REGEX_BASE)")
    var sentences = List[String]()
    var currentSentence = splitText(0).replaceAll("^\\s+", "")

    for (i ← 1 until splitText.length) {
      val chunk = splitText(i).replaceAll("^\\s+", "")

      if (chunk.trim.length > 0 && chunk.matchOnce(SENTENCE_PUNCT_MATCH_REGEX) != null) {
        currentSentence += chunk
      } else {
        sentences :+= currentSentence
        currentSentence = chunk
      }
    }

    sentences :+= currentSentence
    sentences
  }
}
