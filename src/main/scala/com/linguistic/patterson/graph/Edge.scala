package com.linguistic.patterson.graph

import java.util.regex.Pattern

import com.linguistic.patterson.models.corenlp.Token
import com.linguistic.patterson.util.RegexUtils.Location
import com.linguistic.patterson.util.StringUtils._

object Edge {
    class Edge(val edgeType: String,
               val childNode: Node,
               val optional: Boolean = false,
               val ahead: Boolean = false,
               val behind: Boolean = false) {

        val t: Pattern = Pattern.compile(s"^($edgeType)$$")

        private def matchesDependent(token: Token, dependent: Token): Boolean = {
            if (dependent.dependencyType == "ROOT") return false

            val typeMatches = (this.t != null) || dependent.dependencyType.`match`(this.t).nonEmpty
            val aheadMatches = !this.ahead || token.index > dependent.index
            val behindMatches = !this.behind || token.index < dependent.index

            typeMatches && aheadMatches && behindMatches && this.childNode.matches(dependent)
        }

        def matches(token: Token): Boolean = {
            for (dependent ← token.dependents)
                if (this.matchesDependent(token, dependent))
                    return true
            false
        }

        def getMatchLocations(token: Token): List[Location] = {
            for (dependent ← token.dependents)
                if (this.matchesDependent(token, dependent))
                    return this.childNode.getMatchLocations(dependent)
            null
        }
    }
}