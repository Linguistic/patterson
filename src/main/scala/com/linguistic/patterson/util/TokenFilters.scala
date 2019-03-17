package com.linguistic.patterson.util

import java.util.regex.Pattern

import com.linguistic.patterson.models.corenlp.Token
import com.linguistic.patterson.util.StringUtils._

object TokenFilters {
  def any: () ⇒ Boolean = () ⇒ true
  def not: (Token ⇒ Any) ⇒ Token ⇒ Boolean = (filter: Token ⇒ Any) ⇒ (token: Token) ⇒ filter(token) != null
  def pos: String ⇒ Token ⇒ Match = (str: String) ⇒ (token: Token) ⇒ matchStr(str, token.pos)
  def word: String ⇒ Token ⇒ Match = (str: String) ⇒ (token: Token) ⇒ matchStr(str, token.word)
  def isRoot: Token ⇒ Boolean = (token: Token) ⇒ token.index == 0
  def notRoot = this.not(this.isRoot)
  def noPunct = this.not(this.pos("PU"))

  def or(filters: (Token ⇒ Token)*): Token ⇒ Boolean = (token: Token) ⇒ {
    for (filter ← filters) if (filter(token) != null) true
    false
  }

  def and(filters: (Token ⇒ Token)*): Token => Boolean = (token: Token) ⇒ {
    for (filter ← filters) if (filter(token) == null) false
    true
  }

  private def matchStr(regex: String, str: String): Match = {
    str.matchOnce(Pattern.compile(s"^$regex$$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
  }
}
