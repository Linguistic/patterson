package com.linguistic.patterson

import com.linguistic.patterson.constants.Language
import com.linguistic.patterson.clients.RemoteClient
import org.apache.commons.lang3.builder.{ReflectionToStringBuilder, ToStringStyle}

object Main {
    def main(args: Array[String]): Unit = {
        val p = new Patterson(new RemoteClient("http://localhost:9000", Language.CHINESE))
        println(ReflectionToStringBuilder.toString(p.matchGrammar("天气越来越冷了").head.head, ToStringStyle.MULTI_LINE_STYLE))
    }
}
