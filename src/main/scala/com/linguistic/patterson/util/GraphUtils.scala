package com.linguistic.patterson.util

import com.linguistic.patterson.graph.Node
import com.linguistic.patterson.models.corenlp.Token
import com.linguistic.patterson.util.RegexUtils._

object GraphUtils {
    private def recursiveSearchTree(tokenTree: Token, matchTree: Node): List[List[Location]] = {
        var matchLocations = List[List[Location]]()

        if (matchTree.matches(tokenTree))
            matchLocations :+= combineAdjacentLocations(matchTree.getMatchLocs(tokenTree))

        for (dependentTree ← tokenTree.dependents) {
            val depMatchLocations = recursiveSearchTree(dependentTree, matchTree)
            if (depMatchLocations != null)
                matchLocations = matchLocations.concat(depMatchLocations)
        }

        if (matchLocations.isEmpty) null else matchLocations
    }

    def graphMatch(tokens: List[Token], matchTree: Node): List[List[Location]] = recursiveSearchTree(
        tokens.head,
        matchTree
    )
}
