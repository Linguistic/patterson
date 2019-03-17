package com.linguistic.patterson.util

import java.util.regex.Pattern

import scala.collection.mutable.ListBuffer

object StringUtils {
  case class Match(index: Int, text: String, groups: List[String])

  implicit class RegexString(val str: String) {
    def matchOnce(regex: Pattern): Match = {
      val matcher = regex.matcher(str)
      val matched = matcher.find()

      if (matched) {
        var groups = new ListBuffer[String]()

        for (x ← 1 to matcher.groupCount()) {
          groups += matcher.group(x)
        }

        return Match(matcher.start(), matcher.group(), groups.toList)
      }

      null
    }

    def matchAll(regex: Pattern): List[String] = {
      val matcher = regex.matcher(str)
      val matches = new ListBuffer[String]()

      while (matcher.find()) {
        matches += matcher.group()
      }

      matches.toList
    }
  }

}
