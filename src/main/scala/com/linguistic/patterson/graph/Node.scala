package com.linguistic.patterson.graph

import com.linguistic.patterson.models.corenlp.Token
import com.linguistic.patterson.util.RegexUtils.Location
import com.linguistic.patterson.util.TokenFilters._
import com.linguistic.patterson.util.RegexUtils._
import com.linguistic.patterson.util.StringUtils.Match

import scala.collection.mutable.ListBuffer

class Node(val filter: Token ⇒ Match = null,
           doCapture: Boolean = false,
           captureChars: Option[String] = None,
           val edges: List[Edge] = List[Edge]()) {

  def matches(token: Token): Boolean = {
    if (isRoot(token)) {
      return false
    }

    if (this.filter != null && this.filter(token) == null) {
      return false
    }

    if (this.edges != null)
      for (edge ← this.edges)
        if (!edge.optional && !edge.matches(token))
          return false

    true
  }

  def getMatchLocations(token: Token): List[Location] = {
    if (!this.matches(token)) return null

    var matchLocations = new ListBuffer[Location]()

    if (this.doCapture) {
      matchLocations += locationFromToken(token, this.captureChars.orNull)
    }

    if (this.edges.nonEmpty) {
      this.edges.foreach(edge ⇒ {
        val edgeMatchLocations = edge.getMatchLocations(token)

        if (edgeMatchLocations != null) {
          matchLocations ++= edgeMatchLocations
        }
      })
    }

    matchLocations.toList
  }
}
