package com.linguistic.patterson.util

import java.util.regex.Pattern

object StringUtils {
    implicit class RegexString(val str: String) {
        def `match`(regex: Pattern): List[String] = {
            val matcher = regex.matcher(str)
            var matches = List[String]()
            while (matcher.find())
                matches :+= matcher.group()
            if (matches.nonEmpty) matches else null
        }
    }

}