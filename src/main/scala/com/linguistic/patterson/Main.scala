package com.linguistic.patterson

import org.apache.commons.lang3.builder.{ReflectionToStringBuilder, ToStringStyle}

object Main {
    def main(args: Array[String]): Unit = {
        val p = new Patterson()
        println(ReflectionToStringBuilder.toString(p.matchGrammar("天气越来越冷了")(0)(0), ToStringStyle.MULTI_LINE_STYLE))
    }
}
