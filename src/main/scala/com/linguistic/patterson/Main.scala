package com.linguistic.patterson

import com.linguistic.patterson.constants.Language
import com.linguistic.patterson.clients.RemoteClient
import org.apache.commons.lang3.builder.{ReflectionToStringBuilder, ToStringStyle}

object Main {
  def main(args: Array[String]): Unit = {
    val p = new Patterson(new RemoteClient("http://localhost:9000", Language.CHINESE))
    val m = p.matchGrammar("她才来上海两个月")
    println(ReflectionToStringBuilder.toString(m.head.head, ToStringStyle.MULTI_LINE_STYLE))
  }
}
