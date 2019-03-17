package com.linguistic.patterson.data.patterns.zh

import com.linguistic.patterson.data.references.AllSetChinese
import com.linguistic.patterson.graph.{Edge, GraphMatch, Node}
import com.linguistic.patterson.models.corenlp.Sentence
import com.linguistic.patterson.models.local.{ExampleSentence, GrammarPattern}
import com.linguistic.patterson.util.RegexUtils._
import com.linguistic.patterson.util.TokenFilters._

class UsingCaiForSmallNumbersPattern
    extends GrammarPattern(
      id = "usingcaiforsmallnumbers",
      description = "The character 才 (cái) can be used to emphasize that a number is small, or less than expected.",
      structures = List("才 +(Verb) + Number + Measure Word +Noun"),
      refs = List(AllSetChinese()),
      examples = List(
        ExampleSentence(
          original = "你才二十岁？",
          translated = "You're only twenty?",
          ref = AllSetChinese(),
        ),
        ExampleSentence(
          original = "这家公司才三个员工。",
          translated = "This company only has three employees.",
          ref = AllSetChinese(),
        ),
        ExampleSentence(
          original = "这个班才两个学生。",
          translated = "This class only has two students.",
          ref = AllSetChinese(),
        ),
        ExampleSentence(
          original = "我一个月工资才两千。",
          translated = "My monthly salary is only two thousand RMB.",
          ref = AllSetChinese(),
        ),
        ExampleSentence(
          original = "你才吃一个包子？",
          translated = "You only ate one baizi?",
          ref = AllSetChinese(),
        ),
        ExampleSentence(
          original = "她才来上海两个月。",
          translated = "She's only been in Shanghai for two months.",
          ref = AllSetChinese(),
        ),
        ExampleSentence(
          original = "我们才看完一页。",
          translated = "We only finished reading one page.",
          ref = AllSetChinese(),
        ),
        ExampleSentence(
          original = "这顿饭才花了二十块。",
          translated = "This meal only cost twenty kuai.",
          ref = AllSetChinese(),
        ),
        ExampleSentence(
          original = "他们认识才三个星期就结婚了。",
          translated = "They only knew each other for three weeks before they got married.",
          ref = AllSetChinese(),
        ),
      )
    ) {

  override def matches(sentence: Sentence): List[List[Location]] = {
    val counter = new Edge(
      edgeType = "nummod|nmod:.*|dep",
      new Node(filter = pos("CD"),
               edges = List(
                 new Edge(edgeType = "mark:clf", childNode = new Node(filter = pos("M"))),
               ))
    )

    mergeMatchGroups(
      List(
        GraphMatch(
          sentence.tokens,
          new Node(filter = pos("NN"),
                   edges =
                     List(new Edge(edgeType = "advmod", childNode = new Node(filter = word("才"), doCapture = true)),
                          counter))
        ),
        GraphMatch(
          sentence.tokens,
          new Node(filter = pos("CD"),
                   edges =
                     List(new Edge(edgeType = "advmod", childNode = new Node(filter = word("才"), doCapture = true))))
        ),
        GraphMatch(
          sentence.tokens,
          new Node(
            filter = pos("VV"),
            edges = List(
              new Edge(edgeType = "advmod", childNode = new Node(filter = word("才"), doCapture = true)),
              new Edge(edgeType = "dobj", childNode = new Node(filter = pos("NN"), edges = List(counter)))
            )
          )
        ),
        GraphMatch(
          sentence.tokens,
          new Node(
            filter = pos("VV"),
            edges = List(
              new Edge(edgeType = "advmod", childNode = new Node(filter = word("才"), doCapture = true)),
              counter
            )
          )
        ),
      )
    )
  }
}

object UsingCaiForSmallNumbersPattern {
  def apply() = new UsingCaiForSmallNumbersPattern()
}
