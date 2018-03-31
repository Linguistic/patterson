package com.linguistic.patterson.graph

import com.linguistic.patterson.models.Token
import com.linguistic.patterson.util.TokenUtils._

class Node(val filter: Token ⇒ Boolean = null, val edges: String = null) {
    def matches(token: Token): Boolean = {
        if (isRoot(token)) return false
        if (this.filter != null && !this.filter(token)) return false

        if (this.edges != null) {
            for (edge ← this.edges) {
//                if (!edge.op)
            }
        }

        return false
    }
}
