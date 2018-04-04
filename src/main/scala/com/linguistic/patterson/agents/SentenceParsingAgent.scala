package com.linguistic.patterson.agents

import java.util.regex.Pattern

import com.linguistic.patterson.models.Token
import com.linguistic.patterson.clients.TClient
import com.linguistic.patterson.models.corenlp.{Sentence, Token}

class SentenceParsingAgent(client: TClient) {
    private final val SENTENCE_SPLIT_REGEX_BASE : String = "[.](?!\\d)|[!?]+|[。]|[！？]+"
    private final val SENTENCE_SPLIT_REGEX : Pattern = Pattern.compile(s"(${SENTENCE_SPLIT_REGEX_BASE})", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE)
    private final val SENTENCE_PUNCT_MATCH_REGEX  : Pattern = Pattern.compile(s"^${SENTENCE_SPLIT_REGEX_BASE}$$", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE)

    def parse(text: String): Sentence = {
        val preprocessedText = text.replaceAll("\\s+", "")
        val parsedText = this.client.parse(preprocessedText)
        val rootToken = new Token(0, "", 0, 0, "", "")
        val firstSentence = parsedText.sentences(0)
        val basicDeps = firstSentence.basicDependencies

        var tokens = firstSentence.tokens

        tokens = rootToken :: tokens

        basicDeps.foreach(d ⇒ {
            val governor = tokens(d.governor)
            val dependent = tokens(d.dependent)
            val dependencyType = d.dep

            dependent.setGovernor(governor, dependencyType)
        })

        return new Sentence(preprocessedText, tokens, basicDeps)
    }

    def parseMulti(text: String): List[Sentence] = {
        val sentenceTexts = this.splitTextSentences(text)
        return sentenceTexts.map(st ⇒ this.parse(st))
    }

    /**
      * Given a string of text, split into an array of sentences in the text
      * @param text
      */
    def splitTextSentences(text: String): List[String] = {
        val splitText = text.split(SENTENCE_SPLIT_REGEX_BASE)

        var sentences = List[String]()
        var currentSentence = splitText(0).replaceAll("^\\s+", "")

        for (i ← 1 to splitText.length - 1) {
            val chunk = splitText(i).replaceAll("^\\s+", "")

            if (chunk.trim.length > 0 && SENTENCE_PUNCT_MATCH_REGEX.matcher(chunk).matches()) {
                currentSentence += chunk
            } else {
                sentences :+= currentSentence
                currentSentence = chunk
            }
        }

        sentences :+= currentSentence

        return sentences
    }
}
