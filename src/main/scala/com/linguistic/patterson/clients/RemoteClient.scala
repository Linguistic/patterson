package com.linguistic.patterson.clients

import java.io.{BufferedReader, InputStreamReader}
import java.net.{HttpURLConnection, URL, URLEncoder}

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.linguistic.patterson.models.corenlp.{Dependency, Document, Sentence, Token}
import com.linguistic.patterson.util.JacksonPOJO._

class RemoteClient(host: String, language: String) extends TClient {
    private val annotators = List("tokenize", "ssplit", "pos", "ner", "depparse")
    private val query = URLEncoder.encode(s"""properties={"annotators": "${this.annotators.mkString(",")}"}&pipelineLanguage=${this.language}""", "UTF-8")
    private val uri = s"""$host/?$query"""

    def parse(text: String): Document = {
        val url = new URL(uri)
        val con = url.openConnection.asInstanceOf[HttpURLConnection]

        con.setRequestMethod("GET")
        con.setDoOutput(true)
        con.getOutputStream.write(text.getBytes("UTF8"))
        con.getOutputStream.close()

        val in = new BufferedReader(new InputStreamReader(con.getInputStream))
        val content = new StringBuffer

        Stream
            .continually(in.readLine())
            .takeWhile(_ != null)
            .foreach(content.append(_))

        val mapper = new ObjectMapper(new JsonFactory())
        val cd = mapper.readValue(content.toString, classOf[JSONResponse])

        in.close()
        con.disconnect()

        new Document(
            cd.getSentences.map(s ⇒ new Sentence(
                text,
                s.getTokens.map(t ⇒ new Token(
                    index = t.getIndex,
                    word = t.getWord,
                    beginPosition = t.getCharacterOffsetBegin,
                    endPosition = t.getCharacterOffsetEnd,
                    pos = t.getPos,
                    ner = t.getNer
                )).toList,
                s.getBasicDependencies.map(bd ⇒ Dependency(
                    dep = bd.getDep,
                    governor = bd.getGovernor,
                    dependent = bd.getDependent
                )).toList
            )).toList
        )
    }
}