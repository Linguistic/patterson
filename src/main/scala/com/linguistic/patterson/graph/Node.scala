package com.linguistic.patterson.graph

import com.linguistic.patterson.graph.Edge.Edge
import com.linguistic.patterson.models.corenlp.Token
import com.linguistic.patterson.util.RegexUtils.Location
import com.linguistic.patterson.util.TokenFilters._

class Node(val filter: Token ⇒ Boolean = null, capture: Boolean = false, val edges: List[Edge] = List[Edge]()) {
    def matches(token: Token): Boolean = {
        if (isRoot(token)) return false
        if (this.filter != null && !this.filter(token)) return false
        if (this.edges != null)
            for (edge ← this.edges)
                if (!edge.optional && !edge.matches(token))
                    return false
        true
    }

//    def getMatchLocations(token: Token): Unit = {
//        if (!this.matches(token)) return null
//
//        val matchLocations = List[List[Location]]()
//
//        if (this.capture) {
//            val captureChars = if (this.capture) null else this.capture
//            matchLocations.push(locFromToken(token, captureChars));
//        }
//        if (this.edges) {
//            for (const edge of this.edges) {
//                const edgeMatchLocs = edge.getMatchLocs(token);
//                if (edgeMatchLocs) {
//                    matchLocs = matchLocs.concat(edgeMatchLocs);
//                }
//            }
//        }
//        return matchLocs;
//    }
}
