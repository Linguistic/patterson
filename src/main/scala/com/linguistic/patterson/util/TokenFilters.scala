package com.linguistic.patterson.util

import java.util.regex.Pattern

import com.linguistic.patterson.models.corenlp.Token
import com.linguistic.patterson.util.StringUtils._

object TokenFilters {
    def pos = (str: String) ⇒ (token: Token) ⇒ token.pos.`match`(Pattern.compile(s"^${str}$$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
    def word = (str: String) ⇒ (token: Token) ⇒ token.word.`match`(Pattern.compile(s"^${str}$$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
    def not = (filter: Token ⇒ Any) ⇒ (token: Token) ⇒ (filter(token) != null)
    def any = () ⇒ true

    def isRoot = (token: Token) ⇒ token.index == 0
    def notRoot = this.not(this.isRoot)
    def noPunct = this.not(this.pos("PU"))

    def or(filters: (Token ⇒ Token)*) : Token ⇒ Boolean = (token: Token) ⇒ {
        for (filter ← filters) if (filter(token) != null) true
        false
    }

    def and(filters: (Token ⇒ Token)*) : Token => Boolean = (token: Token) ⇒ {
        for (filter ← filters) if (filter(token) == null) false
        true
    }
}
