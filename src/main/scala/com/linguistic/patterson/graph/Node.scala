package com.linguistic.patterson.graph

import com.linguistic.patterson.graph.Edge.Edge
import com.linguistic.patterson.models.corenlp.Token
import com.linguistic.patterson.util.RegexUtils.Location
import com.linguistic.patterson.util.TokenFilters._
import com.linguistic.patterson.util.RegexUtils._

import scala.collection.mutable.ListBuffer

class Node(val filter: Token ⇒ Boolean = null, capture: String = null, val edges: List[Edge] = List[Edge]()) {

  def matches(token: Token): Boolean = {
    if (isRoot(token)) return false
    if (this.filter != null && !this.filter(token)) return false
    if (this.edges != null)
      for (edge ← this.edges)
        if (!edge.optional && !edge.matches(token))
          return false
    true
  }

  def getMatchLocations(token: Token): List[Location] = {
    if (!this.matches(token)) return null

    var matchLocations = new ListBuffer[Location]()

    if (this.capture != null) {
      matchLocations += locationFromToken(token, this.capture)
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
