package com.linguistic.patterson.util

import java.util.regex.Pattern

object StringUtils {
    case class Match(index: Int, groups: List[String])
    implicit class RegexString(val str: String) {
        def `match`(regex: Pattern): List[String] = {
            val matcher = regex.matcher(str)
            var matches = List[String]()

            // TODO: come back to this
//            if (matcher.find)
//                return Match(matcher.start(), matcher.groupCount())
            while (matcher.find())
                matches :+= matcher.group()
            if (matches.nonEmpty) matches else null
        }
    }

}