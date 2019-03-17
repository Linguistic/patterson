package com.linguistic.patterson.graph

import com.linguistic.patterson.models.corenlp.Token
import com.linguistic.patterson.util.RegexUtils.{Location, combineAdjacentLocations}

import scala.collection.mutable.ListBuffer

private class GraphMatch(tokens: List[Token], matchTree: Node) {
  private def recursiveSearchTree(tokenTree: Token, matchTree: Node): List[List[Location]] = {
    var matchLocations = new ListBuffer[List[Location]]()

    if (matchTree.matches(tokenTree)) {
      matchLocations += combineAdjacentLocations(matchTree.getMatchLocations(tokenTree))
    }

    for (dependentTree ← tokenTree.dependents) {
      val depMatchLocations = recursiveSearchTree(dependentTree, matchTree)

      if (depMatchLocations != null) {
        matchLocations ++= depMatchLocations
      }
    }

    if (matchLocations.isEmpty) null else matchLocations.toList
  }

  def run(): List[List[Location]] = recursiveSearchTree(
    tokens.head,
    matchTree
  )
}

object GraphMatch {
  def apply(tokens: List[Token], matchTree: Node): List[List[Location]] = {
    new GraphMatch(tokens, matchTree).run()
  }
}
